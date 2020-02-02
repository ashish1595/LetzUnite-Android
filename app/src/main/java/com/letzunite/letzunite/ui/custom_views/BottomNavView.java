package com.letzunite.letzunite.ui.custom_views;

import android.content.Context;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.letzunite.letzunite.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Deven Singh on 30 Jun, 2018.
 */
public class BottomNavView extends ConstraintLayout {

    private View mContainer;
    @BindView(R.id.ll_home)
    LinearLayout homeLl;
    @BindView(R.id.ll_history)
    LinearLayout historyLl;
    @BindView(R.id.iv_blood_request)
    ImageView bloodRequestIv;
    @BindView(R.id.ll_search)
    LinearLayout searchLl;
    @BindView(R.id.ll_profile)
    LinearLayout profileLl;
    @BindView(R.id.iv_home)
    ImageView homeIv;
    @BindView(R.id.iv_history)
    ImageView historyIv;
    @BindView(R.id.iv_search)
    ImageView searchIv;
    @BindView(R.id.iv_profile)
    ImageView profileIv;
    @BindView(R.id.tv_home)
    TextView homeTv;
    @BindView(R.id.tv_history)
    TextView historyTv;
    @BindView(R.id.tv_search)
    TextView searchTv;
    @BindView(R.id.tv_profile)
    TextView profileTv;
    private OnBottomNavViewItemSelectedListener itemSelectedListener;
    private int lastItemSelected = -1;
    private int currentSelectedItem = -1;
    private boolean isAnimationContinue;
    private Animation animScaleDown;
    private Animation animScaleUp;

    private Resources resources;

    public interface OnBottomNavViewItemSelectedListener {
        void onItemSelected(View view, int position);
    }

    public BottomNavView(Context context) {
        super(context);
        initView(context);
    }

    public BottomNavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BottomNavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setOnBottomNavViewItemSelectedListener(OnBottomNavViewItemSelectedListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    private void initView(Context context) {
        mContainer = LayoutInflater.from(context).inflate(R.layout.layout_bottom_nav_view, this, true);
        ButterKnife.bind(mContainer);
        resources = getResources();
        animScaleDown = AnimationUtils.loadAnimation(context, R.anim.side_scale_down);
        animScaleUp = AnimationUtils.loadAnimation(context, R.anim.side_scale_up);
        animScaleUp.setAnimationListener(mAnimScaleUpAnimationListener);
        animScaleDown.setAnimationListener(mAnimScaleDownAnimationListener);
    }

    @OnClick(R.id.ll_home)
    void onHomeClick() {
        setSelectedItemPosition(0);
    }


    @OnClick(R.id.ll_search)
    void onSearchClick() {
        setSelectedItemPosition(1);
    }


    @OnClick(R.id.iv_blood_request)
    void onBloodRequestIvClick() {
        setSelectedItemPosition(2);
    }


    @OnClick(R.id.ll_history)
    void onHistoryClick() {
        setSelectedItemPosition(3);
    }


    @OnClick(R.id.ll_profile)
    void onProfileClick() {
        setSelectedItemPosition(4);
    }

    public void setSelectedItemPosition(int position) {
        if (isAnimationContinue || lastItemSelected == position) return;
        updateSelectedItemUi(position);
    }

    private void updateSelectedItemUi(int position) {
        if (position == 2) {
            itemSelectedListener.onItemSelected(bloodRequestIv, position);
            return;
        }
        currentSelectedItem = position;
        isAnimationContinue = true;
        updateItemImageUi(lastItemSelected, false);
        switch (position) {
            case 0:
                homeIv.startAnimation(animScaleDown);
                break;
            case 1:
                searchIv.startAnimation(animScaleDown);
                break;
            case 3:
                historyIv.startAnimation(animScaleDown);
                break;
            case 4:
                profileIv.startAnimation(animScaleDown);
                break;
        }
    }

    private void updateItemImageUi(int position, boolean isCurrentSelected) {
        if (position == -1) return;
        switch (position) {
            case 0:
                homeIv.setImageResource(isCurrentSelected ? R.mipmap.ic_home_selected : R.mipmap.ic_home);
                homeTv.setTextColor(isCurrentSelected ? resources.getColor(R.color.color_primary)
                        : resources.getColor(R.color.color_secondary_text));
                if (isCurrentSelected) homeIv.startAnimation(animScaleUp);
                break;
            case 1:
                searchIv.setImageResource(isCurrentSelected ? R.mipmap.ic_rewards_selected : R.mipmap.ic_rewards);
                searchTv.setTextColor(isCurrentSelected ? resources.getColor(R.color.color_primary)
                        : resources.getColor(R.color.color_secondary_text));
                if (isCurrentSelected) searchIv.startAnimation(animScaleUp);
                break;
            case 3:
                historyIv.setImageResource(isCurrentSelected ? R.mipmap.ic_history_selected : R.mipmap.ic_history);
                historyTv.setTextColor(isCurrentSelected ? resources.getColor(R.color.color_primary)
                        : resources.getColor(R.color.color_secondary_text));
                if (isCurrentSelected) historyIv.startAnimation(animScaleUp);
                break;
            case 4:
                profileIv.setImageResource(isCurrentSelected ? R.mipmap.ic_profile_selected : R.mipmap.ic_profile);
                profileTv.setTextColor(isCurrentSelected ? resources.getColor(R.color.color_primary)
                        : resources.getColor(R.color.color_secondary_text));
                if (isCurrentSelected) profileIv.startAnimation(animScaleUp);
                break;
        }
    }

    Animation.AnimationListener mAnimScaleUpAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            itemSelectedListener.onItemSelected(null, currentSelectedItem);
            lastItemSelected = currentSelectedItem;
            isAnimationContinue = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener mAnimScaleDownAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            updateItemImageUi(currentSelectedItem, true);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
