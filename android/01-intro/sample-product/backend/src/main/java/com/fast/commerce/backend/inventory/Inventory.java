package com.fast.commerce.backend.inventory;

import com.fast.commerce.backend.model.Product;

import java.util.ArrayList;
import java.util.List;


public class Inventory {

    private static final List<Product> products = new ArrayList<>();

    static {
        Product dummy = new Product();
        dummy.setName("Dummy to be removed");
        products.add(dummy);
        // Add all the products here.
    }

    public static List<Product> getProducts() {
        return products;
    }

}
