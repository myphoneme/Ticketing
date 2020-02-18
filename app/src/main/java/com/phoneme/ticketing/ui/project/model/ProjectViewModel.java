package com.phoneme.ticketing.ui.project.model;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public ProjectViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");

        mText = new MutableLiveData<>();
        mText.setValue("This is project fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getText1(){ return mText;}
}
