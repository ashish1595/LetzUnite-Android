package com.letzunite.letzunite.ui.blood;

import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.data.manager.EncryptedRequestGenerator;
import com.letzunite.letzunite.data.repository.BaseRepository;
import com.letzunite.letzunite.data.repository.BloodRepository;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.SharedPrefs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Deven Singh on 18 Jun, 2018.
 */
@Module
public class BloodModule {


    @Provides
    BloodRepository provideFeedsRepository(Resource resource, ApiService apiService, SharedPrefs sharedPrefs, EncryptedRequestGenerator requestGenerator) {
        return new BloodRepository(resource, apiService, sharedPrefs, requestGenerator);
    }

    @Provides
    ViewModel provideFeedViewModel(BloodRepository repository) {
        return new BloodViewModel(repository);
    }

    @Provides
    ViewModelProviderFactory provideViewModelProviderFactory(ViewModel viewModel) {
        return new ViewModelProviderFactory(viewModel);
    }
}
