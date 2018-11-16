package com.pichui.news.ui.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pichui.news.ui.fragment.BigImageFragment;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPagerAdapter extends FragmentPagerAdapter {
    private List<BigImageFragment> mFragments = new ArrayList<>();
    public ImageViewPagerAdapter(List<BigImageFragment> fragmentList, FragmentManager fm) {
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
