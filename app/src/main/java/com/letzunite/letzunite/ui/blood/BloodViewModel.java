package com.letzunite.letzunite.ui.blood;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.data.repository.BloodRepository;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.utils.AbsentLiveData;

/**
 * Created by Deven Singh on 18 Jun, 2018.
 */
public class BloodViewModel extends ViewModel {

    private final BloodRepository repository;

    public LiveData<ResponseResource<String>> bloodRequestResponse;
    private final MutableLiveData<String> bloodRequestInputData = new MutableLiveData<>();

    public BloodViewModel(BloodRepository repository) {
        this.repository = repository;
    }

    public LiveData<ResponseResource<String>> getBloodRequestResponse() {
        bloodRequestResponse = Transformations.switchMap(bloodRequestInputData, input -> {
            if (input == null || input.isEmpty()) {
                return AbsentLiveData.create();
            }
            return repository.sendBloodRequest(input);
        });
        return bloodRequestResponse;
    }

    public void sendBloodRequest(String requestData) {
        bloodRequestInputData.postValue(requestData);
    }


    @Override
    protected void onCleared() {
        repository.cancelAllTasks();
        super.onCleared();
    }
}
