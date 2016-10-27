package com.zcj.colorfulsystembar.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by zcj on 2016/10/25.
 */

public class BaseApplication extends Application {
    private static Context sContext;
    private static Handler sHandler;
    private static int sMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sMainThreadId = android.os.Process.myPid();
        sHandler = new Handler();
    }

    public static Context getContext() {
        return sContext;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    public static int getMainThreadId() {
        return sMainThreadId;
    }
}
