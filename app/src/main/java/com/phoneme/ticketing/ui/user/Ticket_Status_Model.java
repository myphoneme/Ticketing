package com.phoneme.ticketing.ui.user;

import com.google.gson.annotations.SerializedName;

public class Ticket_Status_Model {
    @SerializedName("0")
    private int close;

    @SerializedName("1")
    private int open;

    @SerializedName("2")
    private int waiting_for_close;

    public int getWaiting_for_close(){
        return this.waiting_for_close;
    }
    public int getOpen(){
        return this.open;
    }
    public int getClose(){
        return this.close;
    }
}
