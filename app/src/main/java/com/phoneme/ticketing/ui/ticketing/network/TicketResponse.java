package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.List;

public class TicketResponse {//@SerializedName("openticketcount")
    @SerializedName("tickets")
    private List<TicketModel> listOfTickets;

    public List<TicketModel> getListOfTickets(){
        return this.listOfTickets;
    }
}
