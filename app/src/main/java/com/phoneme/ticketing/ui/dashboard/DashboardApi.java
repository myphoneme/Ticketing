package com.phoneme.ticketing.ui.dashboard;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.dashboard.model.DashdataModel;

public class DashboardApi {
    @SerializedName("dashboard")
    public DashdataModel dashdata;

    public DashdataModel getDashdata(){
        return this.dashdata;
    }
}
