package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketStatusCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getStatus()!=null && t2.getStatus()!=null){
            return t1.getStatus().compareToIgnoreCase(t2.getStatus());
        }
        return 1;
    }
}
