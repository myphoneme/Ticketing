package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.user.UserEditResponseModel;

public class TicketEditPostResponse {
    @SerializedName("success")
    private UserEditResponseModel responseModel;

    public UserEditResponseModel getResponseModel(){
        return this.responseModel;
    }
}
