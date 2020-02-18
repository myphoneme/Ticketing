package com.phoneme.ticketing.ui.user.sorting;

import com.phoneme.ticketing.ui.user.UserModel;

import java.util.Comparator;

public class UserSNCompare implements Comparator<UserModel> {
    public int compare(UserModel t1, UserModel t2){
        if(t1.getId()!=null && t2.getId()!=null) {
            return t1.getId().compareToIgnoreCase(t2.getId());
        }
        return 1;
    }
}
