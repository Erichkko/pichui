package com.pichui.news.ui.presenter;


import com.google.gson.Gson;
import com.pi.core.util.DebugLog;
import com.pichui.news.api.callback.CommonCallBack;
import com.pichui.news.api.callback.ObserverCallBack;
import com.pichui.news.model.entity.News;
import com.pichui.news.model.entity.NewsData;
import com.pichui.news.model.response.NewsResponse;
import com.pichui.news.ui.base.BasePresenter;
import com.pichui.news.ui.iview.lNewsListView;
import com.pichui.news.uitil.ListUtils;
import com.pichui.news.uitil.PreUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * @author ChayChan
 * @description: 新闻列表的presenter
 * @date 2017/6/18  10:04
 */

public class NewsListPresenter extends BasePresenter<lNewsListView> {

    private long lastTime;

    public NewsListPresenter(lNewsListView view) {
        super(view);
    }


    public void getNewsList(String channelCode){
        lastTime = PreUtils.getLong(channelCode,0);//读取对应频道下最后一次刷新的时间戳
        if (lastTime == 0){
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000;
        }

        addSubscription(mApiService.getNewsList(channelCode, lastTime, System.currentTimeMillis() / 1000),
                new CommonCallBack<NewsResponse>() {
                    @Override
                    protected void onSuccess(NewsResponse response) {
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(response);
                        DebugLog.json(jsonData);
                    }

                    @Override
                    protected void onError() {
                        DebugLog.e("onError == ");
                    }

                    @Override
                    protected void onCompleted() {
                        mView.onComplete();
                        DebugLog.e("onComplete == ");
                    }

                }
        );
    }
}
