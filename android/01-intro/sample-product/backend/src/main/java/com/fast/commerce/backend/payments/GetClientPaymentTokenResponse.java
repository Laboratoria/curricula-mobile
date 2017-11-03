package com.fast.commerce.backend.payments;

import com.fast.commerce.backend.model.ResponseHeader;


public class GetClientPaymentTokenResponse {

    private ResponseHeader mResponseHeader;
    private String mClientToken;

    public GetClientPaymentTokenResponse(ResponseHeader responseHeader) {
        this.mResponseHeader = responseHeader;
    }

    public ResponseHeader getResponseHeader() {
        return mResponseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.mResponseHeader = responseHeader;
    }

    public String getClientToken() {
        return mClientToken;
    }

    public void setClientToken(String clientToken) {
        this.mClientToken = clientToken;
    }
}
