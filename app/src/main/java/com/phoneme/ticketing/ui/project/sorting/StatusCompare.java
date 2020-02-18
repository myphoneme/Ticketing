package com.phoneme.ticketing.ui.project.sorting;

import com.phoneme.ticketing.ui.project.model.ProjectModel;

import java.util.Comparator;

public class StatusCompare implements Comparator<ProjectModel> {
    public int compare(ProjectModel t1,ProjectModel t2){
        if(t1.getStatus()!=null && t2.getStatus()!=null){
           return  t1.getStatus().compareToIgnoreCase(t2.getStatus());
        }
        return 1;
    }
}
