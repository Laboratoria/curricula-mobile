package com.fast.commerce.backend;

import com.fast.commerce.backend.model.ResponseHeader;
import com.fast.commerce.backend.model.StatusCode;
import com.fast.commerce.backend.order.CreateOrderRequest;
import com.fast.commerce.backend.order.CreateOrderResponse;
import com.fast.commerce.backend.user.RegisterUserRequest;
import com.fast.commerce.backend.user.RegisterUserResponse;

public class CreateOrderHandler implements ApiHandler<CreateOrderResponse, CreateOrderRequest>  {

    @Override
    public CreateOrderResponse handle(CreateOrderRequest arg) {
        // TODO(tgadh): Add Implementation.
        CreateOrderResponse response = new CreateOrderResponse();
        response.setResponseHeader(new ResponseHeader(
                StatusCode.OK,
                null /* No Message */));
        return response;
    }
}
