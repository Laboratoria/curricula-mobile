package com.fast.commerce.backend.model;

public class ResponseHeader {

    private StatusCode statusCode;
    private String message;

    public ResponseHeader() {}

    public ResponseHeader(
            StatusCode statusCode,
            String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
