package com.zzc.capture.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wethis.library.base.SwipeBackFragment;
import com.zzc.capture.R;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-01-05.
 * 版本: v1.0
 */

public class CvtColorFragment extends SwipeBackFragment {
    private static final String TAG = "CvtColorFragment";
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    private Bitmap mBitmap;
    private Bitmap mDstBitmap;


    public CvtColorFragment() {
    }

    public CvtColorFragment(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_cvtcolor;
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

        //灰度图
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //释放目标bitmap对象
                if(mDstBitmap!=null)
                    mDstBitmap.recycle();

                Log.i(TAG, "onClick: "+mBitmap.hashCode());
                /*
                * 克隆bitmap
                * Bitmap bitmap = Bitmap.createBitmap(mBitmap);此方法得到可能是同一个对象
                * 因此使用mDstBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,false);
                */
                mDstBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,false);
                Log.i(TAG, "onClick: "+mDstBitmap.hashCode());
                //将克隆的bitmap转换Mat对象
                Mat src = new Mat();
                Utils.bitmapToMat(mDstBitmap, src);

                Mat dst = new Mat();
                Imgproc.cvtColor(src,dst,Imgproc.COLOR_RGB2GRAY);

                //将Mat对象转换成Bitmap
                Utils.matToBitmap(dst,mDstBitmap);

                image.setImageBitmap(mDstBitmap);
            }
        });

        //hsv
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDstBitmap!=null)
                    mDstBitmap.recycle();

                mDstBitmap=mBitmap.copy(Bitmap.Config.ARGB_8888,false);
                Mat src = new Mat();
                Utils.bitmapToMat(mDstBitmap, src);
                Mat dst = new Mat();
                Imgproc.cvtColor(src,dst,Imgproc.COLOR_RGB2HSV);
                //将Mat对象转换成Bitmap
                Utils.matToBitmap(dst,mDstBitmap);

                image.setImageBitmap(mDstBitmap);
            }
        });
    }
}
