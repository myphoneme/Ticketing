package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;

public class TicketPostViewResponse {
    @SerializedName("message")
    private String message;

    public String getMessage(){
        return this.message;
    }
}
