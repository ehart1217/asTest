package com.example.ehart.myapplication.fragmentation.anim;


import com.example.ehart.myapplication.R;

/**
 * Created by YoKeyword on 16/2/5.
 */
public class DefaultVerticalAnimator extends FragmentAnimator {

    public DefaultVerticalAnimator() {
        enter = R.anim.v_fragment_enter;
        exit = R.anim.v_fragment_exit;
        popEnter = R.anim.v_fragment_pop_enter;
        popExit = R.anim.v_fragment_pop_exit;
    }
}
