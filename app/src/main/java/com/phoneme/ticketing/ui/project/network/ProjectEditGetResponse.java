package com.phoneme.ticketing.ui.project.network;

import com.google.gson.annotations.SerializedName;
import com.phoneme.ticketing.ui.project.model.ProjectEditGetModel;

public class ProjectEditGetResponse {
    @SerializedName("project_data")
    private ProjectEditGetModel projectEditGetModel;

    public ProjectEditGetModel getProjectEditGetModel(){
        return this.projectEditGetModel;
    }
}
