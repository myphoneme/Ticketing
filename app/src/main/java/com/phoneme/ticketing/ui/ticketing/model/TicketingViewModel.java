package com.phoneme.ticketing.ui.ticketing.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TicketingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TicketingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ticketing fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}