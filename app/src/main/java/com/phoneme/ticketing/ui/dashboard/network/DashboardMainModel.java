package com.phoneme.ticketing.ui.dashboard.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.dashboard.model.DashboardSubheading;

import java.util.List;

public class DashboardMainModel {
    @SerializedName("type")
    private String type;

    @SerializedName("icon")
    private String icon;

    @SerializedName("values")
    private List<DashboardSubheading> dashboardSubheadingList;

    @SerializedName("background_color")
    private String backgroundColor;

    public String getBackgroundColor(){
        return this.backgroundColor;
    }

    public List<DashboardSubheading> getDashboardSubheadingList(){
        return this.dashboardSubheadingList;
    }

    public String getIcon(){
        return this.icon;
    }

    public String getType(){
        return this.type;
    }
}
