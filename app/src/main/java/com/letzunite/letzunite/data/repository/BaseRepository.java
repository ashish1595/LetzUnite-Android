package com.letzunite.letzunite.data.repository;

import com.letzunite.letzunite.data.Task;
import com.letzunite.letzunite.utils.AppConstants;
import com.letzunite.letzunite.utils.SharedPrefs;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Deven Singh on 4/22/2018.
 */

public abstract class BaseRepository {

    public HashMap<Task, Call> callHashMap;
    private final SharedPrefs sharedPrefs;

    public BaseRepository(SharedPrefs sharedPrefs) {
        this.sharedPrefs=sharedPrefs;
        callHashMap = new HashMap<>();
    }

    protected Map<String,String> getHeaders(){
        Map<String,String> headerMap=new HashMap<>();
        headerMap.put(AppConstants.Keys.AUTH_TOKEN,sharedPrefs.getToken());
        headerMap.put(AppConstants.Keys.USER_ID,sharedPrefs.getUserId());
        return headerMap;
    }

    public void cancelTask(Task taskId) {
        if (callHashMap != null && !callHashMap.isEmpty()) {
            callHashMap.get(taskId).cancel();
        }
    }

    public void cancelAllTasks() {
        if (callHashMap != null && !callHashMap.isEmpty()) {
            for (Task task : callHashMap.keySet()) {
                callHashMap.get(task).cancel();
            }
        }
    }
}
