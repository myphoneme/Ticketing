package com.phoneme.ticketing.ui.productivity.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.List;

public class TechsupportDashboardResponseList {
    @SerializedName("techusrdata")
    private List<TechsupportUserDataDashboardModel> techUsersData;

    @SerializedName("tickets")
    private List<List<TicketModel>> ticketModelListList;


    @SerializedName("name")
    private List<UserModel> users;

    public List<List<TicketModel>> getTicketModelListList(){
        return this.ticketModelListList;
    }

    public List<TechsupportUserDataDashboardModel> getTechUsersData(){
        return this.techUsersData;
    }

    public List<UserModel> getUsers(){
        return this.users;
    }
}
