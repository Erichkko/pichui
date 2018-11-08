package com.pichui.news.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pi.core.uikit.slidingLayout.SlidingLayout;
import com.pi.core.uikit.slidingLayout.SwipeLayout;
import com.pichui.news.R;
import com.pichui.news.ui.activity.MainActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private static Activity mCurrentActivity;// 对所有activity进行管理
    public static final List<Activity> mActivities = new LinkedList<>();
    private static long mPreTime;
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            mActivities.add(this);
        }

        setContentView(provideContentViewId());
        ButterKnife.bind(this);

        mPresenter = createPresenter();

        initView();
        initData();
        initListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
        }

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 退出应用的方法
     */
    public static void exitApp() {

        ListIterator<Activity> iterator = mActivities.listIterator();

        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }
    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity){
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
//                UIUtils.showToast("再按一次，退出应用");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
    }
    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();
//    @Override
//    public void startActivity(Intent intent, Bundle options) {
//        super.startActivity(intent, options);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//    }
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
//        super.startActivityForResult(intent, requestCode, options);
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
//    }
//    @Override
//    public void finish() {
//
//        super.finish();
//        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
//    }
}
