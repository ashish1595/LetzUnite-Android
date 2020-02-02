package com.letzunite.letzunite.ui.feeds;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.letzunite.letzunite.data.repository.FeedsRepository;
import com.letzunite.letzunite.pojo.common.ResponseResource;
import com.letzunite.letzunite.pojo.feeds.Feeds;

import java.util.List;

/**
 * Created by Deven Singh on 09 Jun, 2018.
 */
public class FeedViewModel extends ViewModel {

    private final FeedsRepository feedsRepository;

    private LiveData<ResponseResource<List<Feeds>>> feedsResponse;
    private final MutableLiveData<Integer> feedsInputData = new MutableLiveData<>();

    public FeedViewModel(FeedsRepository feedsRepository) {
        this.feedsRepository = feedsRepository;
        feedsResponse = Transformations.switchMap(feedsInputData, input ->
                feedsRepository.getFeeds(input));
    }

    public LiveData<ResponseResource<List<Feeds>>> getFeeds() {
        return feedsResponse;
    }

    public void fetchFeeds(Integer type) {
        feedsInputData.postValue(type);
    }

    @Override
    protected void onCleared() {
        feedsRepository.cancelAllTasks();
        super.onCleared();
    }
}
