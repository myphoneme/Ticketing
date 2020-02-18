package com.phoneme.ticketing.ui.user.sorting;

import com.phoneme.ticketing.ui.user.UserModel;

import java.util.Comparator;

public class UserStatusCompare implements Comparator<UserModel> {
    public int compare(UserModel t1, UserModel t2){
        if(t1.getStatus()!=null && t2.getStatus()!=null) {
            return t1.getStatus().compareToIgnoreCase(t2.getStatus());
        }
        return 1;
    }
}
