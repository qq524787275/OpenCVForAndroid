package com.zzc.capture.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

public class BlurFragment extends BaseFragment {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    private Bitmap mBitmap;
    private Bitmap mDstBitmap;
    private static final String TAG = "BlurFragment";
    public BlurFragment() {
    }

    public BlurFragment(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_blur;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        image.setImageBitmap(mBitmap);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //原图
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //释放目标bitmap对象
                if(mDstBitmap!=null)
                    mDstBitmap.recycle();

                //设置原bitmap对象
                image.setImageBitmap(mBitmap);
            }
        });

        //腐蚀效果
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDstBitmap!=null)
                    mDstBitmap.recycle();
                long start=System.currentTimeMillis();
                mDstBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,false);
                Mat src = new Mat();
                Utils.bitmapToMat(mDstBitmap, src);
                Mat dst=new Mat();
                //获取Mat核
                //MORPH_RECT矩形、MORPH_CROSS十字形、MORPH_ELLIPSE椭圆形
                Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(10, 10));
                Imgproc.erode(src,dst,element);

                Utils.matToBitmap(dst,mDstBitmap);
                image.setImageBitmap(mDstBitmap);
                long end=System.currentTimeMillis();
                Log.i(TAG, "onClick: time="+(end-start));
            }
        });

        //膨胀效果
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDstBitmap!=null)
                    mDstBitmap.recycle();
                long start=System.currentTimeMillis();
                mDstBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,false);
                Mat src = new Mat();
                Utils.bitmapToMat(mDstBitmap, src);
                Mat dst=new Mat();
                //获取Mat核
                //MORPH_RECT矩形、MORPH_CROSS十字形、MORPH_ELLIPSE椭圆形
                Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(10, 10));
                Imgproc.dilate(src,dst,element);

                Utils.matToBitmap(dst,mDstBitmap);
                image.setImageBitmap(mDstBitmap);
                long end=System.currentTimeMillis();
                Log.i(TAG, "onClick: time="+(end-start));
            }
        });

        //blur
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDstBitmap!=null)
                    mDstBitmap.recycle();
                long start=System.currentTimeMillis();
                mDstBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,false);
                Mat src = new Mat();
                Utils.bitmapToMat(mDstBitmap, src);
                Mat dst=new Mat();
                Imgproc.blur(src,dst,new Size(10,10));

                Utils.matToBitmap(dst,mDstBitmap);
                image.setImageBitmap(mDstBitmap);
                long end=System.currentTimeMillis();
                Log.i(TAG, "onClick: time="+(end-start));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDstBitmap!=null)
                    mDstBitmap.recycle();
                long start=System.currentTimeMillis();
                mDstBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,false);
                Mat src = new Mat();
                Utils.bitmapToMat(mDstBitmap, src);
                Mat dst=new Mat();
                //ksize的高宽为奇数
                Imgproc.GaussianBlur(src,dst,new Size(11,11),0,0);

                Utils.matToBitmap(dst,mDstBitmap);
                image.setImageBitmap(mDstBitmap);
                long end=System.currentTimeMillis();
                Log.i(TAG, "onClick: time="+(end-start));
            }
        });
    }
}
