package com.fast.commerce.backend;

import com.fast.commerce.backend.inventory.GetInventoryRequest;
import com.fast.commerce.backend.inventory.GetInventoryResponse;
import com.fast.commerce.backend.payments.GetClientPaymentTokenRequest;
import com.fast.commerce.backend.payments.GetClientPaymentTokenResponse;
import com.fast.commerce.backend.payments.CreateTransactionRequest;
import com.fast.commerce.backend.payments.CreateTransactionResponse;
import com.fast.commerce.backend.user.RegisterUserRequest;
import com.fast.commerce.backend.user.RegisterUserResponse;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "fastApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.commerce.fast.com",
                ownerName = "backend.commerce.fast.com",
                packagePath = ""
        )
)
public class FastApi {

        private static final RegisterUserHandler REGISTER_USER_HANDLER =
                new RegisterUserHandler();

        private static final InventoryHandler INVENTORY_HANDLER =
                new InventoryHandler();

        private static final ClientPaymentTokenHandler CLIENT_PAYMENT_TOKEN_HANDLER =
                new ClientPaymentTokenHandler();

        private static final CreateTransactionHandler CREATE_TRANSACTION_HANDLER =
                new CreateTransactionHandler();

        @ApiMethod(name = "registerUser",
                path = "registerUser",
                httpMethod = "post")
        public RegisterUserResponse registerUser(
                RegisterUserRequest registerUserRequest) {
                return REGISTER_USER_HANDLER.handle(registerUserRequest);
        }

        @ApiMethod(name = "getInventory",
                path = "getInventory",
                httpMethod = "post")
        public GetInventoryResponse getInventory(
                GetInventoryRequest getInventoryRequest) {
                return INVENTORY_HANDLER.handle(getInventoryRequest);
        }

        @ApiMethod(name = "getClientPaymentToken",
                path = "getClientPaymentToken",
                httpMethod = "post")
        public GetClientPaymentTokenResponse getClientPaymentToken(
                GetClientPaymentTokenRequest getClientPaymentTokenRequest) {
                return CLIENT_PAYMENT_TOKEN_HANDLER.handle(getClientPaymentTokenRequest);
        }

        @ApiMethod(name = "createTransaction",
                path = "createTransaction",
                httpMethod = "post")
        public CreateTransactionResponse createTransaction(
                CreateTransactionRequest createTransactionRequest) {
                return CREATE_TRANSACTION_HANDLER.handle(createTransactionRequest);
        }
}
