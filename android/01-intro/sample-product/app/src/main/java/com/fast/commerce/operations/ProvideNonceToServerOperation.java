package com.fast.commerce.operations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fast.commerce.MainActivity;
import com.fast.commerce.backend.fastApi.model.CreateTransactionRequest;
import com.fast.commerce.backend.fastApi.model.CreateTransactionResponse;
import com.fast.commerce.util.ApiUtil;

import java.io.IOException;

public class ProvideNonceToServerOperation
        extends AsyncTask<String, Void, CreateTransactionResponse> {
    private static final String TAG = MainActivity.TAG;

    private final Context mContext;

    public ProvideNonceToServerOperation(Context context) {
        mContext = context;
    }

    @Override
    protected CreateTransactionResponse doInBackground(String... params) {
        if (params == null || params.length != 2) {
            return null;
        }
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setRequestHeader(ApiUtil.REQUEST_HEADER);
        request.setPaymentNonce(params[0]);
        request.setAmount(params[1]);
        Log.d(TAG, "Nonce2ServerOp, nonce = " + params[0] + ", amount = " + params[1]);
        try {
            CreateTransactionResponse response = ApiUtil.getApiInstance()
                    .createTransaction(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(CreateTransactionResponse response) {
        Log.d(TAG, "createTransaction response = " + response);
        String toastString = "Transaction status = " + response.getResponseHeader().getStatusCode()
                + ", message = " + response.getResponseHeader().getMessage();
        Toast.makeText(mContext, toastString, Toast.LENGTH_LONG).show();
        // TextView txt = (TextView) findViewById(R.id.output);
        // txt.setText("Executed"); // txt.setText(result);
        // might want to change "executed" for the returned string passed
        // into onPostExecute() but that is upto you
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected void onProgressUpdate(Void... values) {}
}
