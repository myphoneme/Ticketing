package com.phoneme.ticketing.ui.ticketing.model;

import com.google.gson.annotations.SerializedName;

public class UserIdModel {
    @SerializedName("user_id")  //id
    private String id;
    public String getId(){
        return this.id;
    }
}
