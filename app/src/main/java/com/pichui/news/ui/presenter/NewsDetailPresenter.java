package com.pichui.news.ui.presenter;

import com.pi.core.util.DebugLog;
import com.pichui.news.api.callback.CommonCallBack;
import com.pichui.news.api.callback.ObserverCallBack;
import com.pichui.news.app.Constant;
import com.pichui.news.model.entity.NewsDetail;
import com.pichui.news.model.response.CommentResponse;
import com.pichui.news.ui.base.BasePresenter;
import com.pichui.news.ui.iview.INewsDetailView;
import com.pichui.news.ui.iview.lNewsListView;

import org.reactivestreams.Subscriber;

public class NewsDetailPresenter  extends BasePresenter<INewsDetailView> {
    public NewsDetailPresenter(INewsDetailView view) {
        super(view);
    }
    public void getComment(String groupId, String itemId, int pageNow) {
        int offset = (pageNow - 1) * Constant.COMMENT_PAGE_SIZE;
        addSubscription(mApiService.getComment(groupId, itemId, offset + "", String.valueOf(Constant.COMMENT_PAGE_SIZE)), new CommonCallBack<CommentResponse>() {


            @Override
            protected void onSuccess(CommentResponse response) {
                mView.onGetCommentSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }

            @Override
            protected void onCompleted() {
                mView.onComplete();
            }
        });
    }

    public void getNewsDetail(String url) {
        addSubscription(mApiService.getNewsDetail(url), new ObserverCallBack<NewsDetail>() {


            @Override
            protected void onSuccess(NewsDetail response) {
                mView.onGetNewsDetailSuccess(response);
            }

            @Override
            protected void onError() {
                mView.onError();
            }

            @Override
            protected void onCompleted() {
                mView.onComplete();
            }
        });
    }
}
