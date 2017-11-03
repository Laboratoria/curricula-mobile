package com.fast.commerce.backend;

public interface ApiHandler<T, U> {

    public T handle(U arg);

}