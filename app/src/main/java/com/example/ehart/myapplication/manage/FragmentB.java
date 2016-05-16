package com.example.ehart.myapplication.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.fragmentation.anim.DefaultHorizontalAnimator;
import com.example.ehart.myapplication.fragmentation.anim.FragmentAnimator;

/**
 * fragment test
 * Created by ehart on 16/5/15.
 */
public class FragmentB extends BaseFragment {

    public static FragmentB newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentB fragment = new FragmentB();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_manage_child,container,false);
        TextView tv = (TextView) rootView.findViewById(R.id.fragment_manage_child_tv);
        tv.setText("这是fragment B");

        View backBtn = rootView.findViewById(R.id.fragment_manage_child_back_btn);
        initBackBtn(backBtn);
        return rootView;
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
