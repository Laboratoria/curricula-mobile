package com.fast.commerce.cart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fast.commerce.InventoryItem;
import com.fast.commerce.MainActivity;
import com.fast.commerce.db.CartTableUtil;
import com.fast.commerce.db.DatabaseManager;
import com.google.android.gms.wallet.LineItem;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    // Map of String id's to integer.
    Map<String, Integer> idToCountMap;
    List<String> iterableOrder;
    boolean isOrderValid = false;
    SQLiteDatabase db;

    private static Cart theCart;
    public synchronized static Cart getInstance(Context context) {
        if (theCart == null) {
            theCart = new Cart(context);
        }
        return theCart;
    }

    private Cart(Context context) {
        idToCountMap = new LinkedHashMap<>();
        db = DatabaseManager.getInstance(context).getDatabase();
        initializeIdToCountMap();
    }

    private void initializeIdToCountMap() {
        ArrayList<CartItem> items = CartTableUtil.getCartItems(db);
        for (CartItem item : items) {
            idToCountMap.put(item.itemId, item.quantity);
        }
    }

    public synchronized void add(String itemId) {
        int count = 1;
        if (idToCountMap.containsKey(itemId)) {
            update(itemId, idToCountMap.get(itemId) + 1);
            return;
        }
        isOrderValid = false;
        idToCountMap.put(itemId, count);
        CartItem item = new CartItem(itemId, 1);
        CartTableUtil.addCartItem(db, item);
    }

    public synchronized void update(String itemId, int quantity) {
        idToCountMap.put(itemId, quantity);
        CartItem item = new CartItem(itemId, quantity);
        CartTableUtil.updateCartItem(db, item);
    }

    public synchronized void remove(String itemId) {
        idToCountMap.remove(itemId);
        isOrderValid = false;
        CartTableUtil.deleteCartItem(db, itemId);
    }

    public synchronized boolean isEmpty() {
        return idToCountMap.isEmpty();
    }

    public synchronized int getQuantityForItem(String id) {
        if (idToCountMap.containsKey(id)) {
            return idToCountMap.get(id);
        }
        else return 0;
    }

    public synchronized int getTotalCount() {
        if (isEmpty()) return 0;
        int count = 0;
        for (Map.Entry<String, Integer> entry : idToCountMap.entrySet()) {
            count += entry.getValue();
        }
        return count;
    }

    public synchronized int getDistinctItemCount() {
        return idToCountMap.size();
    }

    public synchronized BigDecimal getTotalPrice() {
        BigDecimal total = new BigDecimal("0.0");
        for (Map.Entry<String, Integer> entry : idToCountMap.entrySet()) {
            String itemId = entry.getKey();
            int count = entry.getValue();
            BigDecimal itemPrice = new BigDecimal(
                    MainActivity.itemIdToInventoryMap.get(itemId).price.toString());
            total = total.add(itemPrice.multiply(new BigDecimal(count)));
        }
        return total;
    }

    public synchronized String getItemIdAtPosition(int position) {
        if (!isOrderValid) {
            iterableOrder = new ArrayList<>(idToCountMap.keySet());
        }
        return iterableOrder.get(position);
    }

    public synchronized void checkout() {
        // do whatever needs to be done.
        clear();
    }

    public synchronized void clear() {
        isOrderValid = false;
        idToCountMap.clear();
        CartTableUtil.deleteAllCartItems(db);
    }

    public synchronized com.google.android.gms.wallet.Cart getAndroidPayCart() {
        BigDecimal cartAmount = getTotalPrice();
        com.google.android.gms.wallet.Cart.Builder cartBuilder =
                com.google.android.gms.wallet.Cart.newBuilder()
                        .setCurrencyCode("USD")
                        .setTotalPrice(cartAmount.toString());
        for (int i = 0; i < getDistinctItemCount(); ++i) {
            String itemId = getItemIdAtPosition(i);
            InventoryItem item = MainActivity.itemIdToInventoryMap.get(itemId);
            int numberOfItems = getQuantityForItem(itemId);
            BigDecimal itemCostTotal = item.price;
            itemCostTotal.multiply(new BigDecimal(numberOfItems));
            cartBuilder.addLineItem(LineItem.newBuilder()
                    .setCurrencyCode("USD")
                    .setDescription(item.description)
                    .setQuantity(Integer.toString(numberOfItems))
                    .setUnitPrice(item.price.toString())
                    .setTotalPrice(itemCostTotal.toString())
                    .build());
        }
        com.google.android.gms.wallet.Cart walletCart = cartBuilder.build();
        Log.d(MainActivity.TAG, "Cart amount = " + walletCart.getTotalPrice());
        return walletCart;
    }
}
