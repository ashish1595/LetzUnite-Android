package com.letzunite.letzunite.pojo.feeds;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
public class Feeds {

    @SerializedName("notificationTypeId")
    private Integer notificationTypeId;
    @SerializedName("notificationDetailList")
    private List<NotificationDetails> notificationDetailsList = null;
    @SerializedName("notificationDetail")
    private NotificationDetails notificationDetails;

    public Integer getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(Integer notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public List<NotificationDetails> getNotificationDetailsList() {
        return notificationDetailsList;
    }

    public void setNotificationDetailsList(List<NotificationDetails> notificationDetailsList) {
        this.notificationDetailsList = notificationDetailsList;
    }

    public NotificationDetails getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(NotificationDetails notificationDetails) {
        this.notificationDetails = notificationDetails;
    }
}
