package com.phoneme.ticketing.ui.user.sorting;

import com.phoneme.ticketing.ui.user.UserModel;

import java.util.Comparator;

public class UserMobileNumberCompare implements Comparator<UserModel> {
    public int compare(UserModel t1, UserModel t2){
        if(t1.getMobile_no()!=null && t2.getMobile_no()!=null) {
            return t1.getMobile_no().compareToIgnoreCase(t2.getMobile_no());
        }
        return 1;
    }
}
