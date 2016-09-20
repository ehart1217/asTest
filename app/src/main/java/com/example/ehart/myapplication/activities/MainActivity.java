package com.example.ehart.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.fragmentation.manage.FragmentManageActivity;
import com.example.ehart.myapplication.touch.TouchEventStudyActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mFragmentManageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
    }

    private void findView() {
        mFragmentManageBtn = (Button) findViewById(R.id.test_fragment_manage);
    }

    private void setListener() {
        mFragmentManageBtn.setOnClickListener(this);

        View touchEventBtn = findViewById(R.id.study_touch_event);
        touchEventBtn.setOnClickListener(this);

        View forResultBtn = findViewById(R.id.start_activity_for_result_study);
        forResultBtn.setOnClickListener(this);

        View serviceStudyBtn = findViewById(R.id.service_study);
        serviceStudyBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.test_fragment_manage:
                FragmentManageActivity.navigateTo(this);
                break;
            case R.id.study_touch_event:
                startActivity(new Intent(MainActivity.this, TouchEventStudyActivity.class));
                break;
            case R.id.start_activity_for_result_study:
                ForResultActivity.navigateToForResult(this, 1);
                break;
            case R.id.service_study:
                TestServiceLifeActivity.navigateTo(this);
                break;
            default:
                break;
        }
    }
}
