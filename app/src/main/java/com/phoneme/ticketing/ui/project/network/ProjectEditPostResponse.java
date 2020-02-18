package com.phoneme.ticketing.ui.project.network;

import com.google.gson.annotations.SerializedName;

public class ProjectEditPostResponse {
    @SerializedName("success")
    private String responsesuccess;

    public String getResponsesuccess(){
        return this.responsesuccess;
    }
}
