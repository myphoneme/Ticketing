package com.phoneme.ticketing.user.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.user.UserModel;

public class OTPVerifactionResponse {
    @SerializedName("otpverified")
    private Boolean otpverified;

    @SerializedName("jwttoken")
    private String jwttoken;

    @SerializedName("message")
    private String message;

    @SerializedName("passwordverified")
    private Boolean passwordverified;

    @SerializedName("result")
    private UserModel user;

    public String getJwttoken(){
        return this.jwttoken;
    }
    public Boolean isOtpVerified(){
        return this.otpverified;
    }

    public Boolean getPasswordverified(){
        return this.passwordverified;
    }

    public UserModel getUser(){
        return this.user;
    }

    public String getMessage(){
        return this.message;
    }
}
