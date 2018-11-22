package com.pi.core.uikit.bottomdialog;

/**
 * Created by shaohui on 16/10/11.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pi.core.uikit.bottomdialog.listener.AnimationCallback;
import com.pi.core.uikit.bottomdialog.listener.OutsideClickListener;
import com.pi.uikit.R;

/**
 * Created by shaohui on 16/10/11.
 */

public abstract class BaseBottomDialog extends DialogFragment  implements AnimationCallback {

    private static final String TAG = "base_bottom_dialog";

    protected boolean isOutside = false; // 点击的区域是否在mView区域以外
    protected boolean isCancelableOutside = true; // 是否支持点击外部消失

    protected int[] padding = {0, 0, 0, 0}; // Dialog的内边距
    private static final float DEFAULT_DIM = 0.2f;
    protected long duration = 500; // 进出动画时长
    protected View decorView; // 对话框所有区域，包括灰色阴影
    protected View mView ;
    protected Context mContext;
    protected int dialogHeight, dialogWidth;

    protected OutsideClickListener mOutsideClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(getCancelOutside());

        mView = inflater.inflate(getLayoutRes(), container, false);
        bindView(mView);
        return mView;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void bindView(View v);

    @Override
    public void onStart() {
        super.onStart();
        if (mContext == null) throw new NullPointerException("must invoke init() method first");
        Window window = getDialog().getWindow();
        if (window == null) return;
        decorView = window.getDecorView();

        decorView.setPadding(padding[0], padding[1], padding[2], padding[3]);
        WindowManager.LayoutParams params = window.getAttributes();

        params.dimAmount = getDimAmount();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (dialogHeight > 0) {
            params.height = dialogHeight;
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        params.gravity = Gravity.BOTTOM;

        window.setAttributes(params);
        calculateOutside();
    }
    /**
     * 计算Dialog以外区域的点击事件，点击外部触发Dialog消失动画
     */
    private void calculateOutside() {
        getDialog().setCanceledOnTouchOutside(false); // 屏蔽系统带的点击外部区域隐藏对话框，自己定义
        decorView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // 计算点击的区域是否在Dialog外部
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    float touchX = motionEvent.getRawX(); // 手指点击的地方（相对于整个屏幕）
                    float touchY = motionEvent.getRawY();
                    // 计算可视Dialog区域的边界
                    int[] positions = new int[2];
                    mView.getLocationOnScreen(positions);
                    // Dialog区域的View相对于整个屏幕的位置
                    int left = positions[0];
                    int top = positions[1];
                    int right = left + mView.getWidth();
                    int bottom = top + mView.getHeight();

                    isOutside = touchX < left || touchX > right || touchY < top || touchY > bottom;
                    // 设置了mOutsideClickListener则点击外部不消失
//                    if (isOutside && isCancelableOutside && mOutsideClickListener == null) {
                    if (isOutside && isCancelableOutside ) {
                        AnimManager.getInstance().duration(duration).dismissAnimation(mView, BaseBottomDialog.this);
                    }
                    if (mOutsideClickListener != null)
                        mOutsideClickListener.outsideClick(isOutside, BaseBottomDialog.this);
                }
                return false;
            }
        });
    }

    public int getHeight() {
        return -1;
    }

    public float getDimAmount() {
        return DEFAULT_DIM;
    }

    public boolean getCancelOutside() {
        return true;
    }

    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onAnimationEnd() {
        dismiss();
    }

    public void cancel() {
        AnimManager.getInstance().dismissAnimation(mView, this);
    }
    public void show(FragmentManager fragmentManager) {
        if (mContext == null) throw new NullPointerException("must invoke create() method first");
        show(fragmentManager, getFragmentTag());
    }
}
