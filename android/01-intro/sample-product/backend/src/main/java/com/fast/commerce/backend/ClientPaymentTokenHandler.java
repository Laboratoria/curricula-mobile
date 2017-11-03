package com.fast.commerce.backend;

import com.fast.commerce.backend.model.ResponseHeader;
import com.fast.commerce.backend.model.StatusCode;
import com.fast.commerce.backend.payments.GetClientPaymentTokenRequest;
import com.fast.commerce.backend.payments.GetClientPaymentTokenResponse;

import java.util.logging.Logger;

import static com.fast.commerce.backend.Utils.SANDBOX_GATEWAY;

public class ClientPaymentTokenHandler implements ApiHandler<
        GetClientPaymentTokenResponse, GetClientPaymentTokenRequest> {

    private static final Logger LOG = Logger.getLogger(ClientPaymentTokenHandler.class.getName());

    public GetClientPaymentTokenResponse handle(GetClientPaymentTokenRequest request) {
        String clientToken = SANDBOX_GATEWAY.clientToken().generate();
        GetClientPaymentTokenResponse response = createOkResponse();
        response.setClientToken(clientToken);
        return response;
    }

    private GetClientPaymentTokenResponse createOkResponse() {
        return new GetClientPaymentTokenResponse(
                new ResponseHeader(
                        StatusCode.OK,
                        null /* No Message */));
    }

    private GetClientPaymentTokenResponse createErrorResponse(
            StatusCode code, String message) {
        return new GetClientPaymentTokenResponse(
                new ResponseHeader(
                        code,
                        message));
    }

}
