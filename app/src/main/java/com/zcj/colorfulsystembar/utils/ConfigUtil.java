package com.zcj.colorfulsystembar.utils;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by zcj on 2016/10/27.
 */

public class ConfigUtil {
    public static void getFlavor(Activity activity) {
        getAppMetaData(activity, "FLAVOR_CHANNEL");
    }

    //获取Application中的元数据信息
    public static String getAppMetaData(Activity activity, String name) {
        String result = "";
        try {
            ApplicationInfo applicationInfo = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
            if (null != applicationInfo) {
                Bundle metaData = applicationInfo.metaData;
                if (null != metaData) {
                    result = metaData.getString(name);
                    return result;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "get meta-data error!";
        }
        return result;
    }
}
