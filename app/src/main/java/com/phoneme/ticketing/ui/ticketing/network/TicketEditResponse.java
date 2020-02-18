package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.project.model.ProjectModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.ticketing.model.UserIdModel;
import com.phoneme.ticketing.ui.ticketing.model.UserModelForTicket;
import com.phoneme.ticketing.ui.user.UserModel;

import java.util.List;

public class TicketEditResponse {

    @SerializedName("ticket")
    private TicketModel ticketModel;


    @SerializedName("projects")
    private List<ProjectModel> projectList;

    @SerializedName("prioritylist")
    private List<String> prioritylist;

    @SerializedName("possibleusersforticketallocation")
    private List<UserModelForTicket> possibleUsers;

    @SerializedName("allocatedusersforgiventicketid")
    private List<UserIdModel> allocateUsersForATicket;


    public List<UserIdModel> getAllocateUsersForATicket(){
        return this.allocateUsersForATicket;
    }
    public List<UserModelForTicket> getPossibleUsers(){
        return this.possibleUsers;
    }


    public List<String> getPrioritylist(){
        return prioritylist;
    }



    public List<ProjectModel> getProjectList(){
        return this.projectList;
    }

    public TicketModel getTicketModel(){
        return this.ticketModel;
    }
}
