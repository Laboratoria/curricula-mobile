package com.fast.commerce.backend.user;

import com.fast.commerce.backend.model.ResponseHeader;

public class RegisterUserResponse {

    private ResponseHeader responseHeader;

    public RegisterUserResponse() {}

    public RegisterUserResponse(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

}
