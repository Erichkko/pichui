package com.pichui.news.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pi.core.util.DebugLog;
import com.pichui.news.R;
import com.pichui.news.app.Constant;
import com.pichui.news.model.entity.Channel;
import com.pichui.news.ui.adapter.news.ChannelPagerAdapter;
import com.pichui.news.ui.base.BaseFragment;
import com.pichui.news.ui.base.BasePresenter;
import com.pichui.news.uitil.PreUtils;
import com.pichui.news.uitil.UIUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;


    private List<Channel> mSelectedChannels = new ArrayList<>();
    private List<Channel> mUnSelectedChannels = new ArrayList<>();
    private List<NewsListFragment> mChannelFragments = new ArrayList<>();
    private ChannelPagerAdapter mChannelPagerAdapter;
    private Gson mGson = new Gson();
    private String[] mChannelCodes;

    @Override
    protected void loadData() {
        DebugLog.e("HomeFragment...loadData"
        );
    }

    @Override
    public void initData() {
        DebugLog.e("HomeFragment...initData");
        initChannelData();
        initChannelFragments();
        initMagicIndicator();
        initViewPager();
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initMagicIndicator(){

        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mSelectedChannels == null ? 0 : mSelectedChannels.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mSelectedChannels.get(index).title);
                simplePagerTitleView.setTextSize(16);//设置导航的文字大小
                simplePagerTitleView.setNormalColor(Color.BLACK);//正常状态下的标题颜色
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.tab_selected_color));//选中的标题字体颜色
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }
    private void initViewPager() {
        mChannelPagerAdapter = new ChannelPagerAdapter(mChannelFragments, mSelectedChannels,getChildFragmentManager());
        mViewPager.setAdapter(mChannelPagerAdapter);
        mViewPager.setOffscreenPageLimit(mSelectedChannels.size());
    }
    /**
     * 初始化已选频道和未选频道的数据
     */
    private void initChannelData() {
        String selectedChannelJson = PreUtils.getString(Constant.SELECTED_CHANNEL_JSON, "");
        String unselectChannel = PreUtils.getString(Constant.UNSELECTED_CHANNEL_JSON, "");

        if (TextUtils.isEmpty(selectedChannelJson) || TextUtils.isEmpty(unselectChannel)) {
            //本地没有title
            String[] channels = getResources().getStringArray(R.array.channel);
            String[] channelCodes = getResources().getStringArray(R.array.channel_code);
            //默认添加了全部频道
            for (int i = 0; i < channelCodes.length; i++) {
                String title = channels[i];
                String code = channelCodes[i];
                mSelectedChannels.add(new Channel(title, code));
            }

            selectedChannelJson = mGson.toJson(mSelectedChannels);//将集合转换成json字符串
            DebugLog.e("selectedChannelJson:" + selectedChannelJson);
            PreUtils.putString(Constant.SELECTED_CHANNEL_JSON, selectedChannelJson);//保存到sp
        } else {
            //之前添加过
            List<Channel> selectedChannel = mGson.fromJson(selectedChannelJson, new TypeToken<List<Channel>>() {
            }.getType());
            List<Channel> unselectedChannel = mGson.fromJson(unselectChannel, new TypeToken<List<Channel>>() {
            }.getType());
            mSelectedChannels.addAll(selectedChannel);
            mUnSelectedChannels.addAll(unselectedChannel);
        }
    }

    /**
     * 初始化已选频道的fragment的集合
     */
    private void initChannelFragments() {
        DebugLog.e("initChannelFragments");
        mChannelCodes = getResources().getStringArray(R.array.channel_code);
        for (Channel channel : mSelectedChannels) {
            NewsListFragment newsFragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
            bundle.putBoolean(Constant.IS_VIDEO_LIST, channel.channelCode.equals(mChannelCodes[1]));//是否是视频列表页面,根据判断频道号是否是视频
            newsFragment.setArguments(bundle);
            mChannelFragments.add(newsFragment);//添加到集合中
        }
    }

}
