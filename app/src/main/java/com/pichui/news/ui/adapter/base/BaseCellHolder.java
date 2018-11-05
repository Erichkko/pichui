package com.pichui.news.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pichui.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseCellHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_desc)
    public TextView descView;

    public BaseCellHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
