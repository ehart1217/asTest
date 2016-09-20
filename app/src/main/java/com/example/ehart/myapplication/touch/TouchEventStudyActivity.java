package com.example.ehart.myapplication.touch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.ehart.myapplication.R;


/**
 * Created by ehart on 16-2-22.
 */
public class TouchEventStudyActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_event_study);
        View view = findViewById(R.id.study_view);
//        view.setClickable(true);
    }
}
