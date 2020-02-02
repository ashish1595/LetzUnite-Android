package com.letzunite.letzunite.ui.blood;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.utils.ProgressIndicator;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
public class BloodFragment extends BaseFragment {

    public static final String TAG = "BloodFragment";

    @BindView(R.id.rv_available_time)
    RecyclerView availableTimeRv;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    BloodViewModel viewModel;

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_blood;
    }

    @Override
    protected ViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(BloodViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getBloodRequestResponse().observe(this, bloodRequestObserver);
    }

    Observer<ResponseResource<String>> bloodRequestObserver = observer -> {
        switch (observer.status) {
            case LOADING:
                ProgressIndicator.getDefault().showProgressIndicator(getActivity(), "BLOOD_REQUEST");
                break;
            case SUCCESS:
                showSnackBar("Success");
                ProgressIndicator.getDefault().dismiss("BLOOD_REQUEST");
                break;
            case ERROR:
                showSnackBar("failed", true);
                ProgressIndicator.getDefault().dismiss("BLOOD_REQUEST");
                break;
        }
    };
}
