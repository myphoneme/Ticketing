package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;

public class TicketCreatePostResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("allowed")
    private Boolean allowed;

    @SerializedName("ticket_number")
    private String ticket_number;

    public String getTicket_number(){
        return this.ticket_number;
    }
    public Boolean isAllowed(){
        return this.allowed;
    }
    @SerializedName("message")
    private String message;

    public String getMessage(){
        return this.message;
    }

    public Boolean getSuccess(){
        return this.success;
    }
}
