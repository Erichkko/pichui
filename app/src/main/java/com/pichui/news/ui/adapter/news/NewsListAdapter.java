package com.pichui.news.ui.adapter.news;

import android.content.Context;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pichui.news.R;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class NewsListAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    List<MultipleItem> data ;
    private Context mContext;
    public NewsListAdapter(Context context, List<MultipleItem> data) {
        super(data);
        this.data = data;
        this.mContext = context;
        addItemType(MultipleItem.TEXT, R.layout.item_text_view);
        addItemType(MultipleItem.IMG, R.layout.item_image_view);
        addItemType(MultipleItem.IMG_TEXT, R.layout.item_img_text_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                helper.setText(R.id.tv, item.getContent());
                break;
            case MultipleItem.IMG:
//                helper.setText(R.id.tv, item.getContent());
                break;
            case MultipleItem.IMG_TEXT:
                helper.setText(R.id.tv, item.getContent());
                break;
        }
    }

}
