package com.phoneme.ticketing.ui.user.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.phoneme.ticketing.R;
import com.phoneme.ticketing.helper.SavedUserData;

public class UserProfileFragmentCrop extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile_new_yash_design_crop, container, false);
        //userData = new SavedUserData();
        return root;
    }
}
