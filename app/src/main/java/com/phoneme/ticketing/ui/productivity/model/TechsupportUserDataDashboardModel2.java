package com.phoneme.ticketing.ui.productivity.model;

import com.google.gson.annotations.SerializedName;

public class TechsupportUserDataDashboardModel2 {
    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("totalopenticketcount")
    private int totalopenticketcount;

    @SerializedName("totalcloseticketcount")
    private int totalcloseticketcount;

    @SerializedName("totalavgcount")
    private int totalavgcount;

    @SerializedName("color")
    private String color;

    @SerializedName("role")
    private String role;

    @SerializedName("id")
    private String id;

    @SerializedName("mobile_no")
    private String mobile_no;

    public String getMobile_no(){
        return this.mobile_no;
    }

    @SerializedName("created")
    private String created;

    @SerializedName("status")
    private String status;

    @SerializedName("email")
    private String email;

    public String getEmail(){
        return this.email;
    }
    @SerializedName("password")
    private String password;

    @SerializedName("created_by")
    private String create_by;

    @SerializedName("designation")
    private String designation;

    @SerializedName("totalopenticketsrightnow")
    private String totalopenticketsrightnow;
}
