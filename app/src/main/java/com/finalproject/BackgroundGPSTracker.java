package com.finalproject;

import android.os.AsyncTask;
import android.util.Log;

public class BackgroundGPSTracker {
    private static final String TAG = "BackgroundGPSTracker";
    GPSHandler gpsHandler;

    public BackgroundGPSTracker(GPSHandler handler){
        this.gpsHandler = handler;

    }

    public void run(){

    }
}
