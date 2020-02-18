package com.phoneme.ticketing.ui.project.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectModel {
    @SerializedName("name")
    private String name;

    public String getName(){
        return this.name;
    }

    @SerializedName("id")
    private String id;

    @SerializedName("allocated_users")
    private List<ProjectUserModel> allcatedusers;

    @SerializedName("desc")
    private String description;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("company_name")
    private String company_name;

    @SerializedName("company_id") //Added on jan 14th 2020
    private String company_id;

    public String getCompany_id(){
        return this.company_id;
    }

    public String getCompany_name(){
        return this.company_name;
    }

    public String getCreated_at(){
        return this.created_at;
    }

    public String getStatus(){
        return this.status;
    }

    public String getDescription(){
        return this.description;
    }

    public List<ProjectUserModel> getAllcatedusers(){
        return this.allcatedusers;
    }
    public String getId(){
        return this.id;
    }
}


/*
* "id": "1",
            "name": "Ticketing System",
            "desc": "This project is created for resolving the issues",
            "status": "1",
            "created_at": "2019-08-19 12:32:12",
            "created_by": "46",
            "company_id": "1"
*
*
* */
