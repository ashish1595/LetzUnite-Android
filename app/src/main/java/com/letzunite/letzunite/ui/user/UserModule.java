package com.letzunite.letzunite.ui.user;

import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.ViewModelProviderFactory;
import com.letzunite.letzunite.data.manager.EncryptedRequestGenerator;
import com.letzunite.letzunite.data.repository.UserRepository;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.utils.Resource;
import com.letzunite.letzunite.utils.SharedPrefs;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Deven Singh on 4/13/2018.
 */
@Module
public class UserModule {

    @Provides
    UserRepository provideLoginRepository(Resource resource, ApiService apiService, SharedPrefs sharedPrefs,EncryptedRequestGenerator requestGenerator) {
        return new UserRepository(resource,apiService, sharedPrefs, requestGenerator);
    }

    @Provides
    ViewModel provideLoginViewModel(UserRepository repository) {
        return new UserViewModel(repository);
    }

    @Provides
    ViewModelProviderFactory provideViewModelProviderFactory(ViewModel viewModel) {
        return new ViewModelProviderFactory(viewModel);
    }
}
