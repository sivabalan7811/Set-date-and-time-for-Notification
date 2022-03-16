package com.example.alarm;

import static com.example.alarm.MainActivity.basicNotification;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Myservice extends IntentService {
    public static volatile boolean shouldStop = false;
    public Myservice() {
        super(Myservice.class.getSimpleName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Intent intent1 = new Intent("com.example.andy.myapplication");
//        intent1.putExtra("someName", "Tutorialspoint.com");
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
//        if(shouldStop) {
//            stopSelf();
//            return;
//        }
        Log.d("onHandleIntent","onHandleIntent");
        basicNotification(new Intent(Myservice.this,MainActivity.class),Myservice.this);
    }
}