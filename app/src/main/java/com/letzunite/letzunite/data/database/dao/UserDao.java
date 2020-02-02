package com.letzunite.letzunite.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.letzunite.letzunite.data.database.entities.User;

/**
 * Created by Deven Singh on 4/11/2018.
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table WHERE uid =1")
    LiveData<User> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);
}
