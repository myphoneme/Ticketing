package com.phoneme.ticketing.ui.user.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.user.UserEditResponseModel;

public class UserEditResponse {
    //Checked actual response in postman. 'success' varialbe was not being used
    @SerializedName("success")
    private UserEditResponseModel responseModel;

    @SerializedName("jwttoken")
    private String jwttoken;

//    @SerializedName("image")
//    private String image;

    public String getJwttoken(){
        return this.jwttoken;
    }
//
//    public String getImage(){
//        return this.image;
//    }

    public UserEditResponseModel getResponseModel(){
        return this.responseModel;
    }
}
