package com.pichui.news.ui.adapter.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pi.core.uikit.recycleview.galleryrecyclerview.Item;
import com.pichui.news.R;
import com.pichui.news.model.entity.CommentData;
import com.pichui.news.uitil.GlideUtils;
import com.pichui.news.uitil.TimeUtils;

import java.util.List;

/**
 * @author ChayChan
 * @description: 新闻详情页评论的适配器
 * @date 2017/6/28  16:01
 */

public class GalleryAdapter extends BaseQuickAdapter<Item, BaseViewHolder> {

    private Context mContext;

    public GalleryAdapter(Context context, @Nullable List<Item> data) {
        super(R.layout.item_gallery, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Item item) {
        helper.setText(R.id.name, item.getName());
        helper.setImageResource(R.id.profile_image,item.getImg());
    }
}
