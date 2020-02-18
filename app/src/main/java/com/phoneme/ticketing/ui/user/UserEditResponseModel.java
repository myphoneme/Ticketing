package com.phoneme.ticketing.ui.user;

import com.google.gson.annotations.SerializedName;

public class UserEditResponseModel {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public boolean isStatus(){
        return this.status;
    }
    public String getMessage(){
        return this.message;
    }
}
