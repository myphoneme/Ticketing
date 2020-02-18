package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketNumberCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getTicket_no()!=null && t2.getTicket_no()!=null) {
            return t1.getTicket_no().compareToIgnoreCase(t2.getTicket_no());
        }
        return 1;
    }
}
