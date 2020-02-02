package com.letzunite.letzunite.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * Created by Deven Singh on 11 Jun, 2018.
 */
public class BottomNavigationBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public BottomNavigationBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    float lastDy=0f;

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        if (lastDy<0 && dy>0){
//            lastDy=dy;
//            child.setTranslationY(0f);
//        return;
//        }
//        lastDy=dy;
        child.setTranslationY(Math.max(0f, Math.min((float) child.getHeight(), child.getTranslationY() + dy)));
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            updateSnackbar(child, (Snackbar.SnackbarLayout) dependency);
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    private void updateSnackbar(View child, Snackbar.SnackbarLayout snackbarLayout) {
        if (snackbarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarLayout.getLayoutParams();
            params.setAnchorId(child.getId());
            params.anchorGravity = Gravity.TOP;
            params.gravity = Gravity.TOP;
            snackbarLayout.setLayoutParams(params);
        }
    }
}
