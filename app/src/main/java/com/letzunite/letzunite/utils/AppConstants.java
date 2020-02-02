package com.letzunite.letzunite.utils;

/**
 * Created by Deven Singh on 4/11/2018.
 */

public interface AppConstants {

    String DATABASE_NAME = "letzUnite.db";
    String PREF_NAME = "letzUnite_pref";

    interface Keys {
        String NAME="name";
        String EMAIL_ID = "emailId";
        String PASSWORD = "password";
        String DEVICE_ID = "deviceId";
        String ADDITIONAL_INFO = "additionalInfo";
        String VERSION_SDK = "versionSDK";
        String DEVICE = "device";
        String MODEL = "model";
        String MOBILE_NUMBER="mobileNumber";
        String PRODUCT = "product";
        String BRAND = "brand";
        String DEVICE_NAME = "deviceName";
        String OS_VERSION_ID = "osVersionID";
        String IS_NEW_USER="isNewUser";
        String AUTH_TOKEN = "AUTH_TOKEN";
        String USER_ID="userId";
        String SEARCH_USER_TYPE = "searchUserType";
        String LOCATION="location";
        String BLOOD_GROUP="bloodGroup";
        String RANGE="range";
    }

    interface ToolbarTitle {
        String SIGN_IN = "Sign In";
        String SIGN_UP = "Create new account";
        String LETZ_UNITE="Letz Unite";
    }

    interface TaskId{
        String SIGN_IN="SignInTask";
        String SIGN_UP="SignUpTask";
        String FETCH_FEEDS="FetchFeedsTask";
    }
}
