package com.pichui.news.ui.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.model.entity.News;
import com.pichui.news.ui.adapter.news.MultipleItem;
import com.pichui.news.ui.adapter.news.NewsListAdapter;
import com.pichui.news.ui.base.BaseFragment;

import com.pichui.news.ui.iview.lNewsListView;
import com.pichui.news.ui.presenter.NewsListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsListFragment extends BaseFragment <NewsListPresenter>implements lNewsListView {
    @BindView(R.id.rv)
    public RecyclerView rv;
    List<MultipleItem> data = new ArrayList<>();
    private NewsListAdapter multipleItemAdapter;

    @Override
    public void initView(View rootView) {
        DebugLog.e("initView....");
         multipleItemAdapter = new NewsListAdapter(mActivity, data);
        final LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);

        rv.setAdapter(multipleItemAdapter);
    }

    @Override
    protected void loadData() {
        DebugLog.e("loadData....");
        mPresenter.getNewsList("推荐");
        MultipleItem item1 = new MultipleItem(MultipleItem.IMG_TEXT,"1");
        MultipleItem item2 = new MultipleItem(MultipleItem.IMG,"2");
        MultipleItem item3 = new MultipleItem(MultipleItem.TEXT,"3");
        MultipleItem item4 = new MultipleItem(MultipleItem.IMG_TEXT,"4");
        data.add(item1);
        data.add(item2);
        data.add(item3);
        data.add(item4);
        multipleItemAdapter.notifyDataSetChanged();
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

    }

    @Override
    public void onError() {

    }
}
