package com.letzunite.letzunite.ui.search;

import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.data.repository.SearchBloodRepository;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.SharedPrefs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Deven Singh on 31 Jul, 2018.
 */
@Module
public class SearchBloodModule {

    @Provides
    SearchBloodRepository provideSearchBloodRepository(Resource resource, ApiService apiService, SharedPrefs sharedPrefs) {
        return new SearchBloodRepository(resource, apiService,sharedPrefs);
    }

    @Provides
    ViewModel provideSearchBloodViewModel(SearchBloodRepository repository) {
        return new SearchBloodViewModel(repository);
    }

    @Provides
    ViewModelProviderFactory provideViewModelProviderFactory(ViewModel viewModel) {
        return new ViewModelProviderFactory(viewModel);
    }
}
