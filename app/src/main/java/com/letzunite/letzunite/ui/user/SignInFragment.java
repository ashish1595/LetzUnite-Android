package com.letzunite.letzunite.ui.user;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.data.Task;
import com.letzunite.letzunite.data.database.LetzUniteDatabase;
import com.letzunite.letzunite.data.database.entities.User;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.user.SignInResponse;
import com.letzunite.letzunite.pojo.user.UserProfile;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.ui.main_home.MainActivity;
import com.letzunite.letzunite.utils.AppConstants;
import com.letzunite.letzunite.utils.ProgressIndicator;
import com.letzunite.letzunite.utils.RequestBuilder;
import com.letzunite.letzunite.utils.SharedPrefs;
import com.letzunite.letzunite.utils.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Deven Singh on 4/9/2018.
 */

public class SignInFragment extends BaseFragment<UserViewModel> {

    public static final String TAG = "SignInFragment";

    @BindView(R.id.til_user_id)
    TextInputLayout userIdTil;
    @BindView(R.id.tiet_user_id)
    TextInputEditText userIdTiet;
    @BindView(R.id.til_password)
    TextInputLayout passwordTil;
    @BindView(R.id.tiet_password)
    TextInputEditText passwordTiet;
    @BindView(R.id.bt_login)
    Button loginBt;
    @BindView(R.id.tv_forward_password)
    TextView forgotPasswordTv;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    Validator validator;
    @Inject
    RequestBuilder requestBuilder;
    @Inject
    SharedPrefs sharedPrefs;
    @Inject
    LetzUniteDatabase database;

    private UserViewModel mViewModel;
    private long lastClick;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_sign_in;
    }

    @Override
    protected UserViewModel getViewModel() {
        return mViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(UserViewModel.class);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(AppConstants.ToolbarTitle.SIGN_IN);
        initComponents();
    }

    private void initComponents() {
        mViewModel.getSignInResponse().observe(this, signInObserver);
    }

    Observer<ResponseResource<SignInResponse>> signInObserver = responseResource -> {
        if (responseResource == null) return;
        switch (responseResource.status) {
            case LOADING:
                ProgressIndicator.getDefault().showProgressIndicator(getActivity(), Task.SIGN_IN_TASK.name());
                break;
            case SUCCESS:
                updateUser(responseResource.data);
                ProgressIndicator.getDefault().dismiss(Task.SIGN_IN_TASK.name());
                showSnackBar("Login successfully!");
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case ERROR:
                ProgressIndicator.getDefault().dismiss(Task.SIGN_IN_TASK.name());
                showSnackBar(responseResource.message);
                break;
        }
    };

    private void updateUser(SignInResponse data) {
        sharedPrefs.setToken(data.getTokenId());
        sharedPrefs.setUserId(data.getUserProfile().getEmailId());
        AsyncTask.execute(() -> {
            UserProfile userProfile = data.getUserProfile();
            User user = new User();
            user.setEmailId(userProfile.getEmailId());
            user.setMobileNumber(userProfile.getMobileNumber());
            user.setName(userProfile.getName());
            user.setStatus(userProfile.getStatus());
            database.userDao().insertUser(user);
        });
    }

    @OnClick(R.id.bt_login)
    void signInClick() {
        if (System.currentTimeMillis() - lastClick < 200) return;
        lastClick = System.currentTimeMillis();
        String requestData = getRequestData();
        mViewModel.loginUser(requestData);
    }

    private String getRequestData() {
        String emailId = userIdTiet.getText().toString().trim();
        if (!validator.isValidEmailAddress(emailId)) {
            userIdTil.setError("Invalid email address.");
            return null;
        }
        String password = passwordTiet.getText().toString().trim();
        if (!validator.isValidPassword(password)) {
            passwordTil.setError("Invalid password. Please enter a valid password.");
            return null;
        }
        return requestBuilder.getSignInRequestData(emailId, password);
    }

    @OnClick(R.id.tv_forward_password)
    void forgotPasswordTvClick() {
        replaceFragment(ForgotPasswordFragment.newInstance(), true, ForgotPasswordFragment.TAG);
    }
}
