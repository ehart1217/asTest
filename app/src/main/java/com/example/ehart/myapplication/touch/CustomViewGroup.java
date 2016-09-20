package com.example.ehart.myapplication.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by ehart on 16-2-22.
 */
public class CustomViewGroup extends RelativeLayout {

    private static final String TAG = "CustomViewGroup";

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent : " + MoveActionUtils.getMotionEventAction(event));

        return super.onTouchEvent(event);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "onInterceptTouchEvent : " + MoveActionUtils.getMotionEventAction(event));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                return true;
                break;
            case MotionEvent.ACTION_MOVE:
//                return true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                return true;
//                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent : " + MoveActionUtils.getMotionEventAction(ev));

        return super.dispatchTouchEvent(ev);
    }

}
