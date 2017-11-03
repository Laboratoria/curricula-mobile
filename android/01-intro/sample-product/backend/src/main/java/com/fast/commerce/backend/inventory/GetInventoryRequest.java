package com.fast.commerce.backend.inventory;

import com.fast.commerce.backend.model.RequestHeader;

public class GetInventoryRequest {

    private RequestHeader mRequestHeader;

    public GetInventoryRequest() {}

    public RequestHeader getRequestHeader() {
        return mRequestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.mRequestHeader = requestHeader;
    }
}
