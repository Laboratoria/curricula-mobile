package com.fast.commerce.register;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

public class DigitsHelper implements AuthCallback {

    private static final String TAG = "DigitsHelper";

    public static final String FAST_SP = "FAST_SP";
    public static final String PHONE_NUMBER_KEY = "PHONE_NUMBER";
    public static final String REGISTRATION_STABLE_ID_KEY = "REGISTRATION_STABLE_ID";
    public static final String AUTH_TOKEN_KEY = "AUTH_TOKEN";

    public static final String ANDROID_ID = "ANDROID_ID";

    private final Context context;
    private final SharedPreferences sharedPreferenes;
    private final RegistrationFragment.OnRegistrationListener registrationListener;

    public DigitsHelper(
            final Context context,
            final RegistrationFragment.OnRegistrationListener registrationListener) {
        this.context = context;
        this.registrationListener = registrationListener;
        this.sharedPreferenes = context.getSharedPreferences(FAST_SP, Context.MODE_PRIVATE);
    }

    @Override
    public void success(final DigitsSession digitsSession, final String phoneNumber) {
        final SharedPreferences.Editor editor = sharedPreferenes.edit();
        final String storedPhoneNumber = sharedPreferenes.getString(PHONE_NUMBER_KEY, null);
        if (storedPhoneNumber != null) {
            // Should not happen.
            Log.d(TAG, "Already registered");
            return;
        }
        editor.putString(PHONE_NUMBER_KEY, phoneNumber)
                .putLong(REGISTRATION_STABLE_ID_KEY, digitsSession.getId())
                .putString(AUTH_TOKEN_KEY, digitsSession.getAuthToken().toString())
                .putString(ANDROID_ID, Settings.Secure.getString(
                        context.getContentResolver(), Settings.Secure.ANDROID_ID))
                .apply();
        registrationListener.onRegistration();
    }

    @Override
    public void failure(final DigitsException exception) {
    }
}
