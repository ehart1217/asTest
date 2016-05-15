package com.example.ehart.myapplication.manage;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.utils.FragmentUtils;

/**
 * fragment管理研究一波
 * Created by ehart on 16/5/15.
 */
public class FragmentManageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmet_manage);

        String fragmentTag = FragmentUtils.generateTag(FragmentParent.class);
        FragmentManager fm = getSupportFragmentManager();
        FragmentParent fragment = FragmentParent.newInstance();
        FragmentUtils.addFragment(fm, R.id.activity_fragment_manage_container, fragment, fragmentTag);
    }


}
