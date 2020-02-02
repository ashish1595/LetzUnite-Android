package com.letzunite.letzunite.network;

import com.letzunite.letzunite.BuildConfig;
import com.letzunite.letzunite.pojo.common.BaseResponse;
import com.letzunite.letzunite.pojo.feeds.Feeds;
import com.letzunite.letzunite.pojo.search.NearbyUserData;
import com.letzunite.letzunite.pojo.user.SignInResponse;
import com.letzunite.letzunite.security.RequestData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Deven Singh on 4/12/2018.
 */

public interface ApiService {

//    @POST(BuildConfig.LOGIN_URL)
//    LiveData<ApiResponse<SignInResponse>> loginUser(@Body RequestData data);

    @POST(BuildConfig.PROFILE_URL)
    Call<BaseResponse<String>> createNewAccount(@Body RequestData data);

    @POST(BuildConfig.LOGIN_URL)
    Call<BaseResponse<SignInResponse>> signInUser(@Body RequestData data);

    @GET(BuildConfig.FEEDS_URL)
    Call<BaseResponse<List<Feeds>>> getFeeds(@HeaderMap Map<String, String> headersMap, @Query("type") Integer type,
                                             @Query("notificationId") String feedId);

    @GET(BuildConfig.NEAR_BY_USERS_URL)
    Call<BaseResponse<List<NearbyUserData>>> fetchNearByUser(@HeaderMap Map<String,String> headersMap,
                                                       @QueryMap Map<String,Object> queryMap);

    @POST(BuildConfig.BLOOD_REQUEST_URL)
    Call<BaseResponse<String>> sendBloodRequest(@HeaderMap Map<String, String> headersMap, @Body RequestData data);
}
