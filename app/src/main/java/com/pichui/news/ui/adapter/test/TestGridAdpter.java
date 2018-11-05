package com.pichui.news.ui.adapter.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.pichui.news.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TestGridAdpter extends RecyclerView.Adapter<TestGridAdpter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<TestGridModel> mData;
    private Context mContext;

    public TestGridAdpter(Context context, List<TestGridModel> data)
    {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_test_grid,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestGridModel model = mData.get(position);
        holder.tv_title.setText(model.getTitle());
        holder.iv_icon.setBackgroundResource(model.getIconId());
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
