package com.pi.core.uikit.bottomdialog.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wanglong on 2018/4/11.
 */

public class GridViewPagerView extends ViewPager {

    private float preX = 0;

    public GridViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public GridViewPagerView(Context context) {
        super(context);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent even) {

        if(even.getAction()== MotionEvent.ACTION_DOWN)
        {
            preX=(int) even.getX();
        }else
        {
            if(Math.abs((int)even.getX()-preX)>10)
            {
                return true;
            }else
            {
                preX=(int) even.getX();
            }
        }
        return super.onInterceptTouchEvent(even);
    }
}