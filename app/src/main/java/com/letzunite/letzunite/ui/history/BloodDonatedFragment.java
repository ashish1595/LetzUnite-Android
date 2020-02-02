package com.letzunite.letzunite.ui.history;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.common.BaseFragment;

/**
 * Created by Deven Singh on 10 Jul, 2018.
 */
public class BloodDonatedFragment extends BaseFragment {

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_blood_donated;
    }

    @Override
    protected ViewModel getViewModel() {
        return null;
    }

    public static BloodDonatedFragment newInstance() {
        return new BloodDonatedFragment();
    }
}
