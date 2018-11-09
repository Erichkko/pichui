package com.pichui.news.ui.base;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pichui.news.R;
import com.pichui.news.app.Constant;
import com.pichui.news.model.entity.CommentData;
import com.pichui.news.model.entity.NewsDetail;
import com.pichui.news.model.response.CommentResponse;
import com.pichui.news.ui.adapter.news.CommentAdapter;
import com.pichui.news.ui.iview.INewsDetailView;
import com.pichui.news.ui.presenter.NewsDetailPresenter;
import com.pichui.news.ui.view.NewsDetailHeaderView;
import com.pichui.news.uitil.GlideUtils;
import com.pichui.news.uitil.ListUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public abstract class NewsDetailBaseActivity extends BaseActivity<NewsDetailPresenter> implements INewsDetailView, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String CHANNEL_CODE = "channelCode";
    public static final String PROGRESS = "progress";
    public static final String POSITION = "position";
    public static final String DETAIL_URL = "detailUrl";
    public static final String GROUP_ID = "groupId";
    public static final String ITEM_ID = "itemId";

    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    @BindView(R.id.rv)
    protected RecyclerView mRvComment;


    @BindView(R.id.tv_comment_count)
    TextView mTvCommentCount;



    private String mDetalUrl;
    private String mGroupId;
    private String mItemId;
    protected CommentResponse mCommentResponse;
    private List<CommentData> mCommentList = new ArrayList<>();

    private CommentAdapter mCommentAdapter;

    protected String mChannelCode;
    protected int mPosition;
    protected NewsDetailHeaderView mHeaderView;

    @Override
    protected int provideContentViewId() {
        return getViewContentViewId();
    }
    protected abstract int getViewContentViewId();
    @Override
    protected NewsDetailPresenter createPresenter() {
        return new NewsDetailPresenter(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();

        mChannelCode = intent.getStringExtra(CHANNEL_CODE);
        mPosition = intent.getIntExtra(POSITION, 0);

        mDetalUrl = intent.getStringExtra(DETAIL_URL);
        mGroupId = intent.getStringExtra(GROUP_ID);
        mItemId = intent.getStringExtra(ITEM_ID);
        mItemId = mItemId.replace("i", "");

        mPresenter.getNewsDetail(mDetalUrl);

    }

    @Override
    public void initView() {
        final LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRvComment.setLayoutManager(manager);
        mCommentAdapter = new CommentAdapter(this,  mCommentList);
        mRvComment.setAdapter(mCommentAdapter);

        mHeaderView = new NewsDetailHeaderView(this);
        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(true);
        mCommentAdapter.setOnLoadMoreListener(this, mRvComment);

    }

    @Override
    public void initListener() {
       loadCommentData();

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getComment(mGroupId, mItemId, mCommentList.size() / Constant.COMMENT_PAGE_SIZE + 1);
    }

    private void loadCommentData() {

        mPresenter.getComment(mGroupId, mItemId, 1);
    }


    @Override
    public void onGetCommentSuccess(CommentResponse response) {
        mCommentResponse = response;

        if (ListUtils.isEmpty(response.data)){
            //没有评论了
            mCommentAdapter.loadMoreEnd();
            return;
        }

        if (response.total_number > 0) {
            //如果评论数大于0，显示红点
            mTvCommentCount.setVisibility(View.VISIBLE);
            mTvCommentCount.setText(String.valueOf(response.total_number));
        }

        mCommentList.addAll(response.data);
        mCommentAdapter.notifyDataSetChanged();
        if (!response.has_more) {
            mCommentAdapter.loadMoreEnd();
        }else{
            mCommentAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onComplete() {
        mCommentAdapter.loadMoreComplete();
    }

}
