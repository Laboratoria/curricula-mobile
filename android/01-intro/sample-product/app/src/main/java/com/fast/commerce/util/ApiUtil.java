package com.fast.commerce.util;

import com.fast.commerce.backend.fastApi.FastApi;
import com.fast.commerce.backend.fastApi.model.DeviceInfo;
import com.fast.commerce.backend.fastApi.model.RequestHeader;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import static com.paypal.android.sdk.onetouch.core.metadata.ah.I;

public class ApiUtil {

    private static final String TEST_PHONE_NUMBER = "1234567890";
    private static final String TEST_DEVICE_ID = "ABCD1234";
    private static final String TEST_DEVICE_TYPE = "ANDROID";

    public static final RequestHeader REQUEST_HEADER = getRequestHeader(
            TEST_PHONE_NUMBER, TEST_DEVICE_ID, TEST_DEVICE_TYPE);

    private static FastApi sInstance =
            new FastApi.Builder(
                    AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://fastcommerce-153101.appspot.com/_ah/api/")
                    .build();

    public static FastApi getApiInstance() {
        return sInstance;
    }

    public static RequestHeader getRequestHeader(
            String phoneNumber, String deviceId, String deviceType) {
        RequestHeader header = new RequestHeader();
        header.setPhoneNumber(phoneNumber);

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceId(deviceId);
        deviceInfo.setDeviceType(deviceType);

        header.setDeviceInfo(deviceInfo);
        return header;
    }
    private ApiUtil() {}
}
