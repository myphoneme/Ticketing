package com.phoneme.ticketing.user.network;

import com.google.gson.annotations.SerializedName;

public class FCMTokenUploadResponse {
    @SerializedName("success")
    private Boolean success;

    public Boolean isTokenUploaded(){
        return this.success;
    }
}
