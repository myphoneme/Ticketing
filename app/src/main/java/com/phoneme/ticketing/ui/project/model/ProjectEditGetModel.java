package com.phoneme.ticketing.ui.project.model;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.company.model.CompanyModel;

import java.util.List;

public class ProjectEditGetModel {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("created_by")
    private String created_by;

    @SerializedName("company_id")
    private String company_id;

    @SerializedName("company_name")
    private String compay_name;

    @SerializedName("allocated_users")
    private List<ProjectEditUserModel> projectEditAllocatedUserModelList;

    @SerializedName("possible_allocated_users")
    private List<ProjectUserModel> projectUserModelList;

    @SerializedName("companies")
    private List<CompanyModel> companyModelList;

    @SerializedName("images")
    private String images;
    @SerializedName("team")
    private String team;

    public String getTeam(){
        return team;
    }

    public String getImages(){
        return this.images;
    }

    public List<CompanyModel> getCompanyList(){
        return this.companyModelList;
    }

    public List<ProjectUserModel> getPossibleAllocatedProjectUserModelList(){
        return this.projectUserModelList;
    }

    public List<ProjectEditUserModel> getProjectEditAllocatedUserModelList(){
        return this.projectEditAllocatedUserModelList;
    }


    public String getCompay_name(){
        return this.compay_name;
    }

    public String getCompany_id(){
        return this.company_id;
    }

    public String getCreated_by(){
        return this.created_by;
    }


    public String getCreated_at(){
        return this.created_at;
    }
    public String getStatus(){
        return this.status;
    }

    public String getDesc(){
        return this.desc;
    }
    public String getName(){
        return this.name;
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
        "company_id": "1",
        "company_name": "Phoneme Solutions Pvt. Ltd.",
*
* */
