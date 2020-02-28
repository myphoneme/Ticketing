package com.phoneme.ticketing.ui.productivity.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.List;

public class TechsupportDashboardResponse {
    @SerializedName("techusrdata")
    private List<TechsupportUserDataDashboardModel> techUsersData;

    @SerializedName("tickets")
    private List<TicketModel> ticketModelList;

    @SerializedName("name")
    private List<UserModel> users;

    public List<TechsupportUserDataDashboardModel> getTechUsersData(){
        return this.techUsersData;
    }

    public List<TicketModel> getTicketModelList(){
        return this.ticketModelList;
    }

    public List<UserModel> getUsers(){
        return this.users;
    }
}
