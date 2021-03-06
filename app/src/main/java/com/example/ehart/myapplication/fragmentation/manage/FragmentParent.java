package com.example.ehart.myapplication.fragmentation.manage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.fragmentation.anim.DefaultNoAnimator;
import com.example.ehart.myapplication.fragmentation.anim.FragmentAnimator;
import com.example.ehart.myapplication.fragmentation.utils.FragmentUtils;

/**
 * 父fragment
 * Created by ehart on 16/5/15.
 */
public class FragmentParent extends BaseFragment implements View.OnClickListener {

    private Fragment mFragmentA;
    private Fragment mFragmentB;
    private Fragment mFragmentC;

    private Fragment mCurrentFragment;

    private View mBtnA;
    private View mBtnB;
    private View mBtnC;

    private FragmentManager mFragmentManager;
    private TextView mModeBtn;

    private boolean isDeletePrevious = false;
    private View mPopAllBtn;

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
        initModeBtn();
        return rootView;
    }

    private void findView(View rootView) {
        mBtnA = rootView.findViewById(R.id.fragment_manage_btn_a);
        mBtnB = rootView.findViewById(R.id.fragment_manage_btn_b);
        mBtnC = rootView.findViewById(R.id.fragment_manage_btn_c);

        mPopAllBtn = rootView.findViewById(R.id.fragment_manage_pop_all_btn);

        mModeBtn = (TextView) rootView.findViewById(R.id.fragment_manage_mode_btn);
    }

    private void setListener() {
        mBtnA.setOnClickListener(this);
        mBtnB.setOnClickListener(this);
        mBtnC.setOnClickListener(this);
        mModeBtn.setOnClickListener(this);
        mPopAllBtn.setOnClickListener(this);
    }

    private void initModeBtn() {
        if (isDeletePrevious) {
            mModeBtn.setText("删除前一个fragment");
        } else {
            mModeBtn.setText("不删除前一个fragment");
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            FragmentManager fm = getChildFragmentManager();
            mFragmentA = fm.findFragmentByTag(FragmentUtils.generateTag(FragmentA.class));
            mFragmentB = fm.findFragmentByTag(FragmentUtils.generateTag(FragmentB.class));
            mFragmentC = fm.findFragmentByTag(FragmentUtils.generateTag(FragmentC.class));
            FragmentUtils.showTopHideOtherFragment(fm);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentManager = getChildFragmentManager();
        FragmentUtils.add(mFragmentManager, FragmentA.newInstance(), R.id.fragment_manage_fragment_container);
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultNoAnimator();
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
        } else if (id == R.id.fragment_manage_mode_btn) {
            isDeletePrevious = !isDeletePrevious;
            initModeBtn();
        } else if (id == R.id.fragment_manage_pop_all_btn) {
            FragmentUtils.popAll(mFragmentManager);
        }
    }

    private void showFragmentA() {

        if (mFragmentA == null || !mFragmentA.isAdded()) {
            mFragmentA = FragmentA.newInstance();
            FragmentUtils.addWithBackStack(mFragmentManager, mFragmentA, R.id.fragment_manage_fragment_container, isDeletePrevious);
        } else {
            FragmentUtils.showWithBackStack(mFragmentManager, mFragmentA);

        }
    }

    private void showFragmentB() {
        if (mFragmentB == null || !mFragmentB.isAdded()) {
            mFragmentB = FragmentB.newInstance();
            FragmentUtils.addWithBackStack(mFragmentManager, mFragmentB, R.id.fragment_manage_fragment_container, isDeletePrevious);

        } else {
            FragmentUtils.showWithBackStack(mFragmentManager, mFragmentB);
        }
    }

    private void showFragmentC() {
        if (mFragmentC == null || !mFragmentC.isAdded()) {
            mFragmentC = FragmentC.newInstance();
            FragmentUtils.addWithBackStack(mFragmentManager, mFragmentC, R.id.fragment_manage_fragment_container, isDeletePrevious);
        } else {
            FragmentUtils.showWithBackStack(mFragmentManager, mFragmentC);
        }
    }

    private boolean isDeletePrevious() {
        return mFragmentManager.getBackStackEntryCount() > 1;
    }

}
