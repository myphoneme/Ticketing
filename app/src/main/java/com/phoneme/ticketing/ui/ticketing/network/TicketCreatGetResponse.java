package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.project.model.ProjectModel;
import com.phoneme.ticketing.ui.ticketing.model.UserModelForTicket;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.List;

public class TicketCreatGetResponse {
    @SerializedName("prioritylist")
    private List<String> prioritylist;

    @SerializedName("projects")
    private List<ProjectModel> projectList;

    @SerializedName("allowed")
    private Boolean allowed;

    @SerializedName("possibleusersforticketallocation")
    private List<UserModel> possibleUsers;

    public List<UserModel> getPossibleUsers(){
        return this.possibleUsers;
    }

    public Boolean isAllowed(){
        return this.allowed;
    }

    public List<String> getPrioritylist(){
        return this.prioritylist;
    }

    public List<ProjectModel> getProjectList(){
        return this.projectList;
    }
}
