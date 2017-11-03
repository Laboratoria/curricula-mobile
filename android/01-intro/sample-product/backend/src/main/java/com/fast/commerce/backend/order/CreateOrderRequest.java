package com.fast.commerce.backend.order;

import com.fast.commerce.backend.model.Order;
import com.fast.commerce.backend.model.RequestHeader;

public class CreateOrderRequest {

    private RequestHeader mRequestHeader;
    private Order mOrder;

    public CreateOrderRequest() {}

    public CreateOrderRequest(
            RequestHeader requestHeader,
            Order order) {
        this.mRequestHeader = requestHeader;
        this.mOrder = order;
    }

    public RequestHeader getRequestHeader() {
        return mRequestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.mRequestHeader = requestHeader;
    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        this.mOrder = order;
    }
}

