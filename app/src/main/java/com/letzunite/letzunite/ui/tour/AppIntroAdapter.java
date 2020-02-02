package com.letzunite.letzunite.ui.tour;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Deven Singh on 09-01-2016.
 */
public class AppIntroAdapter extends FragmentPagerAdapter {

    private int PAGE_NUM_ITEMS = 4;

    public AppIntroAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return AppIntroFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return PAGE_NUM_ITEMS;
    }

}
