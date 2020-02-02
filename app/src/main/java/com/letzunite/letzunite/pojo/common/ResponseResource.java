/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.letzunite.letzunite.pojo.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.letzunite.letzunite.pojo.common.Status.ERROR;
import static com.letzunite.letzunite.pojo.common.Status.LOADING;
import static com.letzunite.letzunite.pojo.common.Status.LOGOUT;
import static com.letzunite.letzunite.pojo.common.Status.SUCCESS;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class ResponseResource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    @Nullable
    public final Integer code;

    public ResponseResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this(status, data, message, null);
    }

    public ResponseResource(@NonNull Status status, @Nullable T data, @Nullable String message, @Nullable Integer code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> ResponseResource<T> success(@Nullable T data) {
        return new ResponseResource<>(SUCCESS, data, null);
    }

    public static <T> ResponseResource<T> error(String msg){
        return error(msg,null);
    }

    public static <T> ResponseResource<T> error(String msg, @Nullable Integer code){
        return error(msg,null,code);
    }

    public static <T> ResponseResource<T> error(String msg, @Nullable T data, @Nullable Integer code) {
        return new ResponseResource<>(ERROR, data, msg, code);
    }

    public static <T> ResponseResource<T> loading(@Nullable T data) {
        return new ResponseResource<>(LOADING, data, null);
    }

    public static <T> ResponseResource<T> logout(String msg, @Nullable T data, @Nullable Integer code) {
        return new ResponseResource<>(LOGOUT, data, msg, code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResponseResource<?> resource = (ResponseResource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResponseResource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
