package com.pi.core.uikit.slidingLayout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.pi.uikit.R;


/**
 * Created by on 09/03/16
 */
public class SwipeLayout extends FrameLayout {

    private Drawable leftShadow;
    private boolean swipeEnabled;
    private boolean swipeAnyWhere;
    private boolean swipeFinished;

    public SwipeLayout(Context context) {
        super(context);
    }

    public void replaceLayer(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            leftShadow = activity.getResources().getDrawable(R.drawable.left_shadow, null);
        } else {
            leftShadow = activity.getResources().getDrawable(R.drawable.left_shadow);
        }

        touchSlop = (int) (touchSlopDP * activity.getResources().getDisplayMetrics().density);
        sideWidth = (int) (sideWidthInDP * activity.getResources().getDisplayMetrics().density);
        sideHeight = getActionBarSize(activity);
        mActivity = activity;
        screenWidth = getScreenWidth(mActivity);
        setClickable(true);
        final ViewGroup root = (ViewGroup) activity.getWindow().getDecorView();
        content = root.getChildAt(0);
        ViewGroup.LayoutParams params = content.getLayoutParams();
        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(-1, -1);
        root.removeView(content);
        this.addView(content, params2);
        root.addView(this, params);
    }

    protected int getActionBarSize(Activity activity) {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = activity.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        actionBarSize += activity.getResources().getDimensionPixelSize(R.dimen.padding_status_bar_height);
        return actionBarSize;
    }

    @Override
    protected boolean drawChild(@NonNull Canvas canvas, @NonNull View child, long drawingTime) {
        boolean result = super.drawChild(canvas, child, drawingTime);
        final int shadowWidth = leftShadow.getIntrinsicWidth();
        int left = (int) (getContentX()) - shadowWidth;
        leftShadow.setBounds(left, child.getTop(), left + shadowWidth, child.getBottom());
        leftShadow.draw(canvas);
        return result;
    }
    public  int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    boolean canSwipe = false;
    /**
     * 超过了touchslop仍然没有达到没有条件，则忽略以后的动作
     */
    boolean ignoreSwipe = false;
    View content;
    Activity mActivity;
    int sideWidthInDP = 16;
    int sideWidth = 72;
    int sideHeight = 500;
    int screenWidth = 1080;
    VelocityTracker tracker;

    float downX;
    float downY;
    float lastX;
    float currentX;
    float currentY;

    int touchSlopDP = 20;
    int touchSlop = 60;

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (swipeEnabled && !canSwipe && !ignoreSwipe) {
            if (swipeAnyWhere) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = ev.getX();
                        downY = ev.getY();
                        currentX = downX;
                        currentY = downY;
                        lastX = downX;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = ev.getX() - downX;
                        float dy = ev.getY() - downY;
                        if (dx * dx + dy * dy > touchSlop * touchSlop) {
                            if (dy == 0f || Math.abs(dx / dy) > 1) {
                                downX = ev.getX();
                                downY = ev.getY();
                                currentX = downX;
                                currentY = downY;
                                lastX = downX;
                                canSwipe = true;
                                tracker = VelocityTracker.obtain();
                                return true;
                            } else {
                                ignoreSwipe = true;
                            }
                        }
                        break;
                }
            } else if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getX() < sideWidth && ev.getY() > sideHeight) {
                canSwipe = true;
                tracker = VelocityTracker.obtain();
                return true;
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            ignoreSwipe = false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSwipe || super.onInterceptTouchEvent(ev);
    }

    boolean hasIgnoreFirstMove;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (canSwipe) {
            tracker.addMovement(event);
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    currentX = downX;
                    currentY = downY;
                    lastX = downX;
                    break;
                case MotionEvent.ACTION_MOVE:
                    currentX = event.getX();
                    currentY = event.getY();
                    float dx = currentX - lastX;
                    if (dx != 0f && !hasIgnoreFirstMove) {
                        hasIgnoreFirstMove = true;
                        dx = dx / dx;
                    }
                    if (getContentX() + dx < 0) {
                        setContentX(0);
                    } else {
                        setContentX(getContentX() + dx);
                    }
                    lastX = currentX;
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    tracker.computeCurrentVelocity(10000);
                    tracker.computeCurrentVelocity(1000, 20000);
                    canSwipe = false;
                    hasIgnoreFirstMove = false;
                    int mv = screenWidth * 3;
                    if (Math.abs(tracker.getXVelocity()) > mv) {
                        animateFromVelocity(tracker.getXVelocity());
                    } else {
                        if (getContentX() > screenWidth / 3) {
                            animateFinish(false);
                        } else {
                            animateBack(false);
                        }
                    }
                    tracker.recycle();
                    break;
                default:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    ObjectAnimator animator;

    public void cancelPotentialAnimation() {
        if (animator != null) {
            animator.removeAllListeners();
            animator.cancel();
        }
    }

    public void setContentX(float x) {
        int ix = (int) x;
        content.setX(ix);
        invalidate();
    }

    public float getContentX() {
        return content.getX();
    }


    /**
     * 弹回，不关闭，因为left是0，所以setX和setTranslationX效果是一样的
     *
     * @param withVel 使用计算出来的时间
     */
    private void animateBack(boolean withVel) {
        cancelPotentialAnimation();
        animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), 0);
        int tmpDuration = withVel ? ((int) (duration * getContentX() / screenWidth)) : duration;
        if (tmpDuration < 100) {
            tmpDuration = 100;
        }
        animator.setDuration(tmpDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void animateFinish(boolean withVel) {
        cancelPotentialAnimation();
        animator = ObjectAnimator.ofFloat(this, "contentX", getContentX(), screenWidth);
        int tmpDuration = withVel ? ((int) (duration * (screenWidth - getContentX()) / screenWidth)) : duration;
        if (tmpDuration < 100) {
            tmpDuration = 100;
        }
        animator.setDuration(tmpDuration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!mActivity.isFinishing()) {
                    swipeFinished = true;
                    mActivity.finish();
//                    mActivity.onBackPressed();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
        animator.start();
    }

    private final int duration = 200;

    private void animateFromVelocity(float v) {
        if (v > 0) {
            if (getContentX() < screenWidth / 3 && v * duration / 1000 + getContentX() < screenWidth / 3) {
                animateBack(false);
            } else {
                animateFinish(true);
            }
        } else {
            if (getContentX() > screenWidth / 3 && v * duration / 1000 + getContentX() > screenWidth / 3) {
                animateFinish(false);
            } else {
                animateBack(true);
            }
        }
    }

    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
    }

    public void setSwipeAnyWhere(boolean swipeAnyWhere) {
        this.swipeAnyWhere = swipeAnyWhere;
    }

    public boolean isSwipeFinished() {
        return swipeFinished;
    }
}
