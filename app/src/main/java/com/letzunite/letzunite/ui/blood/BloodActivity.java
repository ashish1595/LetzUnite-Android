package com.letzunite.letzunite.ui.blood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewTreeObserver;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.common.BaseActivity;
import com.letzunite.letzunite.utils.RevealAnimationUtil;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Deven Singh on 30 Jun, 2018.
 */
public class BloodActivity extends BaseActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    private int revealX;
    private int revealY;

    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;

    @Inject
    RevealAnimationUtil revealAnimationUtil;

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
        super.onCreate(savedInstanceState);
        replaceFragment(new BloodFragment(), true, BloodFragment.TAG);
        final Intent intent = getIntent();
        if (savedInstanceState == null &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);
            revealAnimationUtil.setOnRevealAnimationListener(mRevealAnimationListener);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealAnimationUtil.reveal(rootLayout, revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }
    }

    RevealAnimationUtil.RevealAnimationListener mRevealAnimationListener = (animator,isReversed) ->{
     if (isReversed) finish();
    } ;

    @Override
    public void onBackPressed() {
        revealAnimationUtil.unReveal(rootLayout, revealX, revealY);
    }
}
