package com.finalproject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class GPSService extends Service {
    private static final String TAG = "GPSService";
    private Looper serviceLooper;
    private GPSServiceHandler handler;
    public GPSService() {
    }


    public final class GPSServiceHandler extends Handler{

        public GPSServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {


        Log.d(TAG, "onCreate: ");
        
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        handler = new GPSServiceHandler(serviceLooper);




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Message msg = handler.obtainMessage();
        msg.arg1 = startId;
        handler.sendMessage(msg);
        Log.d("GPSSERVICE", "onStartCommand: " + msg.arg1);
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Done", Toast.LENGTH_SHORT).show();
    }
}