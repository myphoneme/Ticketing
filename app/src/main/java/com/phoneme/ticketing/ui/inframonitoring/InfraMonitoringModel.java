package com.phoneme.ticketing.ui.inframonitoring;

import com.google.gson.annotations.SerializedName;

public class InfraMonitoringModel {
    @SerializedName("name")
    private String name;

    @SerializedName("output")
    private String output;

    public String getName(){
        return this.name;
    }
    public String getOutput(){
        return this.output;
    }

    public void setName(String name){
        this.name=name;
    }
    public void setOutput(String output){
        this.output=output;
    }
}
