package com.phoneme.ticketing.ui.user.network;

import com.google.gson.annotations.SerializedName;

public class UserAddPostResponse {
    @SerializedName("added")
    private Boolean added;

    @SerializedName("message")
    private String message;

    public String getMessage(){
        return this.message;
    }
    public Boolean getAdded(){
        return this.added;
    }
}
