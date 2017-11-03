package com.fast.commerce;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class InventoryItem implements Serializable {

    public String itemId;
    public String title;
    public String description;
    // TODO: Store this in BigDecimal instead of float?
    public BigDecimal price;
    public String thumbnailUrl;
    public ArrayList<String> largePictureUrls = new ArrayList<>();
    public int quantity = 0; // to be purchased
    public int inStock;

    public InventoryItem(String id, String title, String url, BigDecimal price) {
        this.itemId = id;
        this.title = title;
        this.description = "This is a " + title + " for sale!";
        this.thumbnailUrl = url;
        largePictureUrls.add(url);
        largePictureUrls.add(url);
        largePictureUrls.add(url);
        this.price = price;
    }
}
