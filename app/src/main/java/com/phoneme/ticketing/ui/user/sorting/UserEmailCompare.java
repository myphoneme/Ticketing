package com.phoneme.ticketing.ui.user.sorting;

import com.phoneme.ticketing.ui.user.UserModel;

import java.util.Comparator;

public class UserEmailCompare implements Comparator<UserModel> {
    public int compare(UserModel t1, UserModel t2){
        if(t1.getEmail()!=null && t2.getEmail()!=null) {
            return t1.getEmail().compareToIgnoreCase(t2.getEmail());
        }
        return 1;
    }
}
