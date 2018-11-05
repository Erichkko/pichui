package com.pichui.news.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.pichui.news.R;
import com.pichui.news.ui.adapter.base.SectionedSpanSizeLookup;
import com.pichui.news.ui.adapter.test.Test1Adapter;
import com.pichui.news.ui.adapter.test.TestEntity;
import com.pichui.news.uitil.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Test1Activity extends AppCompatActivity {
    @BindView(R.id.rv)
    public RecyclerView mRecyclerView;
    private Test1Adapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);
        initView();
    }

  private void initView() {
        mAdapter = new Test1Adapter(this);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
         TestEntity entity = JsonUtils.analysisJsonFile(this,"json");
        mAdapter.setData(entity.allTagsList);
    }




}
