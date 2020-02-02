package com.letzunite.letzunite.di.builder;

import com.letzunite.letzunite.ui.blood.BloodActivity;
import com.letzunite.letzunite.ui.blood.BloodActivityModule;
import com.letzunite.letzunite.ui.main_home.MainActivity;
import com.letzunite.letzunite.ui.main_home.MainActivityModule;
import com.letzunite.letzunite.ui.splash.SplashActivity;
import com.letzunite.letzunite.ui.tour.AppIntroActivity;
import com.letzunite.letzunite.ui.user.LoginActivity;
import com.letzunite.letzunite.ui.user.LoginActivityModule;
import com.letzunite.letzunite.ui.user.UserModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Deven Singh on 4/14/2018.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {LoginActivityModule.class, UserModule.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract AppIntroActivity bindAppIntroActivity();

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = BloodActivityModule.class)
    abstract BloodActivity bindBloodActivity();

}
