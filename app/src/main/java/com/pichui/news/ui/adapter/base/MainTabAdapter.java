package com.pichui.news.ui.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.pichui.news.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @description: 首页页签的adapter
 * @date
 */

public class MainTabAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments = new ArrayList<BaseFragment>();

    public MainTabAdapter(List<BaseFragment> fragmentList, FragmentManager fm) {
        super(fm);
        if (fragmentList != null){
            mFragments = fragmentList;
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
}
