package com.fast.commerce.backend.model;

import java.util.List;

public class Product {

    private String sku;
    private String name;
    private int qtyAvailable;
    private String title;
    private double price;
    private String description;
    private String heroImage;
    private List<String> carouselImages;

    public Product() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(int qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(String heroImage) {
        this.heroImage = heroImage;
    }

    public List<String> getCarouselImages() {
        return carouselImages;
    }

    public void setCarouselImages(List<String> carouselImages) {
        this.carouselImages = carouselImages;
    }
}
