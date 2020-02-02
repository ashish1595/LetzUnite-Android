package com.letzunite.letzunite.ui.profile;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.ui.main_home.MainActivity;
import com.letzunite.letzunite.utils.UserAvatar;

import butterknife.BindView;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
public class ProfileFragment extends BaseFragment {

    public static final String TAG = "ProfileFragment";

    @BindView(R.id.iv_user_avatar)
    UserAvatar userAvatar;

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    protected ViewModel getViewModel() {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).unlockAppBarOpen();
        ((MainActivity) getActivity()).setToolbarTitle("");
        ((MainActivity) getActivity()).setBehaviorOverLapTop(64);
    }
}
