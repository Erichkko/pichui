package com.pichui.news.api.callback;

import android.text.TextUtils;

import com.pi.core.util.DebugLog;
import com.pichui.news.api.model.ResultResponse;
import com.pichui.news.uitil.UIUtils;

import io.reactivex.disposables.Disposable;


/**
 * @author ChayChan
 * @description: 抽取CallBack
 * @date 2017/6/18  21:37
 */
public abstract class CommonCallBack<T> extends BaseObserverCallBack<T> {


    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onComplete() {

    }
    @Override
    public void onError(Throwable e) {
        DebugLog.e(e.getLocalizedMessage());
        onError();
    }

    protected abstract void onSuccess(T response);
    protected abstract void onError();


}
