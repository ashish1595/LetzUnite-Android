package com.letzunite.letzunite.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.letzunite.letzunite.di.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 18 May, 2018.
 */
@Singleton
public class AppUtils {
    private final Context context;
    private final Gson gson;

    @Inject
    public AppUtils(@ApplicationContext Context context, Gson gson) {
        this.context = context;
        this.gson=gson;
    }

    public String getAndroidId() {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /*
     *  To get device height
     * */
    public int getDeviceHeight() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /*
     *  To get device width
     * */
    public int getDeviceWidth() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(dp);
    }

    public float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    /**
     * method to get device details
     *
     * @return {@link Map}
     */
    public Map<String, Object> getDeviceDetails() {
        Map<String, Object> deviceDetails = new HashMap<>();
        deviceDetails.put(AppConstants.Keys.VERSION_SDK, Build.VERSION.SDK_INT);
        deviceDetails.put(AppConstants.Keys.DEVICE, Build.DEVICE);
        deviceDetails.put(AppConstants.Keys.MODEL, Build.MODEL);
        deviceDetails.put(AppConstants.Keys.MODEL, Build.PRODUCT);
        deviceDetails.put(AppConstants.Keys.BRAND, Build.BRAND);
        deviceDetails.put(AppConstants.Keys.DEVICE_NAME, getDeviceName());
        deviceDetails.put(AppConstants.Keys.OS_VERSION_ID, getOsVersion());
        return deviceDetails;
    }

    /**
     * To get Manufacturer and Model name of Device
     */
    public String getDeviceName() {
        String deviceMfg = Build.MANUFACTURER;
        String deviceModel = Build.MODEL;
        if (deviceModel.startsWith(deviceMfg)) {
            return deviceModel.toUpperCase();
        }
        return deviceMfg.toUpperCase() + " " + deviceModel;
    }

    /**
     * To get OS version
     */
    public Integer getOsVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * to read file from assets
     *
     * @param fileName
     * @return
     */
    public String readDataFromFile(String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            return null;
        }
        return json;
    }

    public <T> T toObjFromJsonString(String stringObj, Class<T> clazz) {
        return gson.fromJson(stringObj, clazz);
    }

    public String toJsonString(Object object){
        return gson.toJson(object);
    }
}
