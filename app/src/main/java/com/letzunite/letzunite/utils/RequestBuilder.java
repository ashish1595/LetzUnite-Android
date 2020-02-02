package com.letzunite.letzunite.utils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 02 Jun, 2018.
 */
@Singleton
public class RequestBuilder {

    private final AppUtils appUtils;

    @Inject
    public RequestBuilder(AppUtils appUtils) {
        this.appUtils = appUtils;
    }

    public String getSignInRequestData(String userId, String password) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put(AppConstants.Keys.EMAIL_ID, userId);
        requestData.put(AppConstants.Keys.PASSWORD, password);
        requestData.put(AppConstants.Keys.DEVICE_ID, appUtils.getAndroidId());
//        requestData.put(AppConstants.Keys.ADDITIONAL_INFO, appUtils.getDeviceDetails());
        return appUtils.toJsonString(requestData);
    }

    public String getSignUpRequestData(String emailId, String name, String mobileNumber, String password) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put(AppConstants.Keys.EMAIL_ID, emailId);
        requestData.put(AppConstants.Keys.NAME, name);
        requestData.put(AppConstants.Keys.MOBILE_NUMBER, mobileNumber);
        requestData.put(AppConstants.Keys.PASSWORD, password);
//        requestData.put(AppConstants.Keys.ADDITIONAL_INFO, appUtils.getDeviceDetails());
        return appUtils.toJsonString(requestData);
    }
}
