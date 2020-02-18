package com.phoneme.ticketing.ui.user.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.user.UserModel;

public class UserEditGetResponse {
    @SerializedName("userdata")
    private UserModel userData;
    public UserModel getUserData(){
        return this.userData;
    }
}
