package com.zcj.colorfulsystembar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zcj.colorfulsystembar.R;
import com.zcj.colorfulsystembar.base.BaseActivity;
import com.zcj.colorfulsystembar.utils.ConfigUtil;
import com.zcj.colorfulsystembar.utils.UIUtil;


/**
 * Created by zcj on 2016/10/26.
 */

public class ActivityColorFulStatusBar extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_colorful_statusbar);

        setTransparentStatusbar();
        setDarkStatusBarMode();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
