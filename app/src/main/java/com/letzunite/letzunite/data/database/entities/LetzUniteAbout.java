package com.letzunite.letzunite.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Deven Singh on 4/11/2018.
 */
@Entity(tableName = "about_table")
public class LetzUniteAbout {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;

    @ColumnInfo(name = "name")
    String name;
}
