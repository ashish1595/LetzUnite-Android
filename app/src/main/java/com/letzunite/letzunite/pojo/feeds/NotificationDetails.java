package com.letzunite.letzunite.pojo.feeds;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
public class NotificationDetails {

    @SerializedName("userId")
    private String userId;
    @SerializedName("notificationTypeId")
    private Integer notificationTypeId;
    @SerializedName("data")
    private FeedData feedData;
    @SerializedName("location")
    private List<Double> location=null;
    @SerializedName("readFlag")
    private String readFlag;
    @SerializedName("createdDate")
    private String createdDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(Integer notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public FeedData getFeedData() {
        return feedData;
    }

    public void setFeedData(FeedData feedData) {
        this.feedData = feedData;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
