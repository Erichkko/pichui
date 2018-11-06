package com.pichui.news.ui.fragment;

import android.view.View;

import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.ui.base.BaseFragment;

public class MicroFragment extends BaseFragment {
    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_micro;
    }
    /**
     * 初始化一些view
     * @param rootView
     */
    public void initView(View rootView) {
        DebugLog.e("MicroFragment....");
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置listener的操作
     */
    public void initListener() {

    }
}
