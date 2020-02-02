package com.letzunite.letzunite.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.letzunite.letzunite.data.database.dao.AboutDao;
import com.letzunite.letzunite.data.database.dao.UserDao;
import com.letzunite.letzunite.data.database.entities.LetzUniteAbout;
import com.letzunite.letzunite.data.database.entities.User;

import javax.inject.Singleton;

/**
 * Created by Deven Singh on 02 Jun, 2018.
 */
@Singleton
@Database(entities = {LetzUniteAbout.class, User.class}, version = 1)
public abstract class LetzUniteDatabase extends RoomDatabase {

    public abstract AboutDao aboutDao();

    public abstract UserDao userDao();
}
