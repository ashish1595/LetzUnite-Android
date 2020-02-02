package com.letzunite.letzunite.ui.common;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.letzunite.letzunite.di.PerActivity;
import com.letzunite.letzunite.utils.AppUtils;
import com.letzunite.letzunite.utils.ImageCaptureHandler;
import com.letzunite.letzunite.utils.PermissionHandler;
import com.letzunite.letzunite.utils.SharedPrefs;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Deven Singh on 07 Jun, 2018.
 */
@Module(includes = BaseActivityModule.BaseDeclarations.class)
public class BaseActivityModule {

     @Module
    public interface BaseDeclarations {

        String ACTIVITY_SUPPORT_FRAGMENT_MANAGER = "BaseActivityModule.supportFragmentManager";

        @Binds
        @PerActivity
        Context activityContext(AppCompatActivity activity);

        @Provides
        @Named(ACTIVITY_SUPPORT_FRAGMENT_MANAGER)
        static FragmentManager activityFragmentManager(AppCompatActivity activity) {
            return activity.getSupportFragmentManager();
        }
    }

    @Provides
    @PerActivity
    PermissionHandler providePermissionHandler(AppCompatActivity activity) {
        return new PermissionHandler(activity);
    }

    @PerActivity
    @Provides
    ImageCaptureHandler provideImageCaptureHandler(@PerActivity BaseActivity activity, PermissionHandler permissionHandler,
                                                   SharedPrefs sharedPrefs, AppUtils appUtils) {
        return new ImageCaptureHandler(activity, permissionHandler, sharedPrefs, appUtils);
    }
}
