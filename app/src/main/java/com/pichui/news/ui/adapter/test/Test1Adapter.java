package com.pichui.news.ui.adapter.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.pichui.news.R;
import com.pichui.news.ui.adapter.base.BaseCellHolder;
import com.pichui.news.ui.adapter.base.BaseHeaderHolder;
import com.pichui.news.ui.adapter.base.BaseSectionedRecyclerViewAdapter;
import com.pichui.news.uitil.ListUtils;

import java.util.ArrayList;


public class Test1Adapter extends BaseSectionedRecyclerViewAdapter<BaseHeaderHolder, BaseCellHolder, RecyclerView.ViewHolder> {


    public ArrayList<TestEntity.TagsEntity> allTagList;
    private Context mContext;
    private LayoutInflater mInflater;

    private SparseBooleanArray mBooleanMap;

    public Test1Adapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(ArrayList<TestEntity.TagsEntity> allTagList) {
        this.allTagList = allTagList;
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return ListUtils.isEmpty(allTagList) ? 0 : allTagList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int count = allTagList.get(section).tagInfoList.size();
        if (count >= 0 && !mBooleanMap.get(section)) {
            count = 0;
        }

        return ListUtils.isEmpty(allTagList.get(section).tagInfoList) ? 0 : count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected BaseHeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new BaseHeaderHolder(mInflater.inflate(R.layout.base_title_item, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected BaseCellHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new BaseCellHolder(mInflater.inflate(R.layout.base_cell_item, parent, false));
    }


    @Override
    protected void onBindSectionHeaderViewHolder(final BaseHeaderHolder holder, final int section) {
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = mBooleanMap.get(section);
                String text = isOpen ? "展开" : "关闭";
                mBooleanMap.put(section, !isOpen);
                holder.openView.setText(text);
                notifyDataSetChanged();
            }
        });

        holder.titleView.setText(allTagList.get(section).tagsName);
        holder.openView.setText(mBooleanMap.get(section) ? "关闭" : "展开");

    }


    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(BaseCellHolder holder, int section, int position) {
        holder.descView.setText(allTagList.get(section).tagInfoList.get(position).tagName);

    }
}
