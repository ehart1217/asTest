package com.example.ehart.myapplication.touch;

import android.view.MotionEvent;

/**
 * Created by ehart on 16-2-22.
 */
public class MoveActionUtils {

    public static String getMotionEventAction(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_UP";
            default:
                return "ACTION_OTHER";
        }
    }
}
