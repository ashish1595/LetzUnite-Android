package com.letzunite.letzunite.ui.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.data.repository.SearchBloodRepository;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.search.NearbyUserData;
import com.letzunite.letzunite.utils.AppConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Deven Singh on 31 Jul, 2018.
 */
public class SearchBloodViewModel extends ViewModel {

    private final SearchBloodRepository repository;


    private LiveData<ResponseResource<List<NearbyUserData>>> nearbyUsersResponse;
    private final MutableLiveData<Map<String, Object>> nearbyUserInputData = new MutableLiveData<>();

    public SearchBloodViewModel(SearchBloodRepository repository) {
        this.repository = repository;
        nearbyUsersResponse = Transformations.switchMap(nearbyUserInputData, input ->
                repository.fetchNearbyUsers(input));
    }

    public LiveData<ResponseResource<List<NearbyUserData>>> getNearbyUsers() {
        return nearbyUsersResponse;
    }

    public void fetchNearbyUsers(Integer searchUserType,
                                 String location, String bloodGroup,
                                 Integer range) {
        nearbyUserInputData.postValue(getQueryMap(searchUserType, location, bloodGroup, range));
    }

    private Map<String, Object> getQueryMap(Integer searchUserType, String location,
                                            String bloodGroup, Integer range) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put(AppConstants.Keys.USER_ID,"t");
        queryMap.put(AppConstants.Keys.SEARCH_USER_TYPE, searchUserType);
        queryMap.put(AppConstants.Keys.LOCATION, location);
//        queryMap.put(AppConstants.Keys.BLOOD_GROUP, bloodGroup);
        queryMap.put(AppConstants.Keys.RANGE, range);
        return queryMap;
    }

    @Override
    protected void onCleared() {
        repository.cancelAllTasks();
        super.onCleared();
    }
}
