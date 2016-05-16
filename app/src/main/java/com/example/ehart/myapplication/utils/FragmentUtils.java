package com.example.ehart.myapplication.utils;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransactionBugFixHack;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ehart.myapplication.manage.FragmentRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment  工具集
 * Created by ehart on 16/5/15.
 */
public class FragmentUtils {
    private FragmentUtils() {
    }


    public static void addFragment(FragmentManager fm, int resId, Fragment fragment, String tag) {
        fm.beginTransaction().add(resId, fragment, tag).commit();
    }

    public static String generateTag(Class className) {
        return className.getName();
    }

    /**
     * 移除fragment，默认没有动画
     */
    public static void removeFragment(final Fragment fragment) {
        FragmentManager fm = getParentFragmentManager(fragment);
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
    }

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

    public static void start(FragmentManager fm, Fragment from, Fragment to, int resId, boolean finishPrevious) {
        if (finishPrevious) {
            startWithFinish(fm, from, to, resId);
        } else {
            startWithoutFinish(fm, from, to, resId);
        }

    }

    public static void show(FragmentManager fm, Fragment from, Fragment to) {
        if (from == to) {
            return;
        }
        String toName = to.getClass().getName();
        FragmentTransaction ft = fm.beginTransaction();
        if (from != null) {
            ft.hide(from);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).show(to).addToBackStack(toName).commit();
    }

    private static void startWithoutFinish(FragmentManager fm, Fragment from, Fragment to, int resId) {
        String toName = to.getClass().getName();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(resId, to, toName);

        if (from != null) {
            ft.hide(from);
        }

        ft.addToBackStack(toName);
        ft.commit();
    }

    private static void startWithFinish(FragmentManager fm, Fragment from, Fragment to, int resId) {
        Fragment preFragment = fixAnim(fm, from, resId);
        if (from != null) {
            fm.beginTransaction().remove(from).commit();
            fm.popBackStack();
        }

        String toName = to.getClass().getName();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(resId, to, toName)
                .addToBackStack(toName);

        if (preFragment != null) {
            ft.hide(preFragment);
        }
        ft.commit();
    }

    /**
     * 修复动画
     *
     * @return 前一个fragment
     */
    @Nullable
    private static Fragment fixAnim(FragmentManager fm, Fragment from, int containerId) {
        if (from == null) {
            return null;
        }
        Fragment preFragment = getPreFragment(fm, from);
        Activity activity = from.getActivity();
        if (preFragment != null) {
            View view = preFragment.getView();
            if (view != null) {
                // 不调用 会闪屏
                view.setVisibility(View.VISIBLE);

                ViewGroup viewGroup;
                final View fromView = from.getView();

                if (fromView != null) {
                    if (view instanceof ViewGroup) {
                        viewGroup = (ViewGroup) view;
                        ViewGroup container = (ViewGroup) activity.findViewById(containerId);
                        if (container != null) {
                            container.removeView(fromView);
                            if (fromView.getLayoutParams().height != ViewGroup.LayoutParams.MATCH_PARENT) {
                                fromView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                            }

                            if (viewGroup instanceof LinearLayout) {
                                viewGroup.addView(fromView, 0);
                            } else {
                                viewGroup.addView(fromView);
                            }
                        }
                    }
                }
            }
        }
        return preFragment;
    }

    /**
     * 得到相同栈内前一个fragment
     */
    public static Fragment getPreFragment(FragmentManager fm, Fragment fragment) {
        if (fm == null) {
            return null;
        }
        List<Fragment> fragmentList = fm.getFragments();
        Fragment preFragment = null;
        if (fragmentList != null) {
            int index = fragmentList.indexOf(fragment);
            for (int i = index - 1; i >= 0; i--) {
                preFragment = fragmentList.get(i);
                if (preFragment != null) {
                    break;
                }
            }
        }
        return preFragment;
    }

    public static void back(FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();

        if (count > 1) {
            fragmentManager.popBackStackImmediate();
            FragmentTransactionBugFixHack.reorderIndices(fragmentManager);
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
     * 获得栈顶Fragment
     *
     * @return
     */
    public static Fragment getTopFragment(FragmentManager fragmentManager) {
        int count = fragmentManager.getBackStackEntryCount();
        if (count > 0) {
            String tag = fragmentManager.getBackStackEntryAt(count - 1).getName();
            return fragmentManager.findFragmentByTag(tag);
        }
        return null;
    }

    /**
     * 显示topfragment，隐藏其他的fragment。
     */
    public static void showTopHideOtherFragment(FragmentManager fm) {
        Fragment top = getTopFragment(fm);
        if (top != null) {
            List<Fragment> fragmentList = fm.getFragments();
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment fragment : fragmentList) {
                if (top.getClass().equals(fragment.getClass())) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }
    }

}
