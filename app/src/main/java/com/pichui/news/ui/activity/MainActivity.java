package com.pichui.news.ui.activity;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.pi.core.uikit.bottombarlayout.BottomBarItem;
import com.pi.core.uikit.bottombarlayout.BottomBarLayout;
import com.pi.core.uikit.statusbar.Eyes;
import com.pi.core.uikit.view.NoScrollViewPager;
import com.pichui.news.R;
import com.pichui.news.ui.adapter.base.MainTabAdapter;
import com.pichui.news.ui.base.BaseActivity;
import com.pichui.news.ui.base.BaseFragment;
import com.pichui.news.ui.base.BasePresenter;
import com.pichui.news.ui.fragment.HomeFragment;
import com.pichui.news.ui.fragment.MicroFragment;
import com.pichui.news.ui.fragment.MineFragment;
import com.pichui.news.ui.fragment.NewsListFragment;
import com.pichui.news.ui.fragment.TabFragment;
import com.pichui.news.ui.fragment.VideoFragment;
import com.pichui.news.uitil.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    @BindView(R.id.vp_content)
    public NoScrollViewPager mVpContent;
    @BindView(R.id.bbl_tabbar)
    public BottomBarLayout mBottomBarLayout;
    private RotateAnimation mRotateAnimation;
    private Handler mHandler = new Handler();
    private int[] mStatusColors = new int[]{
            R.color.color_D33D3C,
            R.color.color_BDBDBD,
            R.color.color_BDBDBD,
    };
    private MainTabAdapter mTabAdapter;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    public void initData() {

        HomeFragment homeFragment = new HomeFragment();
        mFragmentList.add(homeFragment);

        VideoFragment videoFragment = new VideoFragment();
        mFragmentList.add(videoFragment);

        MicroFragment microFragment = new MicroFragment();
        mFragmentList.add(microFragment);

        MineFragment meFragment = new MineFragment();

        mFragmentList.add(meFragment);



        mTabAdapter = new MainTabAdapter(mFragmentList, getSupportFragmentManager());
        mVpContent.setAdapter(mTabAdapter);
        mVpContent.setOffscreenPageLimit(mFragmentList.size());
        mBottomBarLayout.setViewPager(mVpContent);

        setStatusBarColor(0); //默认显示第一页
    }

    public void initListener() {
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);
//
                setStatusBarColor(currentPosition); //默认显示第一页
                if (currentPosition == 0) {
                    //如果是第一个，即首页
                    if (previousPosition == currentPosition) {
                        startTabLoading(bottomBarItem,currentPosition);
                        return;
                    }
                }

                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换为原来的图标
                cancelTabLoading(bottomItem);//停止旋转动画
            }
        });

//        mBottomBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
//        mBottomBarLayout.setUnread(1, 1001);//设置第二个页签的未读数
//        mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
//        mBottomBarLayout.setMsg(3, "NEW");//设置第四个页签显示NEW提示文字

    }





    private void startTabLoading(final BottomBarItem bottomBarItem,final int currentPosition){
        //如果是在原来位置上点击,更换首页图标并播放旋转动画
        bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_loading);//更换成加载图标
        bottomBarItem.setStatus(true);

        //播放旋转动画
        if (mRotateAnimation == null) {
            mRotateAnimation = new RotateAnimation(0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            mRotateAnimation.setDuration(800);
            mRotateAnimation.setRepeatCount(-1);
        }
        ImageView bottomImageView = bottomBarItem.getImageView();
        bottomImageView.setAnimation(mRotateAnimation);
        bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画

        //模拟数据刷新完毕
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean tabNotChanged = mBottomBarLayout.getCurrentItem() == currentPosition; //是否还停留在当前页签
                bottomBarItem.setIconSelectedResourceId(R.mipmap.tab_home_selected);//更换成首页原来选中图标
                bottomBarItem.setStatus(tabNotChanged);//刷新图标
                cancelTabLoading(bottomBarItem);
            }
        }, 3000);
    }
    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }

    private void setStatusBarColor(int position) {
        if (position == 3){
            //如果是我的页面，状态栏设置为透明状态栏
            Eyes.translucentStatusBar(MainActivity.this,true);
        }else{
            Eyes.setStatusBarColor(MainActivity.this, UIUtils.getColor(mStatusColors[position]));
        }
    }
}
