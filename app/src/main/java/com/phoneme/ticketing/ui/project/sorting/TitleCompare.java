package com.phoneme.ticketing.ui.project.sorting;

import com.phoneme.ticketing.ui.project.model.ProjectModel;

import java.util.Comparator;

public class TitleCompare implements Comparator<ProjectModel> {
    public int compare(ProjectModel t1,ProjectModel t2){
        if(t1.getName()!=null && t2.getName()!=null) {
            //return t1.getDesc().compareTo(t2.getDesc());
            return t1.getName().compareToIgnoreCase(t2.getName());
            //        return 0;
        }else {
            return 1;
        }
    }
}
