package com.letzunite.letzunite.ui.user;

import android.support.v7.app.AppCompatActivity;

import com.letzunite.letzunite.di.PerActivity;
import com.letzunite.letzunite.ui.common.BaseActivityModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Deven Singh on 4/20/2018.
 */
@Module(includes = BaseActivityModule.class)
public abstract class LoginActivityModule {

    @ContributesAndroidInjector
    abstract SignInFragment bindSignInFragment();

    @ContributesAndroidInjector
    abstract SignUpFragment bindSignUpFragment();

    @ContributesAndroidInjector
    abstract ForgotPasswordFragment bindForgotPasswordFragment();

    @Binds
    abstract AppCompatActivity provideActivity(LoginActivity loginActivity);
}
