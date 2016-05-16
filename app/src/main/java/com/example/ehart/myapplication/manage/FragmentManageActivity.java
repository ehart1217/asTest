package com.example.ehart.myapplication.manage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.utils.FragmentUtils;

/**
 * fragment管理研究一波
 * Created by ehart on 16/5/15.
 */
public class FragmentManageActivity extends AppCompatActivity {

    private FragmentParent mFragment;

    public static void navigateTo(Context context) {
        Intent intent = new Intent(context, FragmentManageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmet_manage);

        String fragmentTag = FragmentUtils.generateTag(FragmentParent.class);
        FragmentManager fm = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mFragment = (FragmentParent) fm.findFragmentByTag(fragmentTag);
        } else {
            mFragment = FragmentParent.newInstance();
            FragmentUtils.addFragment(fm, R.id.activity_fragment_manage_container, mFragment, fragmentTag);
        }


        View stackBtn = findViewById(R.id.activity_fragment_manage_stack_btn);
        stackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragmentStackHierarchyView();
            }
        });

    }


    /**
     * 显示栈视图,调试时使用
     */
    public void showFragmentStackHierarchyView() {
        HierarchyViewContainer container = new HierarchyViewContainer(this);
        container.bindFragmentRecords(FragmentUtils.getFragmentRecords(this));
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        new AlertDialog.Builder(this)
                .setTitle("fragment列表")
                .setView(container)
                .setPositiveButton("关闭", null)
                .setCancelable(true)
                .show();
    }
}
