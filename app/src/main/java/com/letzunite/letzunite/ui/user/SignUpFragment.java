package com.letzunite.letzunite.ui.user;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.utils.ProgressIndicator;
import com.letzunite.letzunite.utils.RequestBuilder;
import com.letzunite.letzunite.utils.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.letzunite.letzunite.utils.AppConstants.ToolbarTitle.SIGN_UP;

/**
 * Created by Deven Singh on 4/9/2018.
 */

public class SignUpFragment extends BaseFragment<UserViewModel> {


    public static final String TAG = "SignUpFragment";
    @BindView(R.id.til_user_name)
    TextInputLayout userNameTil;
    @BindView(R.id.tiet_user_name)
    TextInputEditText userNameTiet;
    @BindView(R.id.til_user_email)
    TextInputLayout emailTil;
    @BindView(R.id.tiet_user_email)
    TextInputEditText emailTiet;
    @BindView(R.id.til_user_number)
    TextInputLayout userNumberTil;
    @BindView(R.id.tiet_user_number)
    TextInputEditText userNumberTiet;
    @BindView(R.id.til_password)
    TextInputLayout passwordTil;
    @BindView(R.id.tiet_password)
    TextInputEditText passwordTiet;
    @BindView(R.id.til_confirm_password)
    TextInputLayout confirmPasswordTil;
    @BindView(R.id.tiet_confirm_password)
    TextInputEditText confirmPasswordTiet;
    @BindView(R.id.bt_create_account)
    Button createAccountBt;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    @Inject
    Validator validator;
    @Inject
    RequestBuilder requestBuilder;

    private UserViewModel mViewModel;
    private long lastClick;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    protected UserViewModel getViewModel() {
        return mViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(UserViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle(SIGN_UP);
        initComponents();
        mViewModel.getSignUpResponse().observe(this,mSignUpObserver);
    }

    private void initComponents() {
        userNameTiet.addTextChangedListener(mUserNameTextWatcher);
        emailTiet.addTextChangedListener(mEmailTextWatcher);
        userNumberTiet.addTextChangedListener(mUserNumberTextWatcher);
        passwordTiet.addTextChangedListener(mPasswordTextWatcher);
        confirmPasswordTiet.addTextChangedListener(mConfirmPasswordTextWatcher);
    }

    TextWatcher mUserNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() > 3) {
                userNameTil.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher mEmailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str = s.toString().trim();
            if (str.length() > 6) {
                emailTil.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher mUserNumberTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int str = s.toString().trim().length();
            if (str>9) {
                userNumberTil.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher mPasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() > 7) {
                passwordTil.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher mConfirmPasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String confirmPass = s.toString().trim();
            if (confirmPass.length() > 7) {
                confirmPasswordTil.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @OnClick(R.id.bt_create_account)
    void createAccountButtonClick() {
        if (System.currentTimeMillis() - lastClick < 200) return;
        lastClick = System.currentTimeMillis();
        // TODO: 5/20/2018 Validate all the fields and send to server to create user profile
        String requestData = getRequestData();
        if (requestData != null) {
           mViewModel.createAccount(requestData);
        }
    }

    private String getRequestData() {
        String name = userNameTiet.getText().toString().trim();
        if (name.length() < 3) {
            userNameTil.setError("Name should be minimum of three letters.");
            return null;
        }
        String emailId = emailTiet.getText().toString().trim();
        if (!validator.isValidEmailAddress(emailId)) {
            emailTil.setError("Invalid email address.");   // TODO: 5/20/2018 set all constants in string file

            return null;
        }
        String mobileNumber = userNumberTiet.getText().toString().trim();
        if (!mobileNumber.isEmpty() && !validator.isValidNumber(mobileNumber)) {
            userNumberTil.setError("Invalid mobile number.");
            return null;
        }
        String password = passwordTiet.getText().toString().trim();
        if (!validator.isValidPassword(password)) {
            passwordTil.setError("Not a valid password. Please make password with the combination of lower letter, capital letter, numbers and following special characters !,@,#,$,%,^,&,*,_,+,=");
            return null;
        }
        String confirmPassword = confirmPasswordTiet.getText().toString().trim();
        if (!confirmPassword.equals(password)) {
            confirmPasswordTil.setError("Password didn't match.");
            return null;
        }
        return requestBuilder.getSignUpRequestData(emailId, name, mobileNumber, password);
    }

    Observer<ResponseResource<String>> mSignUpObserver= responseResource -> {
        if (responseResource==null) return;
        switch (responseResource.status){
            case LOADING:
                ProgressIndicator.getDefault().showProgressIndicator(getBaseActivity(),SIGN_UP);
                break;
            case SUCCESS:
                showSnackBar(responseResource.data);
                ProgressIndicator.getDefault().dismiss(SIGN_UP);
                break;
            case ERROR:
                showSnackBar(responseResource.message,true);
                ProgressIndicator.getDefault().dismiss(SIGN_UP);
                break;
        }
    };
}
