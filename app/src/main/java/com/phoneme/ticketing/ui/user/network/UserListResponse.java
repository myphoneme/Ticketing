package com.phoneme.ticketing.ui.user.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.List;

public class UserListResponse {
    @SerializedName("userlist")
    private List<UserModel> userModelList;

    public List<UserModel> getUserModelList(){
        return this.userModelList;
    }
}
