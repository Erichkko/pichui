package com.pichui.news.ui.fragment;

import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.ui.base.BaseFragment;

public class VideoFragment extends BaseFragment {
    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }
    @Override
    protected void loadData() {
        DebugLog.e("VideoFragment...loadData");
    }

    @Override
    public void initData() {
        DebugLog.e("VideoFragment...initData");
    }
}
