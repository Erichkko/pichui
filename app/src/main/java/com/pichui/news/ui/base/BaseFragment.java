package com.pichui.news.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends LazyLoadFragment {


    private View rootView;

    protected Activity mActivity;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(),container,false);
            unbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    /**
     * 初始化一些view
     * @param rootView
     */
    public void initView(View rootView) {
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置listener的操作
     */
    public void initListener() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        rootView = null;
        unbinder.unbind();
    }


    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

}
