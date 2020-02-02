package com.letzunite.letzunite.ui.user;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.Validator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Deven Singh on 28 Jun, 2018.
 */
public class ForgotPasswordFragment extends BaseFragment<UserViewModel> {

    public final static String TAG = "ForgotPasswordFragment";

    @BindView(R.id.layout_forgot_password)
    ConstraintLayout forgotPasswordCl;
    @BindView(R.id.layout_reset_password)
    ConstraintLayout resetPasswordCl;
    @BindView(R.id.til_user_id)
    TextInputLayout userIdTil;
    @BindView(R.id.tiet_user_id)
    TextInputEditText userIdTiet;
    @BindView(R.id.bt_send_otp)
    Button sendOtpBt;
    @BindView(R.id.til_otp)
    TextInputLayout otpTil;
    @BindView(R.id.tiet_otp)
    TextInputLayout otpTiet;
    @BindView(R.id.til_password)
    TextInputLayout passwordTil;
    @BindView(R.id.tiet_password)
    TextInputLayout passwordTiet;
    @BindView(R.id.til_confirm_password)
    TextInputLayout confirmPasswordTil;
    @BindView(R.id.tiet_confirm_password)
    TextInputLayout confirmPasswordTiet;

    @Inject
    Resource resource;
    @Inject
    Validator validator;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;
    private UserViewModel mViewModel;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_forgot_password;
    }

    @Override
    protected UserViewModel getViewModel() {
        return mViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(UserViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick(R.id.bt_send_otp)
    void onSendOtpButtonClick() {
        String emailId = userIdTiet.getText().toString().trim();
        if (validator.isValidEmailAddress(emailId)) {
            // TODO: 6/28/2018 call API to send otp
        } else {
            userIdTil.setError(resource.toString(validator.isEmptyString(emailId) ? R.string.error_empty_field : R.string.invalid_email_id));
        }
    }
}
