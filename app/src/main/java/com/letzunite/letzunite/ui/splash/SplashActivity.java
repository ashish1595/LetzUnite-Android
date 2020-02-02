package com.letzunite.letzunite.ui.splash;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.main_home.MainActivity;
import com.letzunite.letzunite.ui.tour.AppIntroActivity;
import com.letzunite.letzunite.utils.SharedPrefs;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by Deven Singh on 15 Apr, 2018.
 */
public class SplashActivity extends DaggerAppCompatActivity {

    @BindView(R.id.iv_logo_part1)
    ImageView logoPart1Iv;
    @BindView(R.id.iv_logo_part2)
    ImageView logoPart2Iv;

    @Inject
    SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        new Handler().postDelayed(() -> runOnUiThread(() -> {
            if (sharedPrefs.isUserLoggedIn())
                navigateToHomePage();
            else navigateToTourPage();
        }),3000);
    }

    public void navigateToTourPage() {
        Intent intent = new Intent(SplashActivity.this, AppIntroActivity.class);
        Pair<View, String> p1 = Pair.create(logoPart1Iv, logoPart1Iv.getTransitionName());
        Pair<View, String> p2 = Pair.create(logoPart2Iv, logoPart2Iv.getTransitionName());
        ActivityOptions options = ActivityOptions.
                makeSceneTransitionAnimation(this, p1, p2);
//        getWindow().setEnterTransition(new Explode());
//        getWindow().setExitTransition(new Explode());
        startActivity(intent, options.toBundle());
    }

    public void navigateToHomePage() {
         startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }
}
