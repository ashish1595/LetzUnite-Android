package com.letzunite.letzunite.pojo.common;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Deven Singh on 09 Jun, 2018.
 */
public class ErrorResponse {

    @SerializedName("error_data")
    HashMap<String,Object> errorData;

    public HashMap<String, Object> getErrorData() {
        return errorData;
    }

    public void setErrorData(HashMap<String, Object> errorData) {
        this.errorData = errorData;
    }
}
