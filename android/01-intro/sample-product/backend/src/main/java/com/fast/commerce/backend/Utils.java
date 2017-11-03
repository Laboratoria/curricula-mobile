package com.fast.commerce.backend;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class Utils {
    private static final String SANDBOX_MERCHANT_ID = "6nyqyyx5d2k6s8wd";
    private static final String SANDBOX_PUBLIC_KEY = "c8bttxk3ysrxw39k";
    private static final String SANDBOX_PRIVATE_KEY = "59bcd3dfe1023027abfb6f11acfa5fa7";

    public static BraintreeGateway SANDBOX_GATEWAY = new BraintreeGateway(
            Environment.SANDBOX,
            SANDBOX_MERCHANT_ID,
            SANDBOX_PUBLIC_KEY,
            SANDBOX_PRIVATE_KEY);

    public static String computeSha256Hash(String input) {
        return Hashing.sha256()
                .hashString(input, Charsets.UTF_8)
                .toString();
    }

}