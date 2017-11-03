package com.fast.commerce.cart;

public class CartItem {

    public String itemId;
    public int quantity;

    public CartItem() {}
    public CartItem(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
