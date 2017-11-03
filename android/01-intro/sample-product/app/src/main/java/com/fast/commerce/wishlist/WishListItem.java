package com.fast.commerce.wishlist;

public class WishListItem {

    private long id = -1;
    private String text;
    private long timestamp;

    // should be used sparingly
    public WishListItem() {}

    public WishListItem(long id, String text, long timestamp) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
