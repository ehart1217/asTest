package com.example.ehart.myapplication.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by ehart on 16-2-22.
 */
public class CustomViewGroup2 extends RelativeLayout {

    private static final String TAG = "CustomViewGroup2";

    public CustomViewGroup2(Context context) {
        super(context);
    }

    public CustomViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent : " + MoveActionUtils.getMotionEventAction(event));
        return super.onTouchEvent(event);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent : " + MoveActionUtils.getMotionEventAction(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent : " + MoveActionUtils.getMotionEventAction(ev));
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
////                return true;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                return true;
////                break;
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
////                return true;
//                break;
//        }
        return super.dispatchTouchEvent(ev);
    }

}
