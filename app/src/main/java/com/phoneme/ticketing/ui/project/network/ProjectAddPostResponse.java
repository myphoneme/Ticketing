package com.phoneme.ticketing.ui.project.network;

import com.google.gson.annotations.SerializedName;

public class ProjectAddPostResponse {
    @SerializedName("allowed")
    private Boolean allowed;

    public Boolean isAllowed(){
        return this.allowed;
    }
    @SerializedName("success")
    private String responsesuccess;

    public String getResponsesuccess(){
        return this.responsesuccess;
    }
}
