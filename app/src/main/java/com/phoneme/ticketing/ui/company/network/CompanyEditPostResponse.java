package com.phoneme.ticketing.ui.company.network;

import com.google.gson.annotations.SerializedName;

public class CompanyEditPostResponse {
    @SerializedName("message")
    private String message;

    public String getMessage(){
        return this.message;
    }
}
