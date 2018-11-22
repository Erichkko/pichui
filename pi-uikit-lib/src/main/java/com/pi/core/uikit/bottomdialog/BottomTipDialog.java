package com.pi.core.uikit.bottomdialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.pi.core.uikit.bottomdialog.BaseBottomDialog;
import com.pi.uikit.R;

/**
 * Created by shaohui on 16/10/11.
 */

public class BottomTipDialog extends BaseBottomDialog {

    private static final String KEY_LAYOUT_RES = "bottom_layout_res";
    private static final String KEY_HEIGHT = "bottom_height";
    private static final String KEY_DIM = "bottom_dim";
    private static final String KEY_CANCEL_OUTSIDE = "bottom_cancel_outside";

    private FragmentManager mFragmentManager;

    private boolean mIsCancelOutside = super.getCancelOutside();

    private String mTag = super.getFragmentTag();

    private float mDimAmount = super.getDimAmount();

    private int mHeight = super.getHeight();

    @LayoutRes
    protected int mLayoutRes = R.layout.pi_tip_dialog_layout;

    private TextView tvTip;

    public BottomTipDialog init(Context context) {
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
             tvTip = (TextView) mView.findViewById(R.id.tv_tip);
   }

   private void setTip(String tip){
       tvTip.setText(tip);
   }
    @Override
    public int getLayoutRes() {
        return mLayoutRes;
    }

    public BottomTipDialog setFragmentManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }



    public BottomTipDialog setLayoutRes(@LayoutRes int layoutRes) {
        mLayoutRes = layoutRes;
        return this;
    }

    public BottomTipDialog setCancelOutside(boolean cancel) {
        mIsCancelOutside = cancel;
        return this;
    }

    public BottomTipDialog setTag(String tag) {
        mTag = tag;
        return this;
    }

    public BottomTipDialog setDimAmount(float dim) {
        mDimAmount = dim;
        return this;
    }

    public BottomTipDialog setHeight(int heightPx) {
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


    public BaseBottomDialog show(String tip) {
        show(mFragmentManager);
//        setTip(tip);
        return this;
    }


    public BaseBottomDialog show() {
        show(mFragmentManager);
        return this;
    }
}
