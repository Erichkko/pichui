package com.pichui.news.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.pi.core.uikit.statusbar.Eyes;
import com.pichui.news.R;
import com.pichui.news.ui.base.BaseActivity;
import com.pichui.news.uitil.UIUtils;




public class SplashActivity extends BaseActivity {



    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        Eyes.translucentStatusBar(this,false);//
        UIUtils.postTaskDelay(new Runnable() {
            @Override
            public void run() {
                 startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },2000);
    }

}
