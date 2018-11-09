package com.pichui.news.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pi.core.uikit.recycleview.UniversalItemDecoration;
import com.pi.core.uikit.view.TipView;
import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.app.Constant;
import com.pichui.news.model.entity.News;
import com.pichui.news.ui.activity.MainActivity;
import com.pichui.news.ui.activity.NewsDetailActivity;
import com.pichui.news.ui.activity.WebViewActivity;
import com.pichui.news.ui.adapter.news.MultipleItem;
import com.pichui.news.ui.adapter.news.NewsListAdapter;
import com.pichui.news.ui.adapter.news.VideoListAdapter;
import com.pichui.news.ui.base.BaseFragment;

import com.pichui.news.ui.base.NewsDetailBaseActivity;
import com.pichui.news.ui.iview.lNewsListView;
import com.pichui.news.ui.presenter.NewsListPresenter;
import com.pichui.news.uitil.NetWorkUtils;
import com.pichui.news.uitil.UIUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class NewsListFragment extends BaseFragment <NewsListPresenter>implements lNewsListView {
    @BindView(R.id.rv)
    public RecyclerView rv;

    @BindView(R.id.refresh_Layout)
    public SmartRefreshLayout rfLayout;

    @BindView(R.id.tip_view)
    public TipView mTipView;

    List<News> data = new ArrayList<>();
    private BaseQuickAdapter mNewsAdapter;
    private String mChannelCode;
    private boolean isVideoList;
    private boolean isRecommendChannel;

    @Override
    public void initView(View rootView) {
        DebugLog.e("NewsListFragment  initView....");

        final LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new UniversalItemDecoration() {
            @Override
            public Decoration getItemOffsets(int position) {
                ColorDecoration decoration = new ColorDecoration();
                //你的逻辑设置分割线
                decoration.bottom   = 2;//下分割
//                decoration.right  = 2;// 右分割
//                decoration.left   = 2; //左分割
//                decoration.top     = 2;//上分割线
                decoration.decorationColor =  Color.parseColor("#F8F8F8");
//                分割线颜色
                return decoration;
            }
        });

    }

    private void refreshData() {
        mPresenter.getNewsList(mChannelCode);
    }
    private void loadMore() {
        rfLayout.finishLoadmore();
    }

    @Override
    public void initData() {
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE);
        isVideoList = getArguments().getBoolean(Constant.IS_VIDEO_LIST, false);
        DebugLog.e("mChannelCode == "+mChannelCode);
        DebugLog.e("isVideoList == "+isVideoList);

        String[] channelCodes = UIUtils.getStringArr(R.array.channel_code);
        isRecommendChannel = mChannelCode.equals(channelCodes[0]);//是否是推荐频道
        if (isVideoList){
            mNewsAdapter = new VideoListAdapter(data);
        }else {
            mNewsAdapter = new NewsListAdapter(data,mChannelCode);
        }
        mNewsAdapter.openLoadAnimation();
      rv.setAdapter(mNewsAdapter);

    }

    @Override
    protected void loadData() {
        DebugLog.e("NewsListFragment loadData....");
        rfLayout.autoRefresh();
    }

    @Override
    public void initListener() {
        rfLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (!NetWorkUtils.isNetworkAvailable(mActivity)) {
                    //网络不可用弹出提示
                    mTipView.show();
                    refreshlayout.finishRefresh();
                    return;
                }
                refreshData();
            }
        });
        rfLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                loadMore();

            }


        });
        //条目点击事件
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                News news = data.get(position);
                String itemId = news.item_id;
                StringBuffer urlSb = new StringBuffer("http://m.toutiao.com/i");
                urlSb.append(itemId).append("/info/");
                String url = urlSb.toString();//http://m.toutiao.com/i6412427713050575361/info/
                Intent intent = null;
                if (news.has_video) {
                    UIUtils.showToast("该功能暂未开放.......");
                    return;
                    //视频
//                    intent = new Intent(mActivity, VideoDetailActivity.class);
//                    if (JCVideoPlayerManager.getCurrentJcvd() != null) {
//                        JCVideoPlayerStandard videoPlayer = (JCVideoPlayerStandard) JCVideoPlayerManager.getCurrentJcvd();
//                        //传递进度
//                        int progress = JCMediaManager.instance().mediaPlayer.getCurrentPosition();
//                        if (progress != 0) {
//                            intent.putExtra(VideoDetailActivity.PROGRESS, progress);
//                        }
//                    }
                } else {
                    //非视频新闻
                    if (news.article_type == 1) {
                        //如果article_type为1，则是使用WebViewActivity打开
                        intent = new Intent(mActivity, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, news.article_url);
                        startActivity(intent);
                        return;
                    }
                    //其他新闻
                    intent = new Intent(mActivity, NewsDetailActivity.class);
                }

                intent.putExtra(NewsDetailBaseActivity.CHANNEL_CODE, mChannelCode);
                intent.putExtra(NewsDetailBaseActivity.POSITION, position);

                intent.putExtra(NewsDetailBaseActivity.DETAIL_URL, url);
                intent.putExtra(NewsDetailBaseActivity.GROUP_ID, news.group_id);
                intent.putExtra(NewsDetailBaseActivity.ITEM_ID, itemId);

                startActivity(intent);
            }
        });

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
        mTipView.show(tipInfo);
        data.addAll(0, newList);
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        mTipView.show();
    }

    @Override
    public void onComplete() {
        rfLayout.finishLoadmore();
        rfLayout.finishRefresh();
    }
}
