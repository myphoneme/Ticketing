package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.List;

public class TicketSingleResponse {
    @SerializedName("ticketdata")
    private TicketModel ticket;

    @SerializedName("ticketimages")
    private List<String> ticketimages;

    public TicketModel getTicket(){
        return this.ticket;
    }

    public List<String> getTicketimages(){
        return this.ticketimages;
    }
}
