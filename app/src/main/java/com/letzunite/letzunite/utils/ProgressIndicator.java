package com.letzunite.letzunite.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Window;

import com.letzunite.letzunite.R;

import java.util.HashMap;

/**
 * Created by Deven Singh on 4/23/2018.
 */

public class ProgressIndicator {


    private HashMap<String, ProgressDialog> progressDialogHashMap = new HashMap<>();

    private ProgressIndicator() {
    }

    private static class ProgressIndicatorHolder {
        private static final ProgressIndicator INSTANCE = new ProgressIndicator();
    }

    public static ProgressIndicator getDefault() {
        return ProgressIndicator.ProgressIndicatorHolder.INSTANCE;
    }

    private void showProgressIndicator(Context context, String tittle, String message, String taskId) {
        dismiss(taskId);
        if (context == null) return;
        ProgressDialog progressDialog = new ProgressDialog(context, R.style.DefaultDialogTheme);
        progressDialog.setTitle(tittle);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Window window = progressDialog.getWindow();
            if (window != null)
                window.setBackgroundDrawable(new ColorDrawable(
                        Color.TRANSPARENT));
        }
        if (taskId != null)
            progressDialogHashMap.put(taskId, progressDialog);
        progressDialog.show();
    }

    public void showProgressIndicator(Context context, String message, String taskId) {
        showProgressIndicator(context, null, message, taskId);
    }

    public void showProgressIndicator(Context context, String taskId) {
        showProgressIndicator(context, "Please wait a moment", taskId);
    }


    public void dismiss(String taskId) {
        if (taskId == null || progressDialogHashMap == null) return;
        if (progressDialogHashMap.size() > 0 &&
                progressDialogHashMap.containsKey(taskId)) {
            ProgressDialog progressDialog = progressDialogHashMap.get(taskId);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialogHashMap.remove(taskId);
        }
    }
}
