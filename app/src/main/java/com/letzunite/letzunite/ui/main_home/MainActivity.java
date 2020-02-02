package com.letzunite.letzunite.ui.main_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.blood.BloodActivity;
import com.letzunite.letzunite.ui.common.BaseActivity;
import com.letzunite.letzunite.ui.custom_views.BottomNavView;
import com.letzunite.letzunite.ui.feeds.FeedsFragment;
import com.letzunite.letzunite.ui.history.HistoryFragment;
import com.letzunite.letzunite.ui.profile.ProfileFragment;
import com.letzunite.letzunite.ui.search.BloodSearchFragment;
import com.letzunite.letzunite.utils.AppUtils;
import com.letzunite.letzunite.utils.Resource;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.letzunite.letzunite.ui.common.BaseActivityModule.BaseDeclarations.ACTIVITY_SUPPORT_FRAGMENT_MANAGER;

/**
 * Created by Deven Singh on 4/9/2018.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_nav_view)
    BottomNavView bottomNavView;
    @BindView(R.id.appbar)
    public AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.image_app_bar)
    public ImageView appBarImg;
    @BindView(R.id.toolbar_home)
    Toolbar toolbarHome;
    @BindView(R.id.fl_container)
    FrameLayout containerFl;

    @Inject
    Resource resource;
    @Inject
    AppUtils appUtils;
    @Named(ACTIVITY_SUPPORT_FRAGMENT_MANAGER)
    @Inject
    FragmentManager fragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getBaseContainerId() {
        return R.id.fl_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!sharedPrefs.isUserLoggedIn()) sharedPrefs.setUserLoggedIn(true);
        bottomNavView.setOnBottomNavViewItemSelectedListener(mOnBottomNavItemSelected);
        bottomNavView.setSelectedItemPosition(0);
//        lockAppBarClosed();
    }

    public void setToolbarTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
        toolbarHome.setTitle("");
    }

    public void setBehaviorOverLapTop(){
        setBehaviorOverLapTop(-1);
    }

    public void setBehaviorOverLapTop(int overlayTopInDp){
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) containerFl.getLayoutParams();
        AppBarLayout.ScrollingViewBehavior behavior =
                (AppBarLayout.ScrollingViewBehavior) params.getBehavior();
        behavior.setOverlayTop(overlayTopInDp==-1?(int) appUtils.convertDpToPixel(64):(int) appUtils.convertDpToPixel(overlayTopInDp));
    }

    public void lockAppBarClosed() {
        appBarLayout.setExpanded(false, true);
        appBarLayout.setActivated(false);
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams();
//        lp.height = (int) resource.toDimen(R.dimen.toolbar_height);
        appBarImg.setVisibility(View.GONE);
    }

    public void unlockAppBarOpen() {
        appBarLayout.setExpanded(true, true);
        appBarLayout.setActivated(false);
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)appBarLayout.getLayoutParams();
//        lp.height = (int) getResources().getDimension(R.dimen.toolbar_expand_height);
        appBarImg.setVisibility(View.VISIBLE);
//        appBarImg.setImageResource(R.drawable.bg_toolbar);
//        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.
//                LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.height= (int) resource.toDimen(R.dimen.toolbar_expand_height);
//        appBarImg.setLayoutParams(layoutParams);
    }

    private BottomNavView.OnBottomNavViewItemSelectedListener mOnBottomNavItemSelected = (view, position) -> {
        switch (position) {
            case 0:
                replaceFragmentWithBackStack(new FeedsFragment(), true, FeedsFragment.TAG);
                return;
            case 1:
                replaceFragmentWithBackStack(new BloodSearchFragment(), true, BloodSearchFragment.TAG);
                return;
            case 2:
                navigateToBloodRequestActivity(view);
//                replaceFragmentWithBackStack(new BloodFragment(), true, BloodFragment.TAG);
                return;
            case 3:
                replaceFragmentWithBackStack(new HistoryFragment(), true, HistoryFragment.TAG);
                return;
            case 4:
                replaceFragmentWithBackStack(new ProfileFragment(), true, ProfileFragment.TAG);
                return;
        }
    };

    private void navigateToBloodRequestActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (appUtils.getDeviceHeight() - view.getHeight() / 2);

        Intent intent = new Intent(this, BloodActivity.class);
        intent.putExtra(BloodActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(BloodActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        int index = fragmentManager.getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(index);
        String tag = backEntry.getName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (index == 0 || fragment instanceof FeedsFragment) {
            finishAffinity();
        } else {
            bottomNavView.setSelectedItemPosition(0);
        }
    }
}
