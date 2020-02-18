package com.phoneme.ticketing.ui.ticketing.sorting;

import com.phoneme.ticketing.ui.ticketing.model.TicketModel;

import java.util.Comparator;

public class TicketDescriptionCompare implements Comparator<TicketModel> {
    public int compare(TicketModel t1, TicketModel t2){
        if(t1.getDesc()!=null && t2.getDesc()!=null) {
            //return t1.getDesc().compareTo(t2.getDesc());
            return t1.getDesc().compareToIgnoreCase(t2.getDesc());
            //        return 0;
        }else{
            return 1;
        }
    }
}
