package com.example.ehart.myapplication.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 测试service生命周期
 * Created by ehart on 16-3-28.
 */
public class LifeRecycleService extends Service{

    private static final String TAG = "LifeRecycleService";

    private LifeRecycleBinder mBinder;

    public static Intent newIntent(Context context){
        return new Intent(context, LifeRecycleService.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        print("onBind");
        if(mBinder == null){
            mBinder = new LifeRecycleBinder();
        }
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        print("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        print("onCreate");
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        print("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        print("onDestroy");
        super.onDestroy();
    }

    public class LifeRecycleBinder extends Binder {
        public LifeRecycleService getService(){
            return LifeRecycleService.this;
        }
    }

    private void print(String text){
        Log.d(TAG, text);
    }
}
