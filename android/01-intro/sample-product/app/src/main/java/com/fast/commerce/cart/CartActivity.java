package com.fast.commerce.cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.fast.commerce.MainActivity;
import com.fast.commerce.R;
import com.fast.commerce.backend.fastApi.FastApi;
import com.fast.commerce.backend.fastApi.model.GetClientPaymentTokenRequest;
import com.fast.commerce.backend.fastApi.model.GetClientPaymentTokenResponse;
import com.fast.commerce.operations.ProvideNonceToServerOperation;
import com.fast.commerce.util.ApiUtil;
import com.fast.commerce.util.Util;
import java.io.IOException;

public class CartActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.TAG + "#CartAct";
    private static final String KEY_AUTH = "com.fast.commerce.KEY_AUTH";
    private static final String KEY_NONCE = "com.fast.commerce.KEY_NONCE";

    private static final int DROP_IN_REQUEST = 100;

    private Button mCheckoutButton;

    private BraintreeFragment mBraintreeFragment;
    private PaymentMethodNonce mNonce;
    private String mPaymentToken;

    private RecyclerView.Adapter mAdapter;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context.getPackageName(), CartActivity.class.getName());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);

        View.OnClickListener listener = new ClickListener();
        mCheckoutButton = (Button) findViewById(R.id.checkout_action_button);
        mCheckoutButton.setOnClickListener(listener);
        mCheckoutButton.setEnabled(false);

        RecyclerView recList = (RecyclerView) findViewById(R.id.checkout_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        mAdapter = new CartAdapter(this);
        recList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_NONCE)) {
                mNonce = savedInstanceState.getParcelable(KEY_NONCE);
            }
        }

        // Make API call to fetch client payment token
        new FetchTokenOperation().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
                Log.d(TAG, "Result Ok " + result);
                mNonce = result.getPaymentMethodNonce();
                String toastDisplay = mNonce.getNonce() + "\n";
                postNonceToServer(mNonce.getNonce(),
                        Cart.getInstance(CartActivity.this).getTotalPrice().toString());
                toastDisplay = toastDisplay + Util.displayResult(
                        result.getPaymentMethodNonce(), result.getDeviceData());
                Toast.makeText(this, toastDisplay, Toast.LENGTH_LONG).show();
                Cart.getInstance(CartActivity.this).checkout();
                mCheckoutButton.setEnabled(false);
                mAdapter.notifyDataSetChanged();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d(TAG, "User cancelled");
                // the user canceled
            } else {
                // handle exception
                Exception e = (Exception) data.getParcelableExtra(DropInActivity.EXTRA_ERROR);
                Log.e(TAG, "resultCode = " + resultCode, e);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onAuthorizationFetched();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNonce != null) {
            outState.putParcelable(KEY_NONCE, mNonce);
        }
        if (mPaymentToken != null) {
            outState.putString(KEY_AUTH, mPaymentToken);
        }
    }

    private void onAuthorizationFetched() {
        try {
            mBraintreeFragment = BraintreeFragment.newInstance(this, mPaymentToken);
        } catch (InvalidArgumentException e) {
            mPaymentToken = null;
            Log.e(TAG, "onAuthorizationFetched():" + e.getMessage());
        }
        mCheckoutButton.setEnabled(true);
    }

    private void postNonceToServer(String paymentNonce, String amount) {
        new ProvideNonceToServerOperation(this).execute(paymentNonce, amount);
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            switch (viewId) {
                case R.id.checkout_action_button:
                    Log.d(TAG, "Payment button click");
                    onCheckout(view);
                    break;
            }
        }
    }

    // Braintree v2 integration
    public void onCheckout(View v) {
        if (mPaymentToken == null) {
            Log.d(TAG, "onCheckout(): Payment token not available, trying to get again."
                    + "Try checkout after a minute");
            // Make API call to fetch client payment token
            new FetchTokenOperation().execute();
            return;
        }
        if (Cart.getInstance(CartActivity.this).isEmpty()) {
            Toast.makeText(this, "Error: Empty Cart!, disabling checkout", Toast.LENGTH_LONG).show();
            mCheckoutButton.setEnabled(false);
            return;
        }
        Log.d(TAG, "onCheckout() called with token: " + mPaymentToken);
        DropInRequest dropInRequest = new DropInRequest().clientToken(mPaymentToken)
                .collectDeviceData(true)
                .androidPayCart(Cart.getInstance(CartActivity.this).getAndroidPayCart())
                .androidPayShippingAddressRequired(true)
                .androidPayPhoneNumberRequired(true)
                .androidPayAllowedCountriesForShipping("US");
        startActivityForResult(dropInRequest.getIntent(this), DROP_IN_REQUEST);
    }

    private class FetchTokenOperation extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            FastApi api = ApiUtil.getApiInstance();

            try {
                GetClientPaymentTokenRequest request = new GetClientPaymentTokenRequest();
                GetClientPaymentTokenResponse response
                        = api.getClientPaymentToken(request).execute();
                return response.getClientToken();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null || result.length() == 0) {
                Log.e(TAG, "Null client token received.");
                return;
            }
            mPaymentToken = result;
            Log.d(TAG, "onPostExecute, payment token = " + mPaymentToken);
            onAuthorizationFetched();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
