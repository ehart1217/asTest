package com.example.ehart.myapplication.manage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.ehart.myapplication.R;
import com.example.ehart.myapplication.fragmentation.anim.DefaultNoAnimator;
import com.example.ehart.myapplication.fragmentation.anim.FragmentAnimator;
import com.example.ehart.myapplication.utils.FragmentUtils;

/**
 * fragment 基类
 * Created by ehart on 16-5-16.
 */
public class BaseFragment extends Fragment {

    private Activity mActivity;

    private Animation mNoAnim, mEnterAnim, mExitAnim, mPopEnterAnim, mPopExitAnim;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentAnimator fragmentAnimator = onCreateFragmentAnimator();

        initAnimator(fragmentAnimator);

    }

    private void initAnimator(FragmentAnimator fragmentAnimator) {

        if (fragmentAnimator == null) {
            fragmentAnimator = new DefaultNoAnimator();
        }
        int enter = fragmentAnimator.getEnter();
        int exit = fragmentAnimator.getExit();
        int popEnter = fragmentAnimator.getPopEnter();
        int popExit = fragmentAnimator.getPopExit();

        if (enter == 0) {
            enter = R.anim.no_anim;
        }
        if (exit == 0) {
            exit = R.anim.no_anim;
        }
        if (popEnter == 0) {
            popEnter = R.anim.no_anim;
        }
        if (popExit == 0) {
            popExit = R.anim.pop_exit_no_anim;
        }

        mNoAnim = AnimationUtils.loadAnimation(mActivity, R.anim.no_anim);
        mEnterAnim = AnimationUtils.loadAnimation(mActivity, enter);
        mExitAnim = AnimationUtils.loadAnimation(mActivity, exit);
        mPopEnterAnim = AnimationUtils.loadAnimation(mActivity, popEnter);
        mPopExitAnim = AnimationUtils.loadAnimation(mActivity, popExit);
    }

    protected FragmentAnimator onCreateFragmentAnimator() {
        return null;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            if (enter) {
                return mEnterAnim;
            } else {
                return mPopExitAnim;
            }
        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
            if (enter) {
                return mPopEnterAnim;
            } else {
                return mExitAnim;
            }
        }
        return null;
    }


    protected void initBackBtn(View backBtn) {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = FragmentUtils.getParentFragmentManager(BaseFragment.this);
                FragmentUtils.back(fm);
            }
        });
    }
}
