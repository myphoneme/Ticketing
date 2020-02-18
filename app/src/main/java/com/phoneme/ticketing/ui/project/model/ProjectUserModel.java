package com.phoneme.ticketing.ui.project.model;

import com.google.gson.annotations.SerializedName;

public class ProjectUserModel {
    @SerializedName("name")
    private String user;

    @SerializedName("id")
    private String id;

    public String getUserName(){
        return this.user;
    }

    public String getId(){
        return this.id;
    }
}
