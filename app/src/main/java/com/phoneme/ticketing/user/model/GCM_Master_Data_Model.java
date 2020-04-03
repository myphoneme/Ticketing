package com.phoneme.ticketing.user.model;

import com.google.gson.annotations.SerializedName;

public class GCM_Master_Data_Model {
    @SerializedName("Id")
    private String id;

    @SerializedName("Token")
    private String token;

    @SerializedName("Name")
    private String name;

    @SerializedName("Country")
    private String country;

    @SerializedName("IMEI")
    private String imei;

    @SerializedName("Software")
    private String software;

    @SerializedName("App")
    private String app;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("Model")
    private String model;

    @SerializedName("Manufacturer")
    private String manufacturer;

    @SerializedName("App_String")
    private String app_string;

    @SerializedName("Rating")
    private String rating;

    @SerializedName("Feedback")
    private String feedback;

    public String getApp_string(){
        return this.app_string;
    }
}
