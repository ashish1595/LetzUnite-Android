package com.letzunite.letzunite.ui.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.data.database.LetzUniteDatabase;
import com.letzunite.letzunite.ui.feeds.FeedsFragment;
import com.letzunite.letzunite.ui.splash.SplashActivity;
import com.letzunite.letzunite.utils.AppConstants;
import com.letzunite.letzunite.utils.Logger;
import com.letzunite.letzunite.utils.SharedPrefs;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by Deven Singh on 4/12/2018.
 */
public abstract class BaseActivity extends DaggerAppCompatActivity {

    protected abstract
    @LayoutRes
    int getLayoutId();

    protected abstract
    @IdRes
    int getBaseContainerId();

    protected int baseContainerId = R.id.base_main_container;
    protected int baseActivityLayout = R.layout.activity_base;
    protected Toolbar toolbar;

    @Inject
    protected SharedPrefs sharedPrefs;
    @Inject
    protected LetzUniteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setUpToolbar();
    }

    private void setUpToolbar() {
        if (getBaseContainerId() != baseContainerId) return;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(AppConstants.ToolbarTitle.LETZ_UNITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBackStack, String tag) {
        replaceFragment(fragment, isAddToBackStack, tag, false, null, null);
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBackStack, String tag,
                                boolean isAddSharedElement, ArrayList<View> sharedElementView, ArrayList<String> transitionName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isAddSharedElement) {
            int count = sharedElementView.size();
            for (int i = 0; i < count; i++) {
                transaction.addSharedElement(sharedElementView.get(i), transitionName.get(i));
            }
        }
        transaction.replace(getBaseContainerId(), fragment, tag);
        if (isAddToBackStack)
            transaction.addToBackStack(tag);
        transaction.commit();
    }

    public void replaceFragmentWithPrevious(Fragment fragment, boolean isAddToBackStack, String tag) {
        replaceFragmentWithPrevious(fragment, isAddToBackStack, tag, false, null, null);
    }

    public void replaceFragmentWithPrevious(Fragment fragment, boolean isAddToBackStack, String tag,
                                            boolean isAddSharedElement, ArrayList<View> sharedElementView,
                                            ArrayList<String> transitionName) {
        boolean fragmentPopped = getSupportFragmentManager().popBackStackImmediate(tag, 0);
        if (!fragmentPopped) {
            replaceFragment(fragment, isAddToBackStack, tag, isAddSharedElement, sharedElementView, transitionName);
        }
    }

    public synchronized void replaceFragmentWithBackStack(Fragment fragment, boolean isAddToBackStack, String tag) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0 && getSupportFragmentManager().getBackStackEntryAt(count - 1).getName().equalsIgnoreCase(tag))
        return;
        if (tag.equalsIgnoreCase(FeedsFragment.TAG)) {
            replaceFragmentWithPrevious(fragment, isAddToBackStack, tag);
            return;
        }
        if (count > 2) {
            for (int i = count - 1; i > 0; i--) {
                if (getSupportFragmentManager().getBackStackEntryAt(i).getName().equalsIgnoreCase(tag)) {
                    replaceFragmentWithPrevious(fragment, isAddToBackStack, tag);
                    return;
                }
            }
            replaceFragment(fragment, isAddToBackStack, tag);
        } else {
            replaceFragment(fragment, isAddToBackStack, tag);
        }
    }

    public void showSnackBar(String message, boolean isError) {
        View view = findViewById(android.R.id.content);
        try {
            Snackbar snackbar;
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(isError ? Color.RED : ContextCompat.getColor(this, R.color.color_accent));
            TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
        }
    }

    public void showToast(String toastText) {
        showToast(toastText, false);
    }

    public void showToast(String toastText, boolean isLengthLong) {
        if (isLengthLong)
            Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }

    protected void logout() {
        try {
            sharedPrefs.clearAllData();
            database.clearAllTables();
            finishAffinity();
            startActivity(new Intent(this, SplashActivity.class));
        } catch (Exception e) {
            Logger.error("Exception while logout: " + e);
            throw new IllegalStateException("Logout Exception");
        }
    }
}
