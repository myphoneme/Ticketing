package com.phoneme.ticketing.ui.project.sorting;

import com.phoneme.ticketing.ui.project.model.ProjectModel;

import java.util.Comparator;

public class DescriptionCompare implements Comparator<ProjectModel> {
    public int compare(ProjectModel t1,ProjectModel t2){
        if(t1.getDescription()!=null && t2.getDescription()!=null){
            return t1.getDescription().compareToIgnoreCase(t2.getDescription());
        }
        return 1;
    }
}
