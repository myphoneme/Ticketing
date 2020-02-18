package com.phoneme.ticketing.ui.company.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.company.model.CompanyModel;

public class CompanyEditGetResponse {
    @SerializedName("company")
    private CompanyModel company;

    public CompanyModel getCompany(){
        return this.company;
    }
}
