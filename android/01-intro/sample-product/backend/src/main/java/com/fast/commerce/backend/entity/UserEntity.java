package com.fast.commerce.backend.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fast.commerce.backend.model.DeviceInfo;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String key;
    private String phoneNumber;
    private String name;
    private String email;
    private String avatar;
    private List<DeviceRegistrationEntity> deviceRegistrationEntity;
    private Set<String> contacts;

    public UserEntity() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<DeviceRegistrationEntity> getDeviceRegistrationEntity() {
        return deviceRegistrationEntity;
    }

    public void setDeviceRegistrationEntity(
            List<DeviceRegistrationEntity> deviceRegistrationEntity) {
        this.deviceRegistrationEntity = deviceRegistrationEntity;
    }

    public Set<String> getContacts() {
        return contacts;
    }

    public void setContacts(Set<String> contacts) {
        this.contacts = contacts;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static class DeviceRegistrationEntity implements Serializable {

        private static final long serialVersionUID = 1L;
        private DeviceInfo deviceInfo;
        private double lat;
        private double lng;

        public DeviceRegistrationEntity() {}

        public DeviceInfo getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(DeviceInfo deviceInfo) {
            this.deviceInfo = deviceInfo;
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
