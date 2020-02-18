package com.phoneme.ticketing.ui.company.sorting;

import com.phoneme.ticketing.ui.company.model.CompanyModel;

import java.util.Comparator;

public class CompanyStatusCompare implements Comparator<CompanyModel> {
    public int compare(CompanyModel t1, CompanyModel t2){
        if(t1.getStatus()!=null && t2.getStatus()!=null){
            return t1.getStatus().compareToIgnoreCase(t2.getStatus());
        }
        return 1;
    }
}
