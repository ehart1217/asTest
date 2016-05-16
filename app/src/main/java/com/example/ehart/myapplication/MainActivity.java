package com.example.ehart.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ehart.myapplication.manage.FragmentManageActivity;

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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.test_fragment_manage:
                FragmentManageActivity.navigateTo(this);
                break;
        }
    }
}
