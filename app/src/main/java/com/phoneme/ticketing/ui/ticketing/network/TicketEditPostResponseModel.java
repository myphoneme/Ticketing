package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;

public class TicketEditPostResponseModel {
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
