package com.letzunite.letzunite.data.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Deven Singh on 02 Jun, 2018.
 */
@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    long id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "address")
    String address;

    @ColumnInfo(name = "location")
    String location;

    @ColumnInfo(name="email_id")
    String emailId;

    @ColumnInfo(name="mobile_number")
    String mobileNumber;

    @ColumnInfo(name="user_avatar_url")
    String userAvatarUrl;

    @ColumnInfo(name = "status")
    Integer status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
