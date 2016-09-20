package com.example.ehart.myapplication.fragmentation.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransactionBugFixHack;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.ehart.myapplication.fragmentation.manage.FragmentRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment  工具集
 * Created by ehart on 16/5/15.
 */
public class FragmentUtils {

    private static final String TAG = "CRFragmentUtils";

    private FragmentUtils() {
    }

    public static String generateTag(Class className) {
        return className.getName();
    }

    @Nullable
    public static FragmentManager getParentFragmentManager(final Fragment fragment) {
        if (fragment == null) {
            return null;
        }
        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment != null) {
            return parentFragment.getChildFragmentManager();
        }
        if (fragment.getActivity() instanceof AppCompatActivity) {
            AppCompatActivity parentActivity = (AppCompatActivity) fragment.getActivity();
            return parentActivity.getSupportFragmentManager();
        }
        return null;
    }

    /**
     * add一个fragment，不添加到回退栈
     */
    public static void add(FragmentManager fm, Fragment fragment, int resId) {
        String tag = generateTag(fragment.getClass());
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(resId, fragment, tag);
        ft.commitAllowingStateLoss();
    }


    /**
     * replace一个fragment，不添加到回退栈
     */
    public static void replace(@NonNull FragmentManager fragmentManager,
                               @NonNull Fragment fragment, int frameId) {
        String tag = generateTag(fragment.getClass());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (TextUtils.isEmpty(tag)) {
            transaction.replace(frameId, fragment);
        } else {
            transaction.replace(frameId, fragment, tag);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * show一个fragment，不添加到回退栈
     */
    public static void show(FragmentManager fm, Fragment fragment) {
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    public static void hide(FragmentManager fm, Fragment fragment) {
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    /**
     * 添加fragment，会添加到回退栈。
     */
    public static void addWithBackStack(FragmentManager fm, Fragment to, int resId) {
        addWithBackStack(fm, to, resId, false);
    }

    /**
     * add一个fragment，并且加入到回退栈
     *
     * @param fm             FragmentManager
     * @param to             要显示的fragment
     * @param resId          资源id
     * @param finishPrevious 是否干掉当前栈顶fragment
     */
    public static void addWithBackStack(FragmentManager fm, Fragment to, int resId, boolean finishPrevious) {

        Fragment from = getTopFragment(fm);
        addWithBackStack(fm, from, to, resId, finishPrevious);
    }

    /**
     * add一个fragment，并且加入到回退栈
     *
     * @param fm             FragmentManager
     * @param from           当前栈顶fragment
     * @param to             要显示的fragment
     * @param resId          资源id
     * @param finishPrevious 是否干掉当前栈顶fragment
     */
    public static void addWithBackStack(FragmentManager fm, Fragment from, Fragment to, int resId, boolean finishPrevious) {

        if (from == to) {
            return;
        }

        if (finishPrevious) {
            addWithFinish(fm, from, to, resId);
        } else {
            addWithoutFinish(fm, null, to, resId); // 这里把from传递为null，是为了不隐藏它，因为不需要那个open动画
        }
    }

    /**
     * show一个fragment，并且加入到回退栈
     *
     * @param fm FragmentManager
     * @param to 要显示的fragment
     */
    public static void showWithBackStack(FragmentManager fm, Fragment to) {
        Fragment from = getTopFragment(fm);
        String toName = generateTag(to.getClass());
        FragmentTransaction ft = fm.beginTransaction();
        if (from != null && from != to) {
            ft.hide(from);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).show(to).addToBackStack(toName).commitAllowingStateLoss();
    }

    private static void addWithoutFinish(FragmentManager fm, Fragment from, Fragment to, int resId) {
        String toName = generateTag(to.getClass());
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(resId, to, toName);
        if (from != null) {
            ft.hide(from);
        }
        ft.addToBackStack(toName);
        ft.commitAllowingStateLoss();
    }

    private static void addWithoutFinish(FragmentManager fm, Fragment to, int resId) {
        addWithoutFinish(fm, null, to, resId);
    }

    private static void addWithFinish(FragmentManager fm, Fragment from, Fragment to, int resId) {
        if (from != null) {
            fm.popBackStackImmediate();
            fm.beginTransaction().remove(from).commitAllowingStateLoss();
        }

        String toName = generateTag(to.getClass());
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(resId, to, toName)
                .addToBackStack(toName);

        ft.commitAllowingStateLoss();
    }

    /**
     * 弹出一个回退栈里的fragment
     */
    public static void pop(Fragment fragment) {
        pop(getParentFragmentManager(fragment));
    }


    /**
     * 弹出一个回退栈里的fragment
     */
    public static void pop(FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();

        if (count > 0) {
            fragmentManager.popBackStackImmediate();
            FragmentTransactionBugFixHack.reorderIndices(fragmentManager);
        }
    }

    /**
     * 弹出回退栈里的所有的fragment
     *
     * @param fragment 其中的任何一个fragment
     */
    public static void popAll(Fragment fragment) {
        FragmentManager fm = getParentFragmentManager(fragment);
        popAll(fm);

    }

    /**
     * 弹出回退栈里所有的fragment
     */
    public static void popAll(FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    /**
     * 得到所有的fragment记录
     */
    public static List<FragmentRecord> getFragmentRecords(AppCompatActivity activity) {
        List<FragmentRecord> fragmentRecords = new ArrayList<>();

        List<Fragment> fragmentList = activity.getSupportFragmentManager().getFragments();
        if (fragmentList == null || fragmentList.size() < 1) return null;

        for (Fragment fragment : fragmentList) {
            if (fragment == null) continue;
            fragmentRecords.add(new FragmentRecord(fragment.getClass().getSimpleName(), getChildFragmentRecords(fragment)));
        }

        return fragmentRecords;
    }

    private static List<FragmentRecord> getChildFragmentRecords(Fragment parentFragment) {
        List<FragmentRecord> fragmentRecords = new ArrayList<>();

        List<Fragment> fragmentList = parentFragment.getChildFragmentManager().getFragments();
        if (fragmentList == null || fragmentList.size() < 1) return null;

        for (int i = fragmentList.size() - 1; i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment != null) {
                fragmentRecords.add(new FragmentRecord(fragment.getClass().getSimpleName(), getChildFragmentRecords(fragment)));
            }
        }
        return fragmentRecords;
    }


    /**
     * 获得回退栈栈顶Fragment
     */
    public static
    @Nullable
    Fragment getTopFragment(FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            String tag = fragmentManager.getBackStackEntryAt(count - 1).getName();
            return fragmentManager.findFragmentByTag(tag);
        }
        return null;
    }

    /**
     * 显示回退栈的topfragment，隐藏其他的fragment。
     */
    public static void showTopHideOtherFragment(FragmentManager fm) {
        Fragment top = getTopFragment(fm);
        if (top != null) {
            List<Fragment> fragmentList = fm.getFragments();
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment fragment : fragmentList) {
                if (fragment == null) {
                    continue;
                }
                if (top.getClass().equals(fragment.getClass())) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
            }
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 移除fragment，没有动画
     */
    public static void remove(final Fragment fragment) {
        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment != null) {
            FragmentManager fm = parentFragment.getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        }
        if (fragment.getActivity() instanceof AppCompatActivity) {
            AppCompatActivity parentActivity = (AppCompatActivity) fragment.getActivity();
            FragmentManager fm = parentActivity.getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        }
    }

}
