package com.pichui.news.ui.adapter.news;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.pichui.news.R;
import com.pichui.news.model.entity.News;
import com.pichui.news.uitil.GlideUtils;
import com.pichui.news.uitil.ListUtils;
import com.pichui.news.uitil.TimeUtils;
import com.pichui.news.uitil.UIUtils;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class NewsListAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
    /**
     * 纯文字布局(文章、广告)
     */
    public static final int TEXT_NEWS = 100;
    /**
     * 居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
     */
    public static final int CENTER_SINGLE_PIC_NEWS = 200;
    /**
     * 右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    public static final int RIGHT_PIC_VIDEO_NEWS = 300;
    /**
     * 三张图片布局(文章、广告)
     */
    public static final int THREE_PICS_NEWS = 400;


    String channelCode;

    public NewsListAdapter(@Nullable List<News> data,String channelCode) {
        super(data);
        this.channelCode = channelCode;
        initMultiTypeDelegate();
    }


    @Override
    protected void convert(final BaseViewHolder helper, News news) {
//        switch (helper.getItemViewType()){
//            case TEXT_NEWS:
//                //设置标题、底部作者、评论数、发表时间
//                helper.setText(R.id.tv_title, news.title)
//                        .setText(R.id.tv_author, news.source)
//                        .setText(R.id.tv_comment_num, news.comment_count + UIUtils.getString(R.string.comment))
//                        .setText(R.id.tv_time, TimeUtils.getShortTime(news.behot_time * 1000));
//                break;
//                case CENTER_SINGLE_PIC_NEWS:
//                //设置标题、底部作者、评论数、发表时间
//                helper.setText(R.id.tv_title, news.title)
//                        .setText(R.id.tv_author, news.source)
//                        .setText(R.id.tv_comment_num, news.comment_count + UIUtils.getString(R.string.comment))
//                        .setText(R.id.tv_time, TimeUtils.getShortTime(news.behot_time * 1000));
//                    //中间大图布局，判断是否有视频
//                    TextView tvBottomRight = helper.getView(R.id.tv_bottom_right);
//                    if (news.has_video) {
//                        helper.setVisible(R.id.iv_play, true);//显示播放按钮
//                        tvBottomRight.setCompoundDrawables(null, null, null, null);//去除TextView左侧图标
//                        helper.setText(R.id.tv_bottom_right, TimeUtils.secToTime(news.video_duration));//设置时长
//                        GlideUtils.load(mContext, news.video_detail_info.detail_video_large_image.url, (ImageView)( helper.getView(R.id.iv_img)));//中间图片使用视频大图
//                    } else {
//                        helper.setVisible(R.id.iv_play, false);//隐藏播放按钮
//                        if (news.gallary_image_count == 1){
//                            tvBottomRight.setCompoundDrawables(null, null, null, null);//去除TextView左侧图标
//                        }else{
//                            tvBottomRight.setCompoundDrawables(mContext.getResources().getDrawable(R.mipmap.icon_picture_group), null, null, null);//TextView增加左侧图标
//                            helper.setText(R.id.tv_bottom_right, news.gallary_image_count + UIUtils.getString(R.string.img_unit));//设置图片数
//                        }
//                        GlideUtils.load(mContext, news.image_list.get(0).url.replace("list/300x196", "large"),(ImageView)( helper.getView(R.id.iv_img)));//中间图片使用image_list第一张
//                    }
//                break;
//        }
    }
    private void initMultiTypeDelegate(){
        //代理方式 实现多item布局
        setMultiTypeDelegate(new MultiTypeDelegate<News>() {
            @Override
            protected int getItemType(News news) {
                return itemType(news);
            }
        });
        //Step.2
        getMultiTypeDelegate()
                .registerItemType(TEXT_NEWS,R.layout.item_text_news);
        getMultiTypeDelegate()
                .registerItemType(CENTER_SINGLE_PIC_NEWS,R.layout.item_center_pic_news);
        getMultiTypeDelegate().registerItemType(RIGHT_PIC_VIDEO_NEWS,R.layout.item_pic_video_news);
        getMultiTypeDelegate().registerItemType(THREE_PICS_NEWS,R.layout.item_three_pics_news);
    }

    private int itemType(News news) {
//        return CENTER_SINGLE_PIC_NEWS;
        if (news.has_video) {
            //如果有视频
            if (news.video_style == 0) {
                //右侧视频
                if (news.middle_image == null || TextUtils.isEmpty(news.middle_image.url)) {
                    return TEXT_NEWS;
                }
                return RIGHT_PIC_VIDEO_NEWS;
            } else if (news.video_style == 2) {
                //居中视频
                return CENTER_SINGLE_PIC_NEWS;
            }
            return TEXT_NEWS;
        } else {
            //非视频新闻
            if (!news.has_image) {
                //纯文字新闻
                return TEXT_NEWS;
            } else {
                if (ListUtils.isEmpty(news.image_list)) {
                    //图片列表为空，则是右侧图片
                    return RIGHT_PIC_VIDEO_NEWS;
                }

                if (news.gallary_image_count == 3) {
                    //图片数为3，则为三图
                    return THREE_PICS_NEWS;
                }

                //中间大图，右下角显示图数
                return CENTER_SINGLE_PIC_NEWS;
            }
        }
    }
}
