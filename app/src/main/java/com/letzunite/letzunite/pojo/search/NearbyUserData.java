package com.letzunite.letzunite.pojo.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Deven Singh on 01 Aug, 2018.
 */
public class NearbyUserData {

    @SerializedName("address")
    private String address;
    @SerializedName("id")
    private String id;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("locations")
    private List<Double> locations = null;
    @SerializedName("mobileNumber")
    private String mobileNumber;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private Integer type;
    @SerializedName("bloodType")
    private String bloodType;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Double> getLocations() {
        return locations;
    }

    public void setLocations(List<Double> locations) {
        this.locations = locations;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
