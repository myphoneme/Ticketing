package com.phoneme.ticketing.user.network;

import com.google.gson.annotations.SerializedName;

public class GCMMASTERADDEDResponse {
    @SerializedName("added_id")
    private String added_id;

    public String getAdded_id(){
        return added_id;
    }

}
