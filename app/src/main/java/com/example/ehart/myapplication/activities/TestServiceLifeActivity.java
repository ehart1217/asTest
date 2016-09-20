package com.example.ehart.myapplication.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.services.LifeRecycleService;

/**
 * 测试service的生命周期
 * Created by ehart on 16-3-28.
 */
public class TestServiceLifeActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "TestServiceLifeActivity";
    private LifeRecycleService mService;

    public static void navigateTo(Context context){
        context.startActivity(new Intent(context, TestServiceLifeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service_life);
        View startService = findViewById(R.id.startService);
        View bindService = findViewById(R.id.bindService);
        View unbindService = findViewById(R.id.unbindService);
        View stopService = findViewById(R.id.stopService);

        startService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        stopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startService:
                print("startService btn click");
                startService(LifeRecycleService.newIntent(this));
                break;
            case R.id.bindService:
                print("bindService btn click");
                bindService(LifeRecycleService.newIntent(getApplicationContext()), connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                print("unbindService btn click");
                unbindService(connection);
                break;
            case R.id.stopService:
                print("stopService btn click");
                stopService(LifeRecycleService.newIntent(this));
                break;
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            print("onServiceConnected");
            if(service instanceof LifeRecycleService.LifeRecycleBinder){
                LifeRecycleService.LifeRecycleBinder lifeRecycleBinder = (LifeRecycleService.LifeRecycleBinder) service;
                mService = lifeRecycleBinder.getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void print(String text){
        Log.d(TAG, text);
    }

}
