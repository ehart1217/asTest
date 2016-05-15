package com.example.ehart.myapplication.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * fragment  工具集
 * Created by ehart on 16/5/15.
 */
public class FragmentUtils {
    private FragmentUtils() {
    }


    public static void addFragment(FragmentManager fm, int resId,Fragment fragment, String tag) {
        fm.beginTransaction().add(resId,fragment, tag).commit();
    }

    public static String generateTag(Class className){
        return "fragment_tag_" + className;
    }
}
