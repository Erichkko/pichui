package com.pichui.news.ui.adapter.news;

import android.content.Context;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pichui.news.R;
import com.pichui.news.model.entity.News;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class NewsListAdapter extends BaseMultiItemQuickAdapter<News, BaseViewHolder> {
    List<News> data ;
    public NewsListAdapter( List<News> data) {
        super(data);
        this.data = data;
        addItemType(0, R.layout.item_text_view);
        addItemType(1, R.layout.item_image_view);
        addItemType(2, R.layout.item_img_text_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        switch (helper.getItemViewType()) {
            case 0:
//                helper.setText(R.id.tv, item.getContent());
                break;
            case 1:
//                helper.setText(R.id.tv, item.getContent());
                break;
            case 2:
//                helper.setText(R.id.tv, item.getContent());
                break;
        }
    }

}
