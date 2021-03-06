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

package com.letzunite.letzunite.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.letzunite.letzunite.pojo.common.BaseResponse;
import com.letzunite.letzunite.utils.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

/**
 * Common class used by API responses.
 * @param <T>
 */
public class ApiResponse<T> {
    private static final Pattern LINK_PATTERN = Pattern
            .compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");
    private static final Pattern PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)");
    private static final String NEXT_LINK = "next";
    public final int code;
    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;
    @NonNull
    public final Map<String, String> links;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
        links = Collections.emptyMap();
    }

    public ApiResponse(Response<T> response) {
        if(response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
            code = response.code();
        } else {
            String message = null;
            if (response.errorBody() != null) {
                String errorBody=null;
                try {
                    errorBody = response.errorBody().string();
                } catch (IOException ignored) {
                    Logger.error("Exception", "error while parsing response",ignored);
                }
                Gson gson=new Gson();
                BaseResponse baseResponse= gson.fromJson(errorBody, BaseResponse.class);
                message=baseResponse.getMessage();
                code=baseResponse.getStatus();
                body= (T) baseResponse;
            }else {
                body = null;
                code=500;
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
        }
        String linkHeader = response.headers().get("link");
        if (linkHeader == null) {
            links = Collections.emptyMap();
        } else {
            links = new ArrayMap<>();
            Matcher matcher = LINK_PATTERN.matcher(linkHeader);

            while (matcher.find()) {
                int count = matcher.groupCount();
                if (count == 2) {
                    links.put(matcher.group(2), matcher.group(1));
                }
            }
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public Integer getNextPage() {
        String next = links.get(NEXT_LINK);
        if (next == null) {
            return null;
        }
        Matcher matcher = PAGE_PATTERN.matcher(next);
        if (!matcher.find() || matcher.groupCount() != 1) {
            return null;
        }
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException ex) {
            Logger.warn("cannot parse next page from %s "+next);
            return null;
        }
    }
}
