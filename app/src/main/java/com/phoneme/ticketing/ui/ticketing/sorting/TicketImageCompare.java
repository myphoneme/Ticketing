package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketImageCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getImage()!=null && t2.getImage()!=null) {
            return t1.getImage().compareToIgnoreCase(t2.getImage());
        }
        return 1;
    }
}
