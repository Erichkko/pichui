package com.pichui.news.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.app.Constant;
import com.pichui.news.model.entity.News;
import com.pichui.news.ui.adapter.news.MultipleItem;
import com.pichui.news.ui.adapter.news.NewsListAdapter;
import com.pichui.news.ui.base.BaseFragment;

import com.pichui.news.ui.iview.lNewsListView;
import com.pichui.news.ui.presenter.NewsListPresenter;
import com.pichui.news.uitil.UIUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsListFragment extends BaseFragment <NewsListPresenter>implements lNewsListView {
    @BindView(R.id.rv)
    public RecyclerView rv;

    @BindView(R.id.refresh_Layout)
    public SmartRefreshLayout rfLayout;

    List<MultipleItem> data = new ArrayList<>();
    private NewsListAdapter multipleItemAdapter;
    private String mChannelCode;
    private boolean isVideoList;
    private boolean isRecommendChannel;

    @Override
    public void initView(View rootView) {
        DebugLog.e("NewsListFragment  initView....");
         multipleItemAdapter = new NewsListAdapter(mActivity, data);
        final LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);
        rv.setAdapter(multipleItemAdapter);

        rfLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
            }
        });
        rfLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                loadMore();

            }


        });
    }

    private void refreshData() {
        mPresenter.getNewsList(mChannelCode);
    }
    private void loadMore() {
    }

    @Override
    public void initData() {
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE);
        isVideoList = getArguments().getBoolean(Constant.IS_VIDEO_LIST, false);
        DebugLog.e("mChannelCode == "+mChannelCode);
        DebugLog.e("isVideoList == "+isVideoList);

        String[] channelCodes = UIUtils.getStringArr(R.array.channel_code);
        isRecommendChannel = mChannelCode.equals(channelCodes[0]);//是否是推荐频道
    }

    @Override
    protected void loadData() {
        DebugLog.e("NewsListFragment loadData....");
        refreshData();
//        MultipleItem item1 = new MultipleItem(MultipleItem.IMG_TEXT,"1");
//        MultipleItem item2 = new MultipleItem(MultipleItem.IMG,"2");
//        MultipleItem item3 = new MultipleItem(MultipleItem.TEXT,"3");
//        MultipleItem item4 = new MultipleItem(MultipleItem.IMG_TEXT,"4");
//        data.add(item1);
//        data.add(item2);
//        data.add(item3);
//        data.add(item4);

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_newslist;
    }

    @Override
    protected NewsListPresenter createPresenter() {
        return new NewsListPresenter(this);
    }

    @Override
    public void onGetNewsListSuccess(List<News> newList, String tipInfo) {
        multipleItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onComplete() {
        rfLayout.finishLoadmore();
        rfLayout.finishRefresh();
    }
}
