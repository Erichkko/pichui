package com.pichui.news.ui.activity;



import com.pi.core.uikit.recycleview.galleryrecyclerview.GalleryRecyclerView;
import com.pi.core.uikit.recycleview.galleryrecyclerview.Item;
import com.pichui.news.R;
import com.pichui.news.ui.adapter.test.GalleryAdapter;
import com.pichui.news.ui.base.BaseActivity;
import com.pichui.news.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GalleryActivity extends BaseActivity {
    @BindView(R.id.gallery)
    public GalleryRecyclerView galleryRecyclerView;

    private List<Item> mlist=new ArrayList<>();
    private int[] mImgs ={R.mipmap.test1,R.mipmap.test2,R.mipmap.test3,R.mipmap.test4,R.mipmap.test5,R.mipmap.test6,R.mipmap.test7
            ,R.mipmap.test8,R.mipmap.test9,R.mipmap.test10,R.mipmap.test11,R.mipmap.test12,R.mipmap.test13,R.mipmap.test14,R.mipmap.test15
            ,R.mipmap.test16,R.mipmap.test17,R.mipmap.test18,R.mipmap.test19,R.mipmap.test20,R.mipmap.test21,R.mipmap.test22,R.mipmap.test23};
    private GalleryAdapter mAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        galleryRecyclerView.setCanAlpha(true);
        galleryRecyclerView.setCanScale(true);
        galleryRecyclerView.setBaseScale(0.5f);
        galleryRecyclerView.setBaseAlpha(0.95f);
        mAdapter =  new GalleryAdapter( this,mlist);
        galleryRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void initData() {
        for (int i=0;i<mImgs.length;i++){
            Item item=new Item();
            item.setImg(mImgs[i]);
            item.setName(i+"km");
            mlist.add(item);
        }


        mAdapter =  new GalleryAdapter( this,mlist);
        galleryRecyclerView.setAdapter(mAdapter);
    }

}
