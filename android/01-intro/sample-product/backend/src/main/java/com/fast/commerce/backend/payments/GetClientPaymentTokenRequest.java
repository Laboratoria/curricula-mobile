package com.fast.commerce.backend.payments;

import com.fast.commerce.backend.model.RequestHeader;

public class GetClientPaymentTokenRequest {

    private RequestHeader mRequestHeader;

    public GetClientPaymentTokenRequest() {}

    public GetClientPaymentTokenRequest(RequestHeader requestHeader) {
        this.mRequestHeader = requestHeader;
    }

    public RequestHeader getRequestHeader() {
        return mRequestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.mRequestHeader = requestHeader;
    }
}
