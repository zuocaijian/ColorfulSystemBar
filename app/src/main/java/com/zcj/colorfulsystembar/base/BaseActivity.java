package com.zcj.colorfulsystembar.base;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.zcj.colorfulsystembar.R;
import com.zcj.colorfulsystembar.utils.UIUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 1、状态栏显示(透明、上色、默认);
 * 2、标题栏显示(有标题栏，无标题栏，自定义标题栏)；
 * 3、是否开启手势返回。
 * Created by zcj on 2016/10/25.
 */

public class BaseActivity extends AppCompatActivity {


    /**
     * 状态栏透明
     */
    protected void setTransparentStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param colorId
     */
    protected void setStatusBarColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(colorId));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTransparentStatusbar(); //先将状态栏设置为透明

            //方法一：为状态栏添加一个带颜色的背景。此方法需要在布局文件中添加android:fitsSystemWindows="true"属性
            View view = new View(this);
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    UIUtil.getStatusBarHeight());
            ViewGroup decorView = (ViewGroup) findViewById(android.R.id.content);
            decorView.addView(view, params);


            //方法二：使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
            /*SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(colorId);*/
        }
    }

    /**
     * 深色状态栏字体及图标
     */
    protected void setDarkStatusBarMode() {
        setAPI23DarkStatusBarMode();
        setMiuiDarkStatusBarMode(true);
        setMeizuDarkStatusBarMode(true);
    }

    private void setAPI23DarkStatusBarMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void setMiuiDarkStatusBarMode(boolean darkMode) {
        Class<? extends Window> clazz = getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMeizuDarkStatusBarMode(boolean darkMode) {
        try {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class
                    .getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkMode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
