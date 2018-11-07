package com.pichui.news.ui.fragment;

import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.ui.base.BaseFragment;
import com.pichui.news.ui.base.BasePresenter;

public class HomeFragment extends BaseFragment {
    @Override
    protected void loadData() {
        DebugLog.e("HomeFragment...loadData");
    }

    @Override
    public void initData() {
        DebugLog.e("HomeFragment...initData");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
