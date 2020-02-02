package com.letzunite.letzunite.di.component;

import com.letzunite.letzunite.LetzUnitApplication;
import com.letzunite.letzunite.di.builder.ActivityBuilder;
import com.letzunite.letzunite.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Deven Singh on 4/9/2018.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent extends AndroidInjector<LetzUnitApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<LetzUnitApplication> {
    }
}