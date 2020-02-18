package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketTitleCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getName()!=null && t2.getName()!=null) {
            return t1.getName().compareTo(t2.getName());
        }
        return 1;
    }
}
