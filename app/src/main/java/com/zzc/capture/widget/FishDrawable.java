package com.zzc.capture.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zzc.capture.R;

/**
 * 作者: Zzc on 2018-01-16.
 * 版本: v1.0
 */

public class FishDrawable extends Drawable implements Animatable, ValueAnimator.AnimatorUpdateListener {
    private static final String TAG = "FishDrawable";
    private ValueAnimator mAnimator;
    private Paint mPaint;
    private Context mContext;
    private int curf;
    private Bitmap curBitmap;
    private Bitmap mBitmap;
    private int x;
    private boolean isRun=true;
    private int y;
    public FishDrawable(Context mContext) {
        this.mContext = mContext;
        mPaint=new Paint();
        mPaint.setColor(Color.WHITE);

        mAnimator=ValueAnimator.ofInt(0,9);
        mAnimator.setDuration(600);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.addUpdateListener(this);

        mBitmap=BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.coin);
        x=mBitmap.getWidth();
        y=mBitmap.getHeight()/10;
        Log.i(TAG, "FishDrawable: x"+x);
        Log.i(TAG, "FishDrawable: y"+y);
        curBitmap=Bitmap.createBitmap(mBitmap,0,x*curf,x,y);
    }



    @Override
    public void draw(@NonNull Canvas canvas) {
        if(isRun) {
            //和自定义View中的onDraw()异曲同工\
            Log.i(TAG, "draw: ------");
//            canvas.drawPaint(mPaint);
            curBitmap.recycle();
            curBitmap = Bitmap.createBitmap(mBitmap, 0, x * curf, x, y);
            canvas.drawBitmap(curBitmap, 0, 0, mPaint);
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        //设置Drawable的透明度，一般情况下将此alpha值设置给Paint
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        //设置颜色滤镜，一般情况下将此值设置给Paint
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        //决定绘制的部分是否遮住Drawable下边的东西，有点抽象，有几种模式
        //PixelFormat.UNKNOWN
        //PixelFormat.TRANSLUCENT 只有绘制的地方才盖住下边
        //PixelFormat.TRANSPARENT 透明，不显示绘制内容
        //PixelFormat.OPAQUE 完全盖住下边内容
        return PixelFormat.TRANSLUCENT;
    }


    @Override
    public int getIntrinsicWidth() {
        return x;

    }

    @Override
    public int getIntrinsicHeight() {
        return y;
    }

    @Override
    public void start() {
        isRun=true;
        mAnimator.start();
        Log.i(TAG, "start: ");
    }

    @Override
    public void stop() {
        isRun=false;
        mAnimator.end();
        Log.i(TAG, "stop: ");
    }

    @Override
    public boolean isRunning() {
        Log.i(TAG, "isRunning: ");
        return isRun;
    }

    public void release(){
        mBitmap.recycle();
        mBitmap=null;
        curBitmap.recycle();
        curBitmap=null;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        curf = (int) animation.getAnimatedValue();
        invalidateSelf();
    }
}
