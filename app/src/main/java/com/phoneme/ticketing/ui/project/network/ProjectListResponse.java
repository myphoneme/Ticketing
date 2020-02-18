package com.phoneme.ticketing.ui.project.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.project.model.ProjectModel;

import java.util.List;

public class ProjectListResponse {
    @SerializedName("projectlist")
    private List<ProjectModel> projectModelList;

    public List<ProjectModel> getProjectModelList(){
        return this.projectModelList;
    }
    /*
    @SerializedName("id")
    private String id;

    @SerializedName("allocated_users")
    private List<String> allcatedusers;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String description;

    @SerializedName("status")
    private String status;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("company_name")
    private String company_name;

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


    public String getName(){
        return this.name;
    }
    public List<String> getAllcatedusers(){
        return this.allcatedusers;
    }
    public String getId(){
        return this.id;
    }*/
}
