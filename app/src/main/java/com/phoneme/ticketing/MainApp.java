package com.phoneme.ticketing;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing
        Fresco.initialize(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//added late in feb 13 2020
    }
}
