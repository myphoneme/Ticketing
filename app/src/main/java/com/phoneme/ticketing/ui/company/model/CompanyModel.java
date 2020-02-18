package com.phoneme.ticketing.ui.company.model;

import com.google.gson.annotations.SerializedName;

/*
* "name": "Phoneme Solutions Pvt. Ltd.",
        "Created_by": "46",
        "Created_at": "2019-08-19 12:31:44",
        "status": "1"*/
public class CompanyModel {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("Created_by")
    private String Created_by;

    @SerializedName("Created_at")
    private String Created_at;

    @SerializedName("status")
    private String status;

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
    public String getCreated_by(){
        return this.Created_by;
    }
    public String getCreated_at(){
        return this.Created_at;
    }
    public String getStatus(){
        return this.status;
    }
}
