package com.phoneme.ticketing.ui.user.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.user.UserEditResponseModel;

public class UserEditResponse {
    @SerializedName("success")
    private UserEditResponseModel responseModel;

    @SerializedName("jwttoken")
    private String jwttoken;

    public String getJwttoken(){
        return this.jwttoken;
    }

    public UserEditResponseModel getResponseModel(){
        return this.responseModel;
    }
}
