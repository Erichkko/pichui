package com.pichui.news.ui.adapter.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pichui.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MineBaseCellHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    public TextView tvTitle;

    public MineBaseCellHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
