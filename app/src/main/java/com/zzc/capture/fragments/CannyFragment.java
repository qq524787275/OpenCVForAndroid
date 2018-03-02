package com.zzc.capture.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.wethis.library.base.BaseFragment;
import com.zzc.capture.R;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-01-05.
 * 版本: v1.0
 */

public class CannyFragment extends BaseFragment implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "CannyFragment";
    @BindView(R.id.surface)
    SurfaceView mSurfaceView;
    @BindView(R.id.seekbar1)
    SeekBar mSeekBar1;
    @BindView(R.id.seekbar2)
    SeekBar mSeekBar2;
    @BindView(R.id.seekbar3)
    SeekBar mSeekBar3;
    private int progress1 = 0;
    private int progress2 = 0;
    private Bitmap mBitmap;
    private Bitmap mDstBitmap;
    private SurfaceHolder mHolder;
    private boolean mFlag;
    private Canvas mCanvas;
    private Mat dst;
    private Mat src;

    public CannyFragment() {
    }

    public CannyFragment(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_canny;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSurfaceView.setZOrderOnTop(true);
        ViewGroup.LayoutParams params = mSurfaceView.getLayoutParams();
        params.height = mBitmap.getHeight();
        params.width = mBitmap.getWidth();
        mDstBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, false);
        src = new Mat();
        Utils.bitmapToMat(mDstBitmap, src);
        //将原图将转换为灰度图
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);

        //先用使用 3x3内核来降噪
        Imgproc.blur(src, src, new Size(3, 3));

        dst = src.clone();

        mSurfaceView.setLayoutParams(params);
        mHolder = mSurfaceView.getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mHolder.addCallback(this);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress1 = progress;
                Imgproc.Canny(src, dst, progress1, progress2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress2 = progress;
                Imgproc.Canny(src, dst, progress1, progress2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                transform(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 图像左右平移
     * CannyFragment
     * @param progress
     */
    private void transform(int progress) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mFlag = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mFlag = false;
    }

    @Override
    public void run() {
        drawing();
    }

    private void drawing() {
        while (mFlag) {
            try {
                mCanvas = mHolder.lockCanvas();
                if (mCanvas != null) {
                    Utils.matToBitmap(dst, mDstBitmap);
                    mCanvas.drawBitmap(mDstBitmap, 0, 0, null);
                }
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDstBitmap.recycle();
    }
}
