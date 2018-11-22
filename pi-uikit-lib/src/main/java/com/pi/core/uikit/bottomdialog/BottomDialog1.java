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

import com.pi.core.uikit.bottomdialog.adapter.SmartAdapter;
import com.pi.core.uikit.bottomdialog.listener.OnItemClickListener;
import com.pi.core.uikit.bottomdialog.listener.OutsideClickListener;
import com.pi.core.uikit.bottomdialog.model.Item;
import com.pi.uikit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaohui on 16/10/11.
 */

public class BottomDialog1 extends BaseBottomDialog {

    private static final String KEY_LAYOUT_RES = "bottom_layout_res";
    private static final String KEY_HEIGHT = "bottom_height";
    private static final String KEY_DIM = "bottom_dim";
    private static final String KEY_CANCEL_OUTSIDE = "bottom_cancel_outside";

    public static final int ORIENTATION_LINEAR = 0x100;
    public static final int ORIENTATION_GRID = 0x101;
    protected int orientation = ORIENTATION_LINEAR; // 默认线性
    protected int spanCount = 3; // RecyclerView每一行3列

    @LayoutRes
    protected int mLayoutRes = R.layout.pi_grid_dialog_layout;
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
    protected int itemOrientation = LinearLayout.VERTICAL;

    @DrawableRes
    protected int backgroundRes = R.drawable.shape_round_corner; // 设置dialog的背景布局文件
    protected boolean backgroundResEnable = true; // 是否可设置背景资源

    protected List<Item> list = new ArrayList<>();

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemClickListener mOnItemLongClickListener;

    private SmartAdapter adapter;


    public BottomDialog1 init(Context context) {
        mContext = context;
        setFragmentManager(((FragmentActivity)context).getSupportFragmentManager());
        return this;
    }


//    public static BottomDialog1 create(FragmentManager manager) {
//        BottomDialog1 dialog = new BottomDialog1();
//        dialog.setFragmentManager(manager);
//
//        return dialog;
//    }
//    public static BottomDialog1 create(Context context) {
//        BottomDialog1 dialog = new BottomDialog1();
////        dialog.setFragmentManager(manager);
//
//        return dialog;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPadding(30);
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
        if (getLayoutRes() == R.layout.pi_grid_dialog_layout) {
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
                        AnimManager.getInstance().dismissAnimation(mView, BottomDialog1.this);
                    }
                });
            } else {
                tvCancel.setVisibility(View.GONE);
            }
            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
            LinearLayoutManager manager = null;
            if (orientation == ORIENTATION_LINEAR) {
                manager = new LinearLayoutManager(mContext);
            } else if (orientation == ORIENTATION_GRID){
                manager = new GridLayoutManager(mContext, spanCount);
            }
            recyclerView.setLayoutManager(manager);
            if (adapter == null) {
                adapter = new SmartAdapter(mContext, list);
            }
            recyclerView.setAdapter(adapter);
            if (orientation == ORIENTATION_GRID)
                recyclerView.addItemDecoration(new VerticalDivider(40));
            if (adapter instanceof SmartAdapter) {
                ((SmartAdapter) adapter).setOrientation(itemOrientation);
            }

            if (backgroundResEnable) {
                mView.findViewById(R.id.container).setBackgroundResource(backgroundRes);
                tvCancel.setBackgroundResource(backgroundRes);
                recyclerView.setBackgroundResource(backgroundRes);
            }

            if (mOnItemClickListener != null && adapter instanceof SmartAdapter) {
                ((SmartAdapter) adapter).setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, Item item) {
                        AnimManager.getInstance().dismissAnimation(mView, BottomDialog1.this);
                        mOnItemClickListener.onItemClick(position, item);
                    }
                    @Override
                    public void onItemLongClick(int position, Item item) {
                        AnimManager.getInstance().dismissAnimation(mView, BottomDialog1.this);
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

    public BottomDialog1 setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }


    public BottomDialog1 setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public BottomDialog1 setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public BottomDialog1 setTag(String tag) {
        mTag = tag;
        return this;
    }

    public BottomDialog1 setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public BottomDialog1 setHeight(int heightPx) {
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
    public BottomDialog1 setPadding(int left, int top, int right, int bottom) {
        padding[0] = left;
        padding[1] = top;
        padding[2] = right;
        padding[3] = bottom;
        return this;
    }

    public BottomDialog1 setPadding(int padding1) {
        padding[0] = padding1;
        padding[1] = padding1;
        padding[2] = padding1;
        padding[3] = padding1;
        return this;
    }
    public int getPaddingLeft(){
        return padding[1];
    }
    public BottomDialog1 items(List<Item> items) {
        list = items;
        return this;
    }
    public BottomDialog1 title(String title) {
        this.title = title;
        return this;
    }
    public BottomDialog1 titleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
        return this;
    }
    public BottomDialog1 titleColor(@ColorRes int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public BottomDialog1 titleSize(@IntRange(from = 1) int titleSize) {
        this.titleSize = titleSize;
        return this;
    }
    public BottomDialog1 cancelVisible(boolean cancelVisible) {
        this.cancelVisible = cancelVisible;
        return this;

    }
    public BottomDialog1 setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
        return this;
    }
    public BottomDialog1 cancelableOutside(boolean isCancelableOutside) {
        this.isCancelableOutside = isCancelableOutside;
        return this;
    }
    public BottomDialog1 onOutsideClick(OutsideClickListener outsideClickListener) {
        mOutsideClickListener = outsideClickListener;
        return this;
    }

    public BottomDialog1 dialogHeight(@IntRange(from = 0) int dialogHeight) {
        this.dialogHeight = dialogHeight;
        return this;
    }

    public BottomDialog1 itemOrientation(int itemOrientation) {
        if (itemOrientation != LinearLayout.HORIZONTAL && itemOrientation != LinearLayout.VERTICAL)
            throw new IllegalArgumentException("itemOrientation must is one of in （LinearLayout.HORIZONTAL , " +
                    "LinearLayout.VERTICAL）");
        this.itemOrientation = itemOrientation;
        return this;
    }

    // 设置RecyclerView展示方向
    public BottomDialog1 recyclerViewOrientation(int orientation) {
        if (orientation != BottomDialog1.ORIENTATION_LINEAR && orientation != BottomDialog1.ORIENTATION_GRID)
            throw new IllegalArgumentException("orientation must is one of in （BaseSmartDialog.ORIENTATION_VERTICAL , " +
                    "BaseSmartDialog.ORIENTATION_GRID）");
        this.orientation = orientation;
        return this;
    }

    // 设置RecyclerView为Grid时的列数
    public BottomDialog1 setSpanCount(@IntRange(from = 1) int spanCount) {
        this.spanCount = spanCount;
        return this;
    }
}
