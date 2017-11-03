package com.fast.commerce.backend.payments;

import com.fast.commerce.backend.model.ResponseHeader;

public class CreateTransactionResponse {

    private ResponseHeader mResponseHeader;

    public CreateTransactionResponse(ResponseHeader responseHeader) {
        this.mResponseHeader = responseHeader;
    }

    public ResponseHeader getResponseHeader() {
        return mResponseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.mResponseHeader = responseHeader;
    }
}
