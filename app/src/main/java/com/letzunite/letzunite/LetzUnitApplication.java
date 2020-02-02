package com.letzunite.letzunite;

import com.facebook.stetho.Stetho;
import com.letzunite.letzunite.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by Deven Singh on 4/9/2018.
 */

public class LetzUnitApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected AndroidInjector<? extends LetzUnitApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

}
