package com.letzunite.letzunite.ui.blood;

import android.support.v7.app.AppCompatActivity;

import com.letzunite.letzunite.ui.common.BaseActivityModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Deven Singh on 01 Jul, 2018.
 */
@Module(includes = BaseActivityModule.class)
public abstract class BloodActivityModule {

    @Binds
    abstract AppCompatActivity provideActivity(BloodActivity bloodActivity);

    @ContributesAndroidInjector(modules = BloodModule.class)
    abstract BloodFragment bindBloodFragment();
}
