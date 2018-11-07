package com.pichui.news.api.callback;

import android.text.TextUtils;

import com.pi.core.util.DebugLog;
import com.pichui.news.api.model.ResultResponse;
import com.pichui.news.uitil.UIUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class BaseObserverCallBack<T> implements Observer<T> {

    public Disposable disposable ;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }


}
