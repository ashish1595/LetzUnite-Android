package com.letzunite.letzunite.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.data.Task;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.pojo.common.BaseResponse;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.feeds.Feeds;
import com.letzunite.letzunite.utils.Logger;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.SharedPrefs;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deven Singh on 09 Jun, 2018.
 */
public class FeedsRepository extends BaseRepository {


    private final ApiService apiService;
    private final Resource resource;

    public FeedsRepository(Resource resource, ApiService apiService, SharedPrefs sharedPrefs) {
        super(sharedPrefs);
        this.resource = resource;
        this.apiService = apiService;
    }

    public LiveData<ResponseResource<List<Feeds>>> getFeeds(Integer type) {
        Logger.error("getFeeds", " Line1");
        MediatorLiveData<ResponseResource<List<Feeds>>> result = new MediatorLiveData<>();
        result.setValue(ResponseResource.loading(null));
        Call<BaseResponse<List<Feeds>>> fetchFeedsCall =
                apiService.getFeeds(getHeaders(), type, null);
        if (callHashMap == null) callHashMap = new HashMap<>();
        callHashMap.put(Task.FETCH_FEED_TASK, fetchFeedsCall);
        fetchFeedsCall.enqueue(new Callback<BaseResponse<List<Feeds>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Feeds>>> call, Response<BaseResponse<List<Feeds>>> response) {
                Logger.error("getFeeds", " Line2");
                callHashMap.remove(Task.FETCH_FEED_TASK);
                if (response.isSuccessful()) {
                    BaseResponse<List<Feeds>> baseResponse = response.body();
                    if (baseResponse.isSuccessful()) {
                        result.setValue(ResponseResource.success(baseResponse.getData()));
                    } else {
                        result.setValue(ResponseResource.error(baseResponse.getMessage(), baseResponse.getStatus()));
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
            public void onFailure(Call<BaseResponse<List<Feeds>>> call, Throwable t) {
                Logger.error("getFeeds", " Line3");
                callHashMap.remove(Task.FETCH_FEED_TASK);
                if (call.isCanceled()) return;
                if (t instanceof ConnectException) {
                    result.setValue(ResponseResource.error(resource.toString(R.string.error_connection)));
                    return;
                }
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
