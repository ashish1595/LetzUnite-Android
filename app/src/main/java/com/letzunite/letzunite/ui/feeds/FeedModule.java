package com.letzunite.letzunite.ui.feeds;

import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.data.repository.FeedsRepository;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.SharedPrefs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
@Module
public class FeedModule {

    @Provides
    FeedsRepository provideFeedsRepository(Resource resource, ApiService apiService, SharedPrefs sharedPrefs) {
        return new FeedsRepository(resource, apiService,sharedPrefs);
    }

    @Provides
    ViewModel provideFeedViewModel(FeedsRepository repository) {
        return new FeedViewModel(repository);
    }

    @Provides
    ViewModelProviderFactory provideViewModelProviderFactory(ViewModel viewModel) {
        return new ViewModelProviderFactory(viewModel);
    }
}
