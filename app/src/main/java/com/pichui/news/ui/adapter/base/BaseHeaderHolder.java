package com.pichui.news.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pichui.news.R;


public class BaseHeaderHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public TextView openView;
    public BaseHeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        titleView = (TextView) itemView.findViewById(R.id.tv_title);
        openView = (TextView) itemView.findViewById(R.id.tv_open);
    }
}
