package com.fast.commerce.backend.inventory;

import com.fast.commerce.backend.model.Product;
import com.fast.commerce.backend.model.ResponseHeader;

import java.util.List;


public class GetInventoryResponse {

    private ResponseHeader mResponseHeader;
    private List<Product> mProducts;

    public GetInventoryResponse() {}

    public ResponseHeader getResponseHeader() {
        return mResponseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.mResponseHeader = responseHeader;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        this.mProducts = products;
    }
}
