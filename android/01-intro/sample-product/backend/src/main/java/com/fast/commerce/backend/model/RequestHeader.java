package com.fast.commerce.backend.model;

public class RequestHeader {

    private String phoneNumber;
    private DeviceInfo deviceInfo;

    public RequestHeader() {}

    public RequestHeader(
            String phoneNumber,
            DeviceInfo deviceInfo) {
        this.phoneNumber = phoneNumber;
        this.deviceInfo = deviceInfo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }
}
