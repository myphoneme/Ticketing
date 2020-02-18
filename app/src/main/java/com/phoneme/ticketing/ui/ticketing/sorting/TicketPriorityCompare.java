package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketPriorityCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getPriority()!=null && t2.getPriority()!=null){
            return t1.getPriority().compareToIgnoreCase(t2.getPriority());
        }
        return 1;
    }
}
