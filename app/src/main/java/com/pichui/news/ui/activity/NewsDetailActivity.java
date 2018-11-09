package com.pichui.news.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.core.uikit.statusbar.Eyes;
import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.model.entity.NewsDetail;
import com.pichui.news.ui.base.NewsDetailBaseActivity;
import com.pichui.news.ui.view.NewsDetailHeaderView;
import com.pichui.news.uitil.GlideUtils;
import com.pichui.news.uitil.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsDetailActivity extends NewsDetailBaseActivity {

    @BindView(R.id.ll_user)
    LinearLayout mLlUser;

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;

    @BindView(R.id.tv_author)
    TextView mTvAuthor;

    @Override
    public void initView() {
        super.initView();
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_BDBDBD));//设置状态栏的颜色为灰色
    }
    @Override
    public void onGetNewsDetailSuccess(NewsDetail newsDetail) {

        mHeaderView.setDetail(newsDetail, new NewsDetailHeaderView.LoadWebListener() {
            @Override
            public void onLoadFinished() {
                //加载完成后，显示内容布局
//                mStateView.showContent();


            }
        });

        mLlUser.setVisibility(View.GONE);

        if (newsDetail.media_user != null){
            GlideUtils.loadRound(this,newsDetail.media_user.avatar_url, mIvAvatar);
            mTvAuthor.setText(newsDetail.media_user.screen_name);
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        initScrollListen();

    }

    private void  initScrollListen(){
        final int llInfoBottom = mHeaderView.mLlInfo.getBottom();
        final LinearLayoutManager layoutManager = (LinearLayoutManager) mRvComment.getLayoutManager();
        mRvComment.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int position = layoutManager.findFirstVisibleItemPosition();
                View firstVisiableChildView = layoutManager.findViewByPosition(position);
                int itemHeight = firstVisiableChildView.getHeight();
                int scrollHeight = (position) * itemHeight - firstVisiableChildView.getTop();

                DebugLog.e("scrollHeight: " + scrollHeight);
                DebugLog.e("llInfoBottom: " + llInfoBottom);

                mLlUser.setVisibility(scrollHeight > llInfoBottom ? View.VISIBLE : View.GONE);//如果滚动超过用户信息一栏，显示标题栏中的用户头像和昵称
            }
        });
    }
    @Override
    protected int getViewContentViewId() {
        return R.layout.activity_news_detail;
    }
    @OnClick({R.id.iv_back})
    public void onViewClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }

    }
}
