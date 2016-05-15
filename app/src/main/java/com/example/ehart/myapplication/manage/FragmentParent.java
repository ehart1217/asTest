package com.example.ehart.myapplication.manage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.utils.FragmentUtils;

/**
 * 父fragment
 * Created by ehart on 16/5/15.
 */
public class FragmentParent extends Fragment implements View.OnClickListener {

    private Fragment mFragmentA;
    private Fragment mFragmentB;
    private Fragment mFragmentC;

    private View btnC;
    private View btnB;
    private View btnA;

    public static FragmentParent newInstance() {

        Bundle args = new Bundle();

        FragmentParent fragment = new FragmentParent();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_container, container, false);
        findView(rootView);
        setListener();
        return rootView;
    }

    private void findView(View rootView) {
        btnA = rootView.findViewById(R.id.fragment_manage_btn_a);
        btnB = rootView.findViewById(R.id.fragment_manage_btn_b);
        btnC = rootView.findViewById(R.id.fragment_manage_btn_c);
    }

    private void setListener() {
        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            FragmentManager fm = getChildFragmentManager();
            mFragmentA = fm.findFragmentByTag(FragmentUtils.generateTag(FragmentA.class));
            mFragmentB = fm.findFragmentByTag(FragmentUtils.generateTag(FragmentB.class));
            mFragmentC = fm.findFragmentByTag(FragmentUtils.generateTag(FragmentC.class));

        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fragment_manage_btn_a) {
            showFragmentA();
        } else if (id == R.id.fragment_manage_btn_b) {
            showFragmentB();
        } else if (id == R.id.fragment_manage_btn_c) {
            showFragmentC();
        }
    }

    private void showFragmentA() {
        FragmentManager fm = getChildFragmentManager();
        if (mFragmentA == null) {
            String tag = FragmentUtils.generateTag(FragmentA.class);
            mFragmentA = FragmentA.newInstance();
            FragmentUtils.addFragment(fm, R.id.fragment_manage_fragment_container, mFragmentA, tag);
        } else {

        }
    }

    private void showFragmentB() {

    }

    private void showFragmentC() {

    }
}
