package com.letzunite.letzunite.utils;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Deven Singh on 02 Jun, 2018.
 */
public class PermissionHandler {

    private final AppCompatActivity activity;

    public PermissionHandler(AppCompatActivity activity){
        this.activity=activity;
    }

    private int requestPermissionCode;
    private PermissionsResultListener resultListener;


    public boolean isPermissionGranted(String permission) {
        int hasCameraPermission = ContextCompat.checkSelfPermission(activity, permission);
        return (hasCameraPermission == PackageManager.PERMISSION_GRANTED) ? true : false;
    }

    public void requestPermission(String permission, int requestCode, PermissionsResultListener resultListener) {
        this.requestPermissionCode = requestCode;
        this.resultListener = resultListener;
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    requestCode);
        } else {
            resultListener.shouldShowPermissionRequestReason(requestCode);
        }
    }

    public void requestPermissionAfterShowReason(String permission, int requestCode, PermissionsResultListener resultListener) {
        ActivityCompat.requestPermissions(activity,
                new String[]{permission},
                requestCode);
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == requestPermissionCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (resultListener != null)
                    resultListener.onPermissionResult(requestCode, true);
            } else {
                if (resultListener != null)
                    resultListener.onPermissionResult(requestCode, false);
            }
        }
    }

    public void deleteReference() {
        resultListener = null;
    }

}
