package com.phoneme.ticketing.ui.productivity.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel;
import com.phoneme.ticketing.ui.productivity.model.TechsupportUserDataDashboardModel2;

import java.util.List;

public class TechsupportDashboardResponse2 {
    @SerializedName("techusrdata")
    private List<TechsupportUserDataDashboardModel2> techUsersData;

    public List<TechsupportUserDataDashboardModel2> getTechUsersData(){
        return this.techUsersData;
    }
}
