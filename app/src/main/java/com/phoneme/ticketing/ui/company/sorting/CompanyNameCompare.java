package com.phoneme.ticketing.ui.company.sorting;

import com.phoneme.ticketing.ui.company.model.CompanyModel;

import java.util.Comparator;

public class CompanyNameCompare implements Comparator<CompanyModel> {
    public int compare(CompanyModel t1, CompanyModel t2){
        if(t1.getName()!=null && t2.getName()!=null ){
            return t1.getName().compareToIgnoreCase(t2.getName());
        }
        return 1;
    }

}
