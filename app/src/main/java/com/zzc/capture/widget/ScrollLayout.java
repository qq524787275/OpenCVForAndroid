package com.zzc.capture.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 作者: Zzc on 2018-01-09.
 * 版本: v1.0
 */

public class ScrollLayout extends FrameLayout {
    private static final String TAG = "ScrollLayout";
    private VelocityTracker mTracker;
    private Scroller mScroller;
    private int pointerId;

    public ScrollLayout(@NonNull Context context) {
        this(context, null);
    }

    public ScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.i(TAG, "computeScroll: ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                //设置单位 units用于设置速度单位,units为1表示每毫秒多少像素，units为1000表示每秒多少像素
                mTracker.computeCurrentVelocity(1000);
                Log.i(TAG, "onTouchEvent: move  x的速率:"+mTracker.getXVelocity(pointerId));
                Log.i(TAG, "onTouchEvent: move  y的速率:"+mTracker.getYVelocity(pointerId));
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }
}
