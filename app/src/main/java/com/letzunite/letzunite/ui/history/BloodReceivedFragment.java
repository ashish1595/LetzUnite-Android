package com.letzunite.letzunite.ui.history;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.Fragment;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.common.BaseFragment;

/**
 * Created by Deven Singh on 10 Jul, 2018.
 */
public class BloodReceivedFragment extends BaseFragment {

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_blood_received;
    }

    @Override
    protected ViewModel getViewModel() {
        return null;
    }

    public static BloodReceivedFragment newInstance() {
        return new BloodReceivedFragment();
    }
}
