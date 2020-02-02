package com.letzunite.letzunite.utils;

/**
 * Created by Deven Singh on 02 Jun, 2018.
 */
public interface PermissionsResultListener {

    void onPermissionResult(int requestCode, boolean isPermissionGrated);

    void shouldShowPermissionRequestReason(int requestCode);
}
