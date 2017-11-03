package com.fast.commerce.backend;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.fast.commerce.backend.model.ResponseHeader;
import com.fast.commerce.backend.model.StatusCode;
import com.fast.commerce.backend.payments.CreateTransactionRequest;
import com.fast.commerce.backend.payments.CreateTransactionResponse;
import java.math.BigDecimal;

public class CreateTransactionHandler implements ApiHandler<
        CreateTransactionResponse, CreateTransactionRequest>{
    @Override
    public CreateTransactionResponse handle(CreateTransactionRequest arg) {
        if (arg.getPaymentNonce() == null || arg.getPaymentNonce().length() == 0
                || arg.getAmount() == null || arg.getAmount().length() == 0) {
            return new CreateTransactionResponse(new ResponseHeader(
                    StatusCode.INVALID_REQUEST,
                    null));
        }
        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal(arg.getAmount()))
                .paymentMethodNonce(arg.getPaymentNonce())
                .options()
                    .submitForSettlement(true)
                    .done();
        Result<Transaction> result = Utils.SANDBOX_GATEWAY.transaction().sale(request);

        CreateTransactionResponse response = new CreateTransactionResponse(new ResponseHeader(
                result.isSuccess() ? StatusCode.OK : StatusCode.BACKEND_ERROR,
                result.getMessage()));
        return response;
    }
}
