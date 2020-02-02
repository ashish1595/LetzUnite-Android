package com.letzunite.letzunite.utils;

import android.arch.lifecycle.LiveData;

/**
 * Created by Deven Singh on 4/24/2018.
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T>LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
