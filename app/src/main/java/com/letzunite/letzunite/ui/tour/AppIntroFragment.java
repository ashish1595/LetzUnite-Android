package com.letzunite.letzunite.ui.tour;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.letzunite.letzunite.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deven Singh on 29-04-2016.
 */
public class AppIntroFragment extends Fragment {

    private static final String PAGE = "page";
    private int mPage;
    @BindView(R.id.iv_center)
    ImageView centerIv;
    @BindView(R.id.tv_pager_texts)
    TextView pagerTextsTv;

    public static AppIntroFragment newInstance(int page) {
        AppIntroFragment frag = new AppIntroFragment();
        Bundle b = new Bundle();
        b.putInt(PAGE, page);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_tour_pager, container, false);
        ButterKnife.bind(this, view);
        view.setTag(mPage);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (mPage){
            case 0:
                centerIv.setImageResource(R.mipmap.img_heart_circle);
                pagerTextsTv.setText("Blood Anytime, Anywhere");
                break;
            case 1:
                centerIv.setImageResource(R.mipmap.img_people_circle);
                pagerTextsTv.setText("Blood Anytime, Anywhere");
                break;
            case 2:
                centerIv.setImageResource(R.mipmap.img_hand_circle);
                pagerTextsTv.setText("Blood Anytime, Anywhere");
                break;
            case 3:
                centerIv.setImageResource(R.mipmap.img_gift_circle);
                pagerTextsTv.setText("Blood Anytime, Anywhere");
                break;
        }
    }
}
