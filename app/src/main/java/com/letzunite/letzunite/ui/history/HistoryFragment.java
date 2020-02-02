package com.letzunite.letzunite.ui.history;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.common.BaseActivityModule;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.ui.common.ViewPagerAdapter;
import com.letzunite.letzunite.ui.main_home.MainActivity;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
public class HistoryFragment extends BaseFragment {

    public static final String TAG = "HistoryFragment";
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Named(BaseActivityModule.BaseDeclarations.ACTIVITY_SUPPORT_FRAGMENT_MANAGER)
    @Inject
    FragmentManager fragmentManager;

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    protected ViewModel getViewModel() {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).lockAppBarClosed();
        ((MainActivity) getActivity()).setToolbarTitle("History");
        ((MainActivity) getActivity()).setBehaviorOverLapTop(56);
        setUpTabLayout();
    }

    private void setUpTabLayout() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFrag(BloodDonatedFragment.newInstance(), "Blood Donated");
        viewPagerAdapter.addFrag(BloodReceivedFragment.newInstance(), "Blood Received");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
