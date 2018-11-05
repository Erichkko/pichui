package com.pichui.news.ui.adapter.mine;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pichui.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MineSubTitleCellHolder extends MineBaseCellHolder {

    @BindView(R.id.tv_subtitle)
    public TextView tvSubtitle;

    public MineSubTitleCellHolder(View itemView) {
        super(itemView);

    }

}
