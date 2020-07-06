package com.phoneme.ticketing.ui.productivity.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.productivity.model.datamodelcheck;

import java.util.List;

public class TechsupportDashboardResponseCheck {
//    @SerializedName("techusrdata")
//    private String techusrdata;
//
//    public String getTechusrdata(){
//        return this.techusrdata;
//    }

    @SerializedName("techusrdata")
    private List<datamodelcheck> techusrdata;

    public List<datamodelcheck> getTechusrdata(){
        return this.techusrdata;
    }
}
