package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketCreatedByCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getCreated_by_name()!=null && t2.getCreated_by_name()!=null) {
            return t1.getCreated_by_name().compareToIgnoreCase(t2.getCreated_by_name());
        }
        return 1;
        }
}
