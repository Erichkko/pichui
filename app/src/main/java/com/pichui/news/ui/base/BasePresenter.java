package com.pichui.news.ui.base;


import com.pichui.news.api.ApiRetrofit;
import com.pichui.news.api.ApiService;
import com.pichui.news.api.callback.BaseObserverCallBack;
import com.pichui.news.api.callback.ObserverCallBack;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public abstract class BasePresenter<V> {

    protected ApiService mApiService = ApiRetrofit.getInstance().getApiService();
    protected V mView;
    private CompositeDisposable composite;

    public BasePresenter(V view) {
        attachView(view);
    }

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        onUnsubscribe();
    }


    public void addSubscription(Observable observable, BaseObserverCallBack observer) {
        if (composite == null) {
            composite = new CompositeDisposable();
        }
         observable.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(observer);

        composite.add(observer.disposable);
    }

    //RXjava取消注册，以避免内存泄露
    private void onUnsubscribe() {
        if (composite != null && composite.isDisposed()) {
            composite.dispose();
        }
    }
}