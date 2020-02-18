package com.phoneme.ticketing.user.network;
import com.google.gson.annotations.SerializedName;

public class PhonePostResponse {
    @SerializedName("message")
    private String message;

    public String getMessage(){
        return this.message;
    }
}
