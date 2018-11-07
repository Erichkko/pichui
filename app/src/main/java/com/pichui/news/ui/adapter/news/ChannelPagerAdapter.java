package com.pichui.news.ui.adapter.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.pichui.news.model.entity.Channel;
import com.pichui.news.ui.fragment.NewsListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChayChan
 * @description: 频道的adapter
 * @date 2017/6/16  9:45
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<NewsListFragment> mFragments;
    private List<Channel> mChannels;

    public ChannelPagerAdapter(List<NewsListFragment> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        if ( fragmentList != null){
            mFragments = fragmentList;
        }else {
            mFragments =  new ArrayList<>();
        }
        if ( channelList != null){
            mChannels = channelList;
        }else {
            mChannels =  new ArrayList<>();
        }

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).title;
    }

    @Override
    public int getItemPosition(Object object) {
        //        if (mChildCount > 0) {
        //            mChildCount--;
        return POSITION_NONE;
        //        }
        //        return super.getItemPosition(object);
    }
}
