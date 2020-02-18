package com.phoneme.ticketing.ui.user.sorting;

import com.phoneme.ticketing.ui.user.UserModel;

import java.util.Comparator;

public class UserNameCompare implements Comparator<UserModel> {
    public int compare(UserModel t1, UserModel t2){
        if(t1.getName()!=null && t2.getName()!=null) {
            return t1.getName().compareToIgnoreCase(t2.getName());
        }
        return 1;
    }
}
