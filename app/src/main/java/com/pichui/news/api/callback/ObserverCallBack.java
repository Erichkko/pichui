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
public abstract class  ObserverCallBack<T> extends BaseObserverCallBack<ResultResponse<T>> {

    @Override
    public void onNext(ResultResponse response) {
        boolean isSuccess = (!TextUtils.isEmpty(response.message) && response.message.equals("success"))
                || !TextUtils.isEmpty(response.success) && response.success.equals("true");
        if (isSuccess) {
            onSuccess((T) response.data);
        } else {
            UIUtils.showToast(response.message);
            onFailure(response);
        }
    }

    @Override
    public void onComplete() {
        onCompleted();
    }
    @Override
    public void onError(Throwable e) {
        DebugLog.e(e.getLocalizedMessage());
        onError();
    }

    protected abstract void onSuccess(T response);
    protected abstract void onError();
    protected abstract void onCompleted();
    protected void onFailure(ResultResponse response) {
    }

}
