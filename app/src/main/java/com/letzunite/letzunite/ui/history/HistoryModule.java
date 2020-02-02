package com.letzunite.letzunite.ui.history;

import android.support.v4.app.FragmentManager;

import com.letzunite.letzunite.ui.common.ViewPagerAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.letzunite.letzunite.ui.common.BaseActivityModule.BaseDeclarations.ACTIVITY_SUPPORT_FRAGMENT_MANAGER;

/**
 * Created by Deven Singh on 10 Jul, 2018.
 */
@Module/*(includes = HistoryModule.HistoryFragmentBuilder.class)*/
public class HistoryModule {

//    @Module
//    public abstract class HistoryFragmentBuilder {
//
//        @ContributesAndroidInjector
//        abstract BloodDonatedFragment bindBloodDonatedFragment();
//
//        @ContributesAndroidInjector
//        abstract BloodReceivedFragment bindBloodReceivedFragment();
//
//    }

    @Provides
    ViewPagerAdapter provideViewPagerAdapter(@Named(ACTIVITY_SUPPORT_FRAGMENT_MANAGER) FragmentManager manager) {
        return new ViewPagerAdapter(manager);
    }
}
