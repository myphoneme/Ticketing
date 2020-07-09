package com.phoneme.ticketing.ui.user.network;

import com.google.gson.annotations.SerializedName;

public class UserPasswordChangeResponse {
    @SerializedName("message")
    private String message;

    public String getMessage(){
        return this.message;
    }
}
