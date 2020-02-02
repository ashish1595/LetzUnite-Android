package com.letzunite.letzunite.pojo.feeds;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Deven Singh on 10 Jun, 2018.
 */
public class FeedData {

    @SerializedName("patientName")
    private String patientName;
    @SerializedName("disease")
    private String disease;
    @SerializedName("hospitalName")
    private String hospitalName;
    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("contactPersonNumber")
    private String contactPersonNumber;
    @SerializedName("bloodType")
    private String bloodType;
    @SerializedName("availableHours")
    private String availableHours;
    @SerializedName("hospitalContactPerson")
    private String hospitalContactPerson;
    @SerializedName("location")
    private List<Double> location=null;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("description")
    private String description;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public String getHospitalContactPerson() {
        return hospitalContactPerson;
    }

    public void setHospitalContactPerson(String hospitalContactPerson) {
        this.hospitalContactPerson = hospitalContactPerson;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
