package com.phoneme.ticketing.user.network;

import com.google.gson.annotations.SerializedName;

public class OTPVerifactionResponse {
    @SerializedName("otpverified")
    private Boolean otpverified;

    @SerializedName("jwttoken")
    private String jwttoken;

    public String getJwttoken(){
        return this.jwttoken;
    }
    public Boolean isOtpVerified(){
        return this.otpverified;
    }
}
