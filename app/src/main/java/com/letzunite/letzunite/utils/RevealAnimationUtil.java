package com.letzunite.letzunite.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 01 Jul, 2018.
 */
@Singleton
public class RevealAnimationUtil {

    @Inject
    public RevealAnimationUtil(){}

    public interface RevealAnimationListener {
        void onRevealEnd(Animator animator, boolean isReversed);
    }

    private RevealAnimationListener animationListener;
    private long duration = 600;

    public void setOnRevealAnimationListener(RevealAnimationListener animationListener) {
        this.animationListener = animationListener;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void reveal(View rootView, int centerX, int centerY) {
        float finalRadius = (float) (Math.max(rootView.getWidth(), rootView.getHeight()) * 1.1);
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootView, centerX, centerY, 0, finalRadius);
        circularReveal.setDuration(duration);
        circularReveal.setInterpolator(new AccelerateInterpolator());
        rootView.setVisibility(View.VISIBLE);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) {
                    animationListener.onRevealEnd(animation,false);
                }
            }
        });
        circularReveal.start();
    }

    public void unReveal(View rootView, int centerX, int centerY) {
        float finalRadius = (float) (Math.max(rootView.getWidth(), rootView.getHeight()) * 1.1);
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                rootView, centerX, centerY, finalRadius, 0);
        circularReveal.setDuration(duration);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.setVisibility(View.INVISIBLE);
                if (animationListener != null) {
                    animationListener.onRevealEnd(animation,true);
                }
            }
        });
        circularReveal.start();
    }

}
