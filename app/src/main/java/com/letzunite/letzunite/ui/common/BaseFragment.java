package com.letzunite.letzunite.ui.common;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letzunite.letzunite.utils.AppConstants;
import com.letzunite.letzunite.utils.AppUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by Deven Singh on 4/11/2018.
 */

public abstract class BaseFragment<V extends ViewModel> extends DaggerFragment {

    /**
     * provided by implemented subclass
     *
     * @return
     */
    protected abstract
    @LayoutRes
    int getFragLayoutId();

    protected abstract V getViewModel();

    protected boolean isFragAlreadyExist = true;

    @Inject
   protected AppUtils appUtils;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel();
        isFragAlreadyExist = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected void setToolbarTitle(String titleText) {
        getBaseActivity().toolbar.setTitle(titleText);
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBackStack, String tag) {
        replaceFragment(fragment, isAddToBackStack, false, tag, null, null);
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBackStack,
                                boolean isAddSharedElement, String tag, ArrayList<View> sharedElementView, ArrayList<String> transitionName) {
        getBaseActivity().replaceFragment(fragment, isAddToBackStack, tag, isAddSharedElement, sharedElementView, transitionName);
    }

    public void replaceFragmentWithPrevious(Fragment fragment, boolean isAddToBackStack, String tag) {
        getBaseActivity().replaceFragmentWithPrevious(fragment, isAddToBackStack, tag);
    }

    protected void showToast(String toastText) {
        showToast(toastText, false);
    }

    protected void showToast(String toastText, boolean isLengthLong) {
        getBaseActivity().showToast(toastText, isLengthLong);
    }


    protected void showSnackBar(String message, boolean isError) {
        getBaseActivity().showSnackBar(message, isError);
    }

    protected void showSnackBar(String message) {
        showSnackBar(message, false);
    }

}
