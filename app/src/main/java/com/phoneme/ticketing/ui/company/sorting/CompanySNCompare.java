package com.phoneme.ticketing.ui.company.sorting;

import com.phoneme.ticketing.ui.company.model.CompanyModel;

import java.util.Comparator;

public class CompanySNCompare implements Comparator<CompanyModel> {
    public int compare(CompanyModel t1, CompanyModel t2){
        if(t1.getId()!=null && t2.getId()!=null ){
            return t1.getId().compareToIgnoreCase(t2.getId());
        }
        return 1;
    }
}
