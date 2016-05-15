package com.example.ehart.myapplication.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ehart.myapplication.R;

/**
 * fragment test
 * Created by ehart on 16/5/15.
 */
public class FragmentA extends Fragment {

    public static FragmentA newInstance() {
        
        Bundle args = new Bundle();
        
        FragmentA fragment = new FragmentA();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_child,container,false);
        TextView tv = (TextView) rootView.findViewById(R.id.fragment_manage_child_tv);
        tv.setText("这是fragment A");
        return rootView;
    }
}
