package com.zcj.colorfulsystembar.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.zcj.colorfulsystembar.base.BaseApplication;


/**
 * Created by zcj on 2016/10/27.
 */

public class UIUtil {
    public static DisplayMetrics getMetrics() {
        DisplayMetrics dm = new DisplayMetrics();

        //方法一：
        WindowManager wm = (WindowManager) BaseApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        //方法二：
        dm = BaseApplication.getContext().getResources().getDisplayMetrics();
        return dm;
    }

    public static int getScreenWidth() {
        return getMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getMetrics().heightPixels;
    }

    public static int px2dp(int px) {
        float density = getMetrics().density;
        return (int) (px * 1.0 / density + 0.5);
    }

    public static int dp2px(int dp) {
        float density = getMetrics().density;
        return (int) (dp * 1.0 * density + 0.5);
    }

    public static int getStatusBarHeight() {
        int statusBarHeight = 0;

        //方法一：
        Activity activity = null;
        Rect rectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        statusBarHeight = rectangle.top;

        //方法二：
        int resourceId = BaseApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = BaseApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }

        //方法三：
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = BaseApplication.getContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusBarHeight;
    }

    public static int getActionBarHeight(Activity activity) {
        int actionBarHeight = 0;

        //方法一：
        actionBarHeight = activity.getActionBar().getHeight() == 0 ? activity.getActionBar().getHeight() : ((AppCompatActivity) activity).getSupportActionBar().getHeight();

        //方法二：
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        actionBarHeight = contentTop - getStatusBarHeight();

        //方法三:
        final TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
            }
        } else {
            // 使用android.support.v7.appcompat包做actionbar兼容的情况
            if (activity.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
            }
        }

        return actionBarHeight;
    }

    public static int getNavigationBarHeight(Activity activity) {
        int navigationBarHeight = 0;
        int id = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0) {
            navigationBarHeight = activity.getResources().getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    public static void postTaskSafely(Runnable task) {
        int mainThreadId = BaseApplication.getMainThreadId();
        int currentThreadId = android.os.Process.myTid();
        if (mainThreadId == currentThreadId) {
            task.run();
        } else {
            BaseApplication.getHandler().post(task);
        }
    }
}
