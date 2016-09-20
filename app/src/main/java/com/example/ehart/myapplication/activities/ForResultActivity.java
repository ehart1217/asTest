package com.example.ehart.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ehart.myapplication.R;

/**
 * Created by ehart on 16-3-15.
 */
public class ForResultActivity extends Activity{

    public static final String EXTRA_KEY = "study_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_a_btn);
        View testBtn = findViewById(R.id.test_btn);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_KEY, "测试");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public static void navigateToForResult(Activity activity,int requestCode){
        Intent intent = new Intent(activity,ForResultActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}
