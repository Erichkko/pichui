package com.pichui.news.ui.fragment;

import android.view.View;

import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.ui.base.BaseFragment;
import com.pichui.news.ui.base.BasePresenter;

public class MicroFragment extends BaseFragment {
    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_micro;
    }


    @Override
    protected void loadData() {
        DebugLog.e("MicroFragment...loadData");
    }

    @Override
    public void initData() {
//        DebugLog.e("MicroFragment...initData");
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
