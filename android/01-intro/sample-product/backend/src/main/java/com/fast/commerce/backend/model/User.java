package com.fast.commerce.backend.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phoneNumber;
    private String name;
    private String email;
    private String avatar;
    private List<DeviceRegistration> deviceRegistrations;

    public User() {}

    public User(
            String phoneNumber,
            String name,
            String email,
            String avatar,
            List<DeviceRegistration> deviceRegistrations) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.deviceRegistrations = deviceRegistrations;
    }

    public List<DeviceRegistration> getDeviceRegistrations() {
        return deviceRegistrations;
    }

    public void setDeviceRegistrations(List<DeviceRegistration> deviceRegistrations) {
        this.deviceRegistrations = deviceRegistrations;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public static class DeviceRegistration implements Serializable {

        private static final long serialVersionUID = 1L;

        private  DeviceInfo deviceInfo;
        private  double lat;
        private  double lng;

        public DeviceRegistration() {}

        public DeviceRegistration(
                DeviceInfo deviceInfo,
                double lat,
                double lng) {
            this.deviceInfo = deviceInfo;
            this.lat = lat;
            this.lng = lng;
        }

        public void setDeviceInfo(DeviceInfo deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public DeviceInfo getDeviceInfo() {
            return deviceInfo;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }
}
