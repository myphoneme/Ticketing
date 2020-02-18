package com.phoneme.ticketing.ui.project.model;

import com.google.gson.annotations.SerializedName;

public class ProjectEditUserModel {
    @SerializedName("user_id")
    private String user_id;

    @SerializedName("user_name")
    private String user_name;

    public String getUser_name(){
        return this.user_name;
    }
    public String getUser_id(){
        return this.user_id;
    }
}
