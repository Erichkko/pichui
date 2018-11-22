package com.pi.core.uikit.bottomdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.core.uikit.bottomdialog.adapter.SingleHorizontalAdapter;
import com.pi.core.uikit.bottomdialog.listener.OnItemClickListener;
import com.pi.core.uikit.bottomdialog.listener.OutsideClickListener;
import com.pi.core.uikit.bottomdialog.model.Item;
import com.pi.uikit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaohui on 16/10/11.
 */

public class SingleHorizontalBottomDialog extends BaseBottomDialog {

    private static final String KEY_LAYOUT_RES = "bottom_layout_res";
    private static final String KEY_HEIGHT = "bottom_height";
    private static final String KEY_DIM = "bottom_dim";
    private static final String KEY_CANCEL_OUTSIDE = "bottom_cancel_outside";


    @LayoutRes
    protected int mLayoutRes = R.layout.pi_single_horizontal_dialog_layout;
    private FragmentManager mFragmentManager;

    private boolean mIsCancelOutside = super.getCancelOutside();

    private String mTag = super.getFragmentTag();

    private float mDimAmount = super.getDimAmount();

    private int mHeight = super.getHeight();

    protected boolean titleVisible = true; // 是否显示标题
    protected boolean cancelVisible = true; // 是否显示取消按钮
    protected boolean animEnable = true; // 是否启用动画


    protected String title = "标题";
    protected int gravity = Gravity.BOTTOM;
    protected int titleGravity = Gravity.CENTER; // 标题的位置
    protected int titleColor = R.color.black; // 标题颜色
    protected int titleSize = 16; // 标题字体大小


    @DrawableRes
    protected int backgroundRes = R.drawable.shape_round_corner; // 设置dialog的背景布局文件
    protected boolean backgroundResEnable = true; // 是否可设置背景资源

    protected List<Item> list = new ArrayList<>();

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemClickListener mOnItemLongClickListener;

    private SingleHorizontalAdapter adapter;
    public static int spanCount = 4;


    public SingleHorizontalBottomDialog init(Context context) {
        mContext = context;
        setFragmentManager(((FragmentActivity)context).getSupportFragmentManager());
        return this;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLayoutRes = savedInstanceState.getInt(KEY_LAYOUT_RES);
            mHeight = savedInstanceState.getInt(KEY_HEIGHT);
            mDimAmount = savedInstanceState.getFloat(KEY_DIM);
            mIsCancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_RES, mLayoutRes);
        outState.putInt(KEY_HEIGHT, mHeight);
        outState.putFloat(KEY_DIM, mDimAmount);
        outState.putBoolean(KEY_CANCEL_OUTSIDE, mIsCancelOutside);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void bindView(View v) {
        initDefaultView();
    }
    private void initDefaultView() {
        // 默认提供的布局
        if (getLayoutRes() == R.layout.pi_single_horizontal_dialog_layout) {
            TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
            if (titleVisible) {
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
                tvTitle.setGravity(titleGravity);
                tvTitle.setTextColor(ContextCompat.getColor(mContext, titleColor));
                tvTitle.setTextSize(titleSize);
            } else {
                tvTitle.setVisibility(View.GONE);
            }

            TextView tvCancel = (TextView) mView.findViewById(R.id.tv_cancel);
            if (cancelVisible) {
                tvCancel.setVisibility(View.VISIBLE);
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        dismiss();
                        AnimManager.getInstance().dismissAnimation(mView, SingleHorizontalBottomDialog.this);
                    }
                });
            } else {
                tvCancel.setVisibility(View.GONE);
            }
            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            if (adapter == null) {
                adapter = new SingleHorizontalAdapter(mContext, list);
                adapter.setPaddingleft(padding[1]);
            }
            recyclerView.setAdapter(adapter);


            if (backgroundResEnable) {
                mView.findViewById(R.id.container).setBackgroundResource(backgroundRes);
                tvCancel.setBackgroundResource(backgroundRes);
                recyclerView.setBackgroundResource(backgroundRes);
            }

            if (mOnItemClickListener != null && adapter instanceof SingleHorizontalAdapter) {
                ((SingleHorizontalAdapter) adapter).setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, Item item) {
                        AnimManager.getInstance().dismissAnimation(mView, SingleHorizontalBottomDialog.this);
                        mOnItemClickListener.onItemClick(position, item);
                    }
                    @Override
                    public void onItemLongClick(int position, Item item) {
                        AnimManager.getInstance().dismissAnimation(mView, SingleHorizontalBottomDialog.this);
                        mOnItemLongClickListener.onItemLongClick(position, item);
                    }
                });
            }

        }
    }
    @Override
    public int getLayoutRes() {
        return mLayoutRes;
    }
    public SingleHorizontalBottomDialog setPadding(int left, int top, int right, int bottom) {
        padding[0] = left;
        padding[1] = top;
        padding[2] = right;
        padding[3] = bottom;
        return this;
    }

    public SingleHorizontalBottomDialog setPadding(int padding1) {
        padding[0] = padding1;
        padding[1] = padding1;
        padding[2] = padding1;
        padding[3] = padding1;
        return this;
    }
    public int getPaddingLeft(){
        return padding[1];
    }
    public SingleHorizontalBottomDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }


    public SingleHorizontalBottomDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public SingleHorizontalBottomDialog setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public SingleHorizontalBottomDialog setTag(String tag) {
        mTag = tag;
        return this;
    }

    public SingleHorizontalBottomDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public SingleHorizontalBottomDialog setHeight(int heightPx) {
        mHeight = heightPx;
        return this;
    }

    @Override
    public float getDimAmount() {
        return mDimAmount;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public boolean getCancelOutside() {
        return mIsCancelOutside;
    }

    @Override
    public String getFragmentTag() {
        return mTag;
    }



    public BaseBottomDialog show() {
        show(mFragmentManager);
        return this;
    }

    public SingleHorizontalBottomDialog items(List<Item> items) {
        list = items;
        return this;
    }
    public SingleHorizontalBottomDialog title(String title) {
        this.title = title;
        return this;
    }
    public SingleHorizontalBottomDialog titleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
        return this;
    }
    public SingleHorizontalBottomDialog titleColor(@ColorRes int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public SingleHorizontalBottomDialog titleSize(@IntRange(from = 1) int titleSize) {
        this.titleSize = titleSize;
        return this;
    }
    public SingleHorizontalBottomDialog cancelVisible(boolean cancelVisible) {
        this.cancelVisible = cancelVisible;
        return this;
    }
    public SingleHorizontalBottomDialog cancelableOutside(boolean isCancelableOutside) {
        this.isCancelableOutside = isCancelableOutside;
        return this;
    }
    public SingleHorizontalBottomDialog onOutsideClick(OutsideClickListener outsideClickListener) {
        mOutsideClickListener = outsideClickListener;
        return this;
    }

    public SingleHorizontalBottomDialog dialogHeight(@IntRange(from = 0) int dialogHeight) {
        this.dialogHeight = dialogHeight;
        return this;
    }

    // 设置RecyclerView为Grid时的列数
    public SingleHorizontalBottomDialog setSpanCount(@IntRange(from = 1) int spanCount) {
        this.spanCount = spanCount;
        return this;
    }
    public SingleHorizontalBottomDialog setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
        return this;
    }
}
