package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketCreatedTimeCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getCreated_at()!=null && t2.getCreated_at()!=null) {
            return t1.getCreated_at().compareTo(t2.getCreated_at());
        }
        return 1;
    }
}
