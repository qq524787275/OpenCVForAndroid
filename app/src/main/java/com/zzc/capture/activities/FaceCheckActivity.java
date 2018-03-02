package com.zzc.capture.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzc.capture.R;
import com.zzc.capture.util.FaceUtil;
import com.zzc.capture.widget.CameraFaceDetectionView;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaceCheckActivity extends AppCompatActivity implements CameraFaceDetectionView.OnFaceDetectorListener {
    private static final String TAG = "FaceCheckActivity";
    private static final String FACE1 = "face1";
    private static final String FACE2 = "face2";
    private Bitmap mBitmapFace1;
    private Bitmap mBitmapFace2;

    @BindView(R.id.face1)
    ImageView face1;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.face2)
    ImageView face2;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.cameraFaceDetectionView)
    CameraFaceDetectionView cameraFaceDetectionView;
    @BindView(R.id.switch_camera)
    Button switchCamera;
    @BindView(R.id.bn_get_face)
    Button bnGetFace;
    private double cmp;

    private static boolean isGettingFace = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_check);
        ButterKnife.bind(this);

        cameraFaceDetectionView.enableView();
        cameraFaceDetectionView.setOnFaceDetectorListener(this);

        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraFaceDetectionView.switchCamera();
            }
        });

        bnGetFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGettingFace=true;
            }
        });
    }


    @Override
    public void onFace(Mat mat, Rect rect) {
        if(isGettingFace){
            Log.i(TAG, "onFace: -------抓取------------");
            if (null == mBitmapFace1 || null != mBitmapFace2) {
                mBitmapFace1 = null;
                mBitmapFace2 = null;

                // 保存人脸信息并显示
                FaceUtil.saveImage(this, mat, rect, FACE1);
                mBitmapFace1 = FaceUtil.getImage(this, FACE1);
                cmp = 0.0d;
            } else {
                FaceUtil.saveImage(this, mat, rect, FACE2);
                mBitmapFace2 = FaceUtil.getImage(this, FACE2);

                // 计算相似度
                cmp = FaceUtil.compare(this, FACE1, FACE2);
                Log.i(TAG, "onFace: 相似度 : " + cmp);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null == mBitmapFace1) {
                        face1.setImageResource(R.mipmap.ic_launcher_round);
                    } else {
                        face1.setImageBitmap(mBitmapFace1);
                    }
                    if (null == mBitmapFace2) {
                        face2.setImageResource(R.mipmap.ic_launcher_round);
                    } else {
                        face2.setImageBitmap(mBitmapFace2);
                    }
                    textView.setText(String.format("相似度 :  %.2f", cmp) + "%");
                }
            });

            isGettingFace=false;
        }
    }
}
