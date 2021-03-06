package com.phoneme.ticketing.ui.dashboard.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardMainResponse {
    @SerializedName("dashboard")
    private List<DashboardMainModel> dashboardMainModelList;

    public List<DashboardMainModel> getDashboardMainModelList(){
        return this.dashboardMainModelList;
    }
}
