package com.letzunite.letzunite.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.data.Task;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.pojo.common.BaseResponse;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.feeds.Feeds;
import com.letzunite.letzunite.pojo.search.NearbyUserData;
import com.letzunite.letzunite.utils.AppConstants;
import com.letzunite.letzunite.utils.Logger;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.SharedPrefs;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Deven Singh on 31 Jul, 2018.
 */
public class SearchBloodRepository extends BaseRepository {

    private final ApiService apiService;
    private final Resource resource;

    public SearchBloodRepository(Resource resource, ApiService apiService, SharedPrefs sharedPrefs) {
        super(sharedPrefs);
        this.apiService=apiService;
        this.resource=resource;
    }


    public LiveData<ResponseResource<List<NearbyUserData>>> fetchNearbyUsers(Map<String,Object> queries) {
        MediatorLiveData<ResponseResource<List<NearbyUserData>>> result = new MediatorLiveData<>();
        result.setValue(ResponseResource.loading(null));
        Call<BaseResponse<List<NearbyUserData>>> fetchNearByUserCall =
                apiService.fetchNearByUser(getHeaders(), queries);
        if (callHashMap == null) callHashMap = new HashMap<>();
        callHashMap.put(Task.SEARCH_NEAR_BY_USER_TASK, fetchNearByUserCall);
        fetchNearByUserCall.enqueue(new Callback<BaseResponse<List<NearbyUserData>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<NearbyUserData>>> call, Response<BaseResponse<List<NearbyUserData>>> response) {
                callHashMap.remove(Task.SEARCH_NEAR_BY_USER_TASK);
                if (response.isSuccessful()) {
                    BaseResponse<List<NearbyUserData>> baseResponse = response.body();
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
            public void onFailure(Call<BaseResponse<List<NearbyUserData>>> call, Throwable t) {
                callHashMap.remove(Task.SEARCH_NEAR_BY_USER_TASK);
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
