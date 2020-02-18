package com.phoneme.ticketing.ui.project.network;
import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.company.model.CompanyModel;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.List;

public class ProjectAddGetResponse {
    @SerializedName("allowed")
    private Boolean allowed;

    @SerializedName("userlist")
    private List<UserModel> userModelList;

    @SerializedName("company")
    private List<CompanyModel> companyModelList;

    public Boolean isAllowed(){
        return this.allowed;
    }


    public List<UserModel> getUserModelList(){
        return this.userModelList;
    }

    public List<CompanyModel> getCompanyModelList(){
        return this.companyModelList;
    }
}
