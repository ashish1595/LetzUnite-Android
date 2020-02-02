package com.letzunite.letzunite.ui.main_home;

import android.support.v7.app.AppCompatActivity;

import com.letzunite.letzunite.ui.common.BaseActivityModule;
import com.letzunite.letzunite.ui.feeds.FeedModule;
import com.letzunite.letzunite.ui.feeds.FeedsFragment;
import com.letzunite.letzunite.ui.history.BloodDonatedFragment;
import com.letzunite.letzunite.ui.history.BloodReceivedFragment;
import com.letzunite.letzunite.ui.history.HistoryFragment;
import com.letzunite.letzunite.ui.history.HistoryModule;
import com.letzunite.letzunite.ui.profile.ProfileFragment;
import com.letzunite.letzunite.ui.search.BloodSearchFragment;
import com.letzunite.letzunite.ui.search.SearchBloodModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Deven Singh on 07 Jun, 2018.
 */
@Module(includes = BaseActivityModule.class)
public abstract class MainActivityModule {

    @Binds
    abstract AppCompatActivity provideActivity(MainActivity mainActivity);

    @ContributesAndroidInjector(modules = FeedModule.class)
    abstract FeedsFragment bindFeedsFragment();

    @ContributesAndroidInjector(modules = HistoryModule.class)
    abstract HistoryFragment bindHistoryFragment();

    @ContributesAndroidInjector
    abstract BloodDonatedFragment bindBloodDonatedFragment();

    @ContributesAndroidInjector
    abstract BloodReceivedFragment bindBloodReceivedFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment bindProfileFragment();

    @ContributesAndroidInjector(modules = SearchBloodModule.class)
    abstract BloodSearchFragment bindRewardsFragment();

}
