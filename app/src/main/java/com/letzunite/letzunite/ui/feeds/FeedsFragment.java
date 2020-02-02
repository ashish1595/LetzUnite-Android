package com.letzunite.letzunite.ui.feeds;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.letzunite.letzunite.R;
import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.feeds.Feeds;
import com.letzunite.letzunite.ui.common.BaseFragment;
import com.letzunite.letzunite.ui.main_home.MainActivity;
import com.letzunite.letzunite.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Deven Singh on 09 Jun, 2018.
 */
public class FeedsFragment extends BaseFragment {

    public static final String TAG = "FeedsFragment";

    @BindView(R.id.rv_feeds)
    RecyclerView feedsRv;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private FeedsAdapter feedsAdapter;
    private List<Feeds> feedsList = new ArrayList<>();

    private FeedViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected int getFragLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected ViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this, providerFactory).get(FeedViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).lockAppBarClosed();
        ((MainActivity) getActivity()).setToolbarTitle("Feeds");
        initComponents();
        viewModel.getFeeds().observe(this, mObserver);
        viewModel.fetchFeeds(null);
    }

    private void initComponents() {
        feedsAdapter = new FeedsAdapter(feedsList);
        feedsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        feedsRv.setHasFixedSize(true);
        feedsRv.setAdapter(feedsAdapter);
    }

    Observer<ResponseResource<List<Feeds>>> mObserver = objectResponseResource -> {
        switch (objectResponseResource.status) {
            case LOADING:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case SUCCESS:
                progressBar.setVisibility(View.GONE);
                List<Feeds> feeds = objectResponseResource.data;
                if (!feedsList.isEmpty()) feedsList.clear();
                feedsList.addAll(feeds);
                feedsAdapter.notifyDataSetChanged();
                Logger.debug("Success", "Response: " + objectResponseResource.data);
                break;
            case ERROR:
                progressBar.setVisibility(View.GONE);
                Logger.debug("Failure", "Code: " + objectResponseResource.code +
                        "Message: " + objectResponseResource.message);
                break;
            case LOGOUT:
                progressBar.setVisibility(View.GONE);
                break;
        }
    };
}
