package com.pichui.news.ui.adapter.news;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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

public class CommentAdapter extends BaseQuickAdapter<CommentData, BaseViewHolder> {

    private Context mContext;

    public CommentAdapter(Context context, @Nullable List<CommentData> data) {
        super(R.layout.item_comment, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentData commentData) {
        GlideUtils.loadRound(mContext, commentData.comment.user_profile_image_url,(ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_name, commentData.comment.user_name)
                .setText(R.id.tv_like_count, String.valueOf(commentData.comment.digg_count))
                .setText(R.id.tv_content, commentData.comment.text)
                .setText(R.id.tv_time, TimeUtils.getShortTime(commentData.comment.create_time * 1000));
    }
}
