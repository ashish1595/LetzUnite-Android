package com.letzunite.letzunite.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.Explode;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.common.BaseActivity;
import com.letzunite.letzunite.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Named;

import static com.letzunite.letzunite.ui.common.BaseActivityModule.BaseDeclarations.ACTIVITY_SUPPORT_FRAGMENT_MANAGER;

/**
 * Created by Deven Singh on 4/9/2018.
 */

public class LoginActivity extends BaseActivity {

    @Named(ACTIVITY_SUPPORT_FRAGMENT_MANAGER)
    @Inject
    FragmentManager fragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base;
    }

    @Override
    protected int getBaseContainerId() {
        return R.id.base_main_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setEnterTransition(new Explode());
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || bundle.getBoolean(AppConstants.Keys.IS_NEW_USER)) {
            replaceFragment(SignUpFragment.newInstance(), true, SignUpFragment.TAG);
        } else {
            replaceFragment(SignInFragment.newInstance(), true, SignInFragment.TAG);
        }
    }

    @Override
    public void onBackPressed() {
        int index = fragmentManager.getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
        String tag = backEntry.getName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (index == 0 || fragment instanceof SignInFragment || fragment instanceof SignUpFragment) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
