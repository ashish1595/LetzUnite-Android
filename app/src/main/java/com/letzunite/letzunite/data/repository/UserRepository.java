package com.letzunite.letzunite.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.data.Task;
import com.letzunite.letzunite.data.manager.EncryptedRequestGenerator;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.pojo.common.BaseResponse;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.user.SignInResponse;
import com.letzunite.letzunite.utils.Logger;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.SharedPrefs;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deven Singh on 4/11/2018.
 */
public class UserRepository extends BaseRepository {

    private final ApiService apiService;
    private final EncryptedRequestGenerator requestGenerator;
    private final Resource resource;
    private HashMap<Task, Call> callHashMap;

    public UserRepository(Resource resource, ApiService apiService, SharedPrefs sharedPrefs,
                          EncryptedRequestGenerator requestGenerator) {
        super(sharedPrefs);
        this.resource = resource;
        this.apiService = apiService;
        this.requestGenerator = requestGenerator;
    }

    public LiveData<ResponseResource<String>> createNewAccount(String requestData) {
        MediatorLiveData<ResponseResource<String>> result = new MediatorLiveData<>();
        result.setValue(ResponseResource.loading(null));
        Call<BaseResponse<String>> loginCall =
                apiService.createNewAccount(requestGenerator.getEncryptedRequestData(requestData));
        if (callHashMap == null) callHashMap = new HashMap<>();
        callHashMap.put(Task.SIGN_UP_TASK, loginCall);
        loginCall.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                callHashMap.remove(Task.SIGN_UP_TASK);
                if (response.isSuccessful()) {
                    BaseResponse<String> baseResponse = response.body();
                    if (baseResponse.isSuccessful()) {
                        result.setValue(ResponseResource.success(baseResponse.getData()));
                    } else {
                        result.setValue(ResponseResource.error(baseResponse.getMessage()));
                    }
                } else {
                    String message = null;
                    try {
                        message = response.errorBody().string();
                    } catch (IOException e) {
                        Logger.debug("createNewAccount(requestData): ", "IOException occurs: " + e);
                    }
                    result.setValue(ResponseResource.error(message == null ? resource.toString(R.string.error_default) : message));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                callHashMap.remove(Task.SIGN_UP_TASK);
                if (call.isCanceled()) return;
                if (t instanceof SocketTimeoutException) {
                    result.setValue(ResponseResource.error(resource.toString(R.string.error_time_out)));
                    return;
                }
                result.setValue(ResponseResource.error(resource.toString(R.string.error_default)));
            }
        });
        return result;
    }

    public LiveData<ResponseResource<SignInResponse>> loginUser(String requestData) {
        MediatorLiveData<ResponseResource<SignInResponse>> result = new MediatorLiveData<>();
        result.setValue(ResponseResource.loading(null));
        Call<BaseResponse<SignInResponse>> loginCall =
                apiService.signInUser(requestGenerator.getEncryptedRequestData(requestData));
        if (callHashMap == null) callHashMap = new HashMap<>();
        callHashMap.put(Task.SIGN_IN_TASK, loginCall);
        loginCall.enqueue(new Callback<BaseResponse<SignInResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<SignInResponse>> call, Response<BaseResponse<SignInResponse>> response) {
                callHashMap.remove(Task.SIGN_IN_TASK);
                if (response.isSuccessful()) {
                    BaseResponse<SignInResponse> baseResponse = response.body();
                    if (baseResponse.isSuccessful()) {
                        result.setValue(ResponseResource.success(baseResponse.getData()));
                    } else {
                        result.setValue(ResponseResource.error(baseResponse.getMessage()));
                    }
                } else {
                    String message = null;
                    try {
                        message = response.errorBody().string();
                    } catch (IOException e) {
                        Logger.debug("loginUser(requestData): ", "IOException occurs: " + e);
                    }
                    result.setValue(ResponseResource.error(message == null ? resource.toString(R.string.error_default) : message));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SignInResponse>> call, Throwable t) {
                callHashMap.remove(Task.SIGN_IN_TASK);
                if (call.isCanceled()) return;
                if (t instanceof SocketTimeoutException) {
                    result.setValue(ResponseResource.error(resource.toString(R.string.error_time_out)));
                    return;
                }
                result.setValue(ResponseResource.error(resource.toString(R.string.error_default)));
            }
        });
        return result;
    }
}
