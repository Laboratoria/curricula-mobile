package com.fast.commerce.backend.order;

import com.fast.commerce.backend.model.ResponseHeader;

public class CreateOrderResponse {

    private ResponseHeader mResponseHeader;

    public CreateOrderResponse() {}

    public ResponseHeader getResponseHeader() {
        return mResponseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.mResponseHeader = responseHeader;
    }
}
