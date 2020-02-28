package com.phoneme.ticketing.ui.productivity.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TechSupportDashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TechSupportDashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tech support dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}