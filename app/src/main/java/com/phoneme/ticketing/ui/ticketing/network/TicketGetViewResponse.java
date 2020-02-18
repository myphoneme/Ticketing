package com.phoneme.ticketing.ui.ticketing.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.project.model.ProjectModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.List;

public class TicketGetViewResponse {
    @SerializedName("ticket")
    private TicketModel ticketModel;

    @SerializedName("threads")
    private List<TicketModel> ticketModelList;

    @SerializedName("project")
    private ProjectModel projectModel;

    public ProjectModel getProjectModel(){
        return this.projectModel;
    }


//    @SerializedName("projects")
//    private List<ProjectModel> projectList;
//
//    @SerializedName("prioritylist")
//    private List<String> prioritylist;
//
//    public List<String> getPrioritylist(){
//        return prioritylist;
//    }
//
//
//
//    public List<ProjectModel>getProjectList(){
//        return this.projectList;
//    }
    public List<TicketModel> getThreads(){
        return this.ticketModelList;
    }
    public TicketModel getTicketModel(){
        return this.ticketModel;
    }
}
