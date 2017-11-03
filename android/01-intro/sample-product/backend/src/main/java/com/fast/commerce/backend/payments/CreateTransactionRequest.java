package com.fast.commerce.backend.payments;

import com.fast.commerce.backend.model.RequestHeader;


public class CreateTransactionRequest {

    private RequestHeader mRequestHeader;
    private String mPaymentNonce;
    private String mAmount;

    public CreateTransactionRequest() {}

    public CreateTransactionRequest(RequestHeader requestHeader) {
        this.mRequestHeader = requestHeader;
    }

    public RequestHeader getRequestHeader() {
        return mRequestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.mRequestHeader = requestHeader;
    }

    public String getPaymentNonce() {
        return mPaymentNonce;
    }

    public void setPaymentNonce(String paymentNonce) {
        this.mPaymentNonce = paymentNonce;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        this.mAmount = amount;
    }
}
