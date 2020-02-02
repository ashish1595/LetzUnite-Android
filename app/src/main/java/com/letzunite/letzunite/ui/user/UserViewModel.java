package com.letzunite.letzunite.ui.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.data.repository.UserRepository;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.user.SignInResponse;
import com.letzunite.letzunite.utils.AbsentLiveData;

/**
 * Created by Deven Singh on 4/20/2018.
 */
public class UserViewModel extends ViewModel {

    private UserRepository repository;
    public LiveData<ResponseResource<SignInResponse>> signInResponse;
    public LiveData<ResponseResource<String>> signUpResponse;
    private final MutableLiveData<String> signInInputData = new MutableLiveData<>();
    private final MutableLiveData<String> signUpInputData = new MutableLiveData<>();

    public UserViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public LiveData<ResponseResource<SignInResponse>> getSignInResponse() {
        signInResponse = Transformations.switchMap(signInInputData, input -> {
            if (input == null || input.isEmpty()) {
                return AbsentLiveData.create();
            }
            return repository.loginUser(input);
        });
        return signInResponse;
    }

    public void loginUser(String requestData) {
        signInInputData.postValue(requestData);
    }

    public LiveData<ResponseResource<String>> getSignUpResponse() {
        signUpResponse = Transformations.switchMap(signUpInputData, input -> {
            if (input == null || input.isEmpty()) {
                return AbsentLiveData.create();
            }
            return repository.createNewAccount(input);
        });
        return signUpResponse;
    }

    public void createAccount(String requestData) {
        signUpInputData.postValue(requestData);
    }

    @Override
    protected void onCleared() {
        repository.cancelAllTasks();
        super.onCleared();
    }
}
