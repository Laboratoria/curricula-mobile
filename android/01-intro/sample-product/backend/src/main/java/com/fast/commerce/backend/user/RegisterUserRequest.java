package com.fast.commerce.backend.user;

import com.fast.commerce.backend.model.RequestHeader;
import com.fast.commerce.backend.model.User;


public class RegisterUserRequest {

    private RequestHeader mRequestHeader;
    private User mUser;

    public RegisterUserRequest() {}

    public RegisterUserRequest(
            RequestHeader requestHeader,
            User user) {
        this.mRequestHeader = requestHeader;
        this.mUser = user;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.mRequestHeader = requestHeader;
    }

    public void setUser(User user) {
        this.mUser = user;
    }

    public RequestHeader getRequestHeader() {
        return mRequestHeader;
    }

    public User getUser() {
        return mUser;
    }

}
