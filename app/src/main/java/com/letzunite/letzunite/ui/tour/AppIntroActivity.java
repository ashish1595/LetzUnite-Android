package com.letzunite.letzunite.ui.tour;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ui.common.BaseActivity;
import com.letzunite.letzunite.ui.user.LoginActivity;
import com.letzunite.letzunite.utils.AppConstants;
import com.letzunite.letzunite.utils.CirclePageIndicator;
import com.letzunite.letzunite.utils.Resource;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Deven Singh on 25 Apr, 2018.
 */
public class AppIntroActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bt_create_account)
    Button createAccountBt;
    @BindView(R.id.tv_login)
    TextView loginTv;
    @BindView(R.id.circle_page_indicator)
    CirclePageIndicator circlePageIndicator;

    @Inject
    Resource resource;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tour;
    }

    @Override
    protected int getBaseContainerId() {
        return R.id.cl_app_tour;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSharedElementEnterTransition(new TransitionSet().addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).addTransition(new ChangeClipBounds())
                .addTransition(new ChangeImageTransform()).setDuration(1000));
        getWindow().setEnterTransition(new Slide().setDuration(1000));
        super.onCreate(savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        setSpannableText();
        viewPager.setAdapter(new AppIntroAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(false, new IntroPageTransformer());
        circlePageIndicator.setViewPager(viewPager);
    }

    private void setSpannableText() {
        SpannableString spannableString=new SpannableString(resource.toString(R.string.already_has_account));
        spannableString.setSpan(new RelativeSizeSpan(1.15f),23,30,0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 23, 30, 0);
        spannableString.setSpan(clickableSpan, 23, 30, 0);
        spannableString.setSpan(new ForegroundColorSpan(resource.toColor(R.color.color_primary)), 23, 30, 0);
        loginTv.setMovementMethod(LinkMovementMethod.getInstance());
        loginTv.setText(spannableString);
    }

    ClickableSpan clickableSpan = new ClickableSpan() {

        @Override
        public void onClick(View widget) {
            navigateToNextPage(false);
        }
    };


    @OnClick(R.id.bt_create_account)
    void onCreateAccountButtonClick() {
        navigateToNextPage(true);
    }

    private void navigateToNextPage(boolean isNewUser) {
        Intent intent = new Intent(AppIntroActivity.this, LoginActivity.class);
        intent.putExtra(AppConstants.Keys.IS_NEW_USER, isNewUser);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
