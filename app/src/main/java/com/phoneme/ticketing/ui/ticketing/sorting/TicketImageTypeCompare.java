package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketImageTypeCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getImage_type()!=null && t2.getImage_type()!=null) {
            return t1.getImage_type().compareToIgnoreCase(t2.getImage_type());
        }
        return 1;
    }
}
