package com.fast.commerce.wishlist;

import android.content.Context;
import android.util.Log;

import com.fast.commerce.db.DatabaseManager;
import com.fast.commerce.db.WishlistTableUtil;

import java.util.ArrayList;

import static com.paypal.android.sdk.onetouch.core.metadata.ah.s;

public class WishlistManager {

    // TODO protect by locks?
    private static ArrayList<WishListItem> sWishList;
    private static WishListItem deletedItem;

    public static ArrayList<WishListItem> getWishList(Context context) {
        if (sWishList != null) return sWishList;
        DatabaseManager manager = DatabaseManager.getInstance(context);
        sWishList = WishlistTableUtil.getWishlistItems(manager.getDatabase());
        return sWishList;
    }

    public static void deleteItem(WishListItem item, Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        WishlistTableUtil.deleteWishlistItem(manager.getDatabase(), item);
        deletedItem = item;
        sWishList.remove(item); // linear scan
    }

    public static void undoDeletion(Context context) {
        if (deletedItem == null) {
            Log.e("Wishlist", "Deleted item is null. Can't restore");
            return;
        }
        // update timestamp of the item.
        addItem(deletedItem, context);
    }

    public static void addItem(String text, Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        long id = WishlistTableUtil.addWishlistItem(manager.getDatabase(), text);
        if (id < 0) {
            return;
        }
        if (sWishList != null) {
            WishListItem item = new WishListItem(id, text, System.currentTimeMillis());
            sWishList.add(0, item);
        }
    }

    public static void addItem(WishListItem item, Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        long id = WishlistTableUtil.addWishlistItem(manager.getDatabase(), item);
        if (id < 0) {
            return;
        }
        // reinserting, so there will be new id.
//        item.setId(id);
        if (sWishList != null) {
            sWishList.add(0, item);
        }
    }

}
