package com.pichui.news.ui.adapter.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pichui.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MineCellHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.v_line_1)
    public View topLine;
    @BindView(R.id.v_line_2)
    public View bottomLine;

    public MineCellHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
