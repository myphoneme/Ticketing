package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class ProjectNameCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getProject_name()!=null && t2.getProject_name()!=null) {
            return t1.getProject_name().compareToIgnoreCase(t2.getProject_name());
        }
        return 1;
    }
}
