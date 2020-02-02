package com.letzunite.letzunite.pojo.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Deven Singh on 02 Jun, 2018.
 */
public class BaseResponse<T> {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Integer status;
    @SerializedName("data")
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccessful(){
        return status>=200 && status<300;
    }
}
