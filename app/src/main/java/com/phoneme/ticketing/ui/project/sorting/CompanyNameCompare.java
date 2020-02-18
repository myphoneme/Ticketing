package com.phoneme.ticketing.ui.project.sorting;

import com.phoneme.ticketing.ui.project.model.ProjectModel;

import java.util.Comparator;

public class CompanyNameCompare implements Comparator<ProjectModel> {

    public int compare(ProjectModel t1,ProjectModel t2){
        if(t1.getCompany_name()!=null && t2.getCompany_name()!=null) {
            //return t1.getDesc().compareTo(t2.getDesc());
            return t1.getCompany_name().compareToIgnoreCase(t2.getCompany_name());
            //        return 0;
        }else{
            return 1;
        }
    }
}
