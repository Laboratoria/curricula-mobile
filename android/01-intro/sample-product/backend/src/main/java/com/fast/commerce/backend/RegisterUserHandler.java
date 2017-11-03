package com.fast.commerce.backend;

import com.fast.commerce.backend.entity.UserEntity;
import com.fast.commerce.backend.model.ResponseHeader;
import com.fast.commerce.backend.model.StatusCode;
import com.fast.commerce.backend.model.User;
import com.fast.commerce.backend.storage.OfyService;
import com.fast.commerce.backend.user.RegisterUserRequest;
import com.fast.commerce.backend.user.RegisterUserResponse;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RegisterUserHandler
        implements ApiHandler<RegisterUserResponse, RegisterUserRequest> {

    private static final Logger LOG =
            Logger.getLogger(RegisterUserHandler.class.getName());

    public RegisterUserResponse handle(RegisterUserRequest registerUserRequest) {
        if (!isValidRequest(registerUserRequest)) {
            return createErrorResponse(
                    StatusCode.INVALID_REQUEST,
                    "Invalid RegisterUserRequest.");
        }
        List<UserEntity.DeviceRegistrationEntity> registrationEntities =
                new ArrayList<>(
                        registerUserRequest
                                .getUser()
                                .getDeviceRegistrations()
                                .size());
        for (User.DeviceRegistration registration
                : registerUserRequest.getUser().getDeviceRegistrations()) {
            UserEntity.DeviceRegistrationEntity registrationEntity
                    = new UserEntity.DeviceRegistrationEntity();
            try {
                BeanUtils.copyProperties(
                        registrationEntity,
                        registration);
                registrationEntities.add(registrationEntity);
            } catch (Exception e) {
                LOG.warning("Cannot parse device registration. Invalid request.");
                return createErrorResponse(
                        StatusCode.BACKEND_ERROR,
                        "Invalid Request.");
            }
        }
        UserEntity userEntity = new UserEntity();
        try {
            BeanUtils.copyProperties(
                    userEntity,
                    registerUserRequest.getUser());
        } catch (Exception e) {
            LOG.warning("Cannot parse user entity. Invalid request.");
            return createErrorResponse(
                    StatusCode.BACKEND_ERROR, "Invalid Request.");
        }
        String userKey = Utils.computeSha256Hash(
                registerUserRequest
                        .getUser()
                        .getPhoneNumber());
        userEntity.setDeviceRegistrationEntity(registrationEntities);
        userEntity.setKey(userKey);
        List<UserEntity> saveables = new ArrayList<UserEntity>();
        saveables.add(userEntity);
        LOG.info("Saving entities " + saveables.size());
        OfyService.ofy().save().entities(saveables).now();
        return createOkResponse();
    }

    private List<String> createContactKeys(List<String> contacts) {
        if (contacts == null || contacts.size() == 0) {
            return null;
        }
        List<String> keys = new ArrayList<>(contacts.size());
        for (String contact : contacts) {
            keys.add(Utils.computeSha256Hash(contact));
        }
        return keys;
    }

    private boolean isValidRequest(RegisterUserRequest registerUserRequest) {
        // TODO(tgadh): Using apache utils.
        User user = registerUserRequest.getUser();
        if (user == null
                || user.getPhoneNumber() == null
                || user.getPhoneNumber().length() == 0) {
            LOG.warning("Invalid registerUserRequest.");
            return false;
        }
        for (User.DeviceRegistration registration : user.getDeviceRegistrations()) {
            if (registration.getDeviceInfo() == null
                    || registration.getDeviceInfo().getDeviceId() == null
                    || registration.getDeviceInfo().getDeviceType() == null) {
                LOG.warning("Invalid device registrations in RegisterUserRequest.");
                return false;
            }
        }
        return true;
    }

    private RegisterUserResponse createOkResponse() {
        return new RegisterUserResponse(
                new ResponseHeader(
                        StatusCode.OK,
                        null /* No Message */));
    }

    private RegisterUserResponse createErrorResponse(
            StatusCode code, String message) {
        return new RegisterUserResponse(
                new ResponseHeader(
                        code,
                        message));
    }

}
