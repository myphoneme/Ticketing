package com.phoneme.ticketing.user.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.user.model.GCM_Master_Data_Model;

public class GCM_Master_Data_Response {
    @SerializedName("gcmdata")
    private String gcmMasterData;

//    public GCM_Master_Data_Model getgcmMasterData() {
//        return gcmMasterData;
//    }
}
