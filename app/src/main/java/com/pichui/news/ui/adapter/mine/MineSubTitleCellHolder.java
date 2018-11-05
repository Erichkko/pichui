package com.pichui.news.ui.adapter.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pichui.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MineSubTitleCellHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    public TextView tvTitle;

    @BindView(R.id.tv_subtitle)
    public TextView tvSubtitle;

    public MineSubTitleCellHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
