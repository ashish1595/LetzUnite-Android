package com.letzunite.letzunite.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 02 Jun, 2018.
 */
@Singleton
public class SharedPrefs {

    interface Key {
        String USER_ID = "userId";
        String IS_USER_LOGGED_IN = "isUserLoggedIn";
        String TOKEN = "appToken";
        String CAPTURED_IMAGE_PATH = "capturedImagePath";
        String CROPPED_IMAGE_PATH = "croppedImagePath";
    }

    private SharedPreferences mSharedPreferences;

    @Inject
    public SharedPrefs(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public String getUserId() {
        return mSharedPreferences.getString(Key.USER_ID, "");
    }

    public void setUserId(String userId) {
        mSharedPreferences.edit().putString(Key.USER_ID, userId).commit();
    }

    public boolean isUserLoggedIn() {
        return mSharedPreferences.getBoolean(Key.IS_USER_LOGGED_IN, false);
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        mSharedPreferences.edit().putBoolean(Key.IS_USER_LOGGED_IN, userLoggedIn).commit();
    }

    public String getToken() {
        return mSharedPreferences.getString(Key.TOKEN, "");
    }

    public void setToken(String token) {
        mSharedPreferences.edit().putString(Key.TOKEN, token).commit();
    }

    public String getCapturedImagePath() {
        return mSharedPreferences.getString(Key.CAPTURED_IMAGE_PATH, null);
    }

    public void setCapturedImagePath(String capturedImagePath) {
        mSharedPreferences.edit().putString(Key.CAPTURED_IMAGE_PATH, capturedImagePath).commit();
    }

    public String getCroppedImagePath() {
        return mSharedPreferences.getString(Key.CROPPED_IMAGE_PATH, null);
    }

    public void setCroppedImagePath(String croppedImagePath) {
        mSharedPreferences.edit().putString(Key.CROPPED_IMAGE_PATH, croppedImagePath).commit();
    }

    public void clearAllData() {
        mSharedPreferences.edit().clear().commit();
    }
}
