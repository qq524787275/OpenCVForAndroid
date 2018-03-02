package com.zzc.capture.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zzc.capture.R;
import com.zzc.capture.util.FaceInfoPreferences;
import com.zzc.capture.util.FaceRecoginzerUtil;
import com.zzc.capture.widget.CameraFaceDetectionView;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaceRecognizerActivity extends AppCompatActivity implements CameraFaceDetectionView.OnFaceDetectorListener {
    private static final String TAG = "FaceRecognizerActivity";

    public static final int SHOW_LOADING = 1;
    public static final int HIDE_LOADING = 2;
    public static final int SHOW_FACE_NAME = 3;

    @BindView(R.id.faceview)
    CameraFaceDetectionView faceview;
    @BindView(R.id.load)
    Button load;
    @BindView(R.id.recognize)
    Button recognize;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.change)
    Button change;
    @BindView(R.id.train)
    Button train;
    private FaceRecoginzerUtil util;
    private QMUITipDialog mLoadingDialog;
    private QMUIDialog qmuiDialog;
    private boolean isload = false;
    private boolean isrecognize = false;
    private int i = 0;
    private static String name;


    public static Mat[] mats = new Mat[10];
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_LOADING:
                    mLoadingDialog.show();
                    break;
                case HIDE_LOADING:
                    mLoadingDialog.dismiss();
                    break;
                case SHOW_FACE_NAME:
                    Toast.makeText(FaceRecognizerActivity.this,(String)msg.obj, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };
    private FaceInfoPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognizer);
        ButterKnife.bind(this);
        faceview.setOnFaceDetectorListener(this);
        initView();
        util = FaceRecoginzerUtil.getInstance(FaceRecognizerActivity.this);
        preferences = FaceInfoPreferences.getInstance(FaceRecognizerActivity.this);
        faceview.enableView();
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qmuiDialog.show();
                Toast.makeText(FaceRecognizerActivity.this, "录入", Toast.LENGTH_SHORT).show();
            }
        });

        recognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isrecognize = true;
                Toast.makeText(FaceRecognizerActivity.this, "识别", Toast.LENGTH_SHORT).show();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FaceRecognizerActivity.this, "清除", Toast.LENGTH_SHORT).show();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceview.switchCamera();
            }
        });

        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        showDialog();
                        util.createAtTxt();
                        util.train(util.getAtPath(), util.getXmlPath());
                        hideDialog();
                    }
                }).start();
            }
        });
    }

    private void initView() {
        mLoadingDialog = new QMUITipDialog
                .Builder(this)
                .setTipWord("正在加载")
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();

        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        qmuiDialog = builder
                .setTitle("姓名录入")
                .setPlaceholder("在此输入姓名")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        name = builder.getEditText().getText().toString().trim();
                        isload = true;
                        dialog.dismiss();
                    }
                })
                .create();
    }


    @Override
    public void onFace(final Mat mat, final Rect rect) {

        if (isrecognize) {
            showDialog();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    util.savePreImg(FaceRecognizerActivity.this, mat, rect);
                    int labe = FaceRecoginzerUtil.recoginzer(util.getXmlPath(), util.getPrePath());
                    Log.i(TAG, "onFace: ---------------找到了：" + labe);
                    isrecognize = false;
                    showFaceName(preferences.getName(labe+""));
                    hideDialog();
                }
            }).start();
            isrecognize = false;
        }


        if (isload) {
            Log.i(TAG, "onFace: i=" + i);
            mats[i] = mat;
            i++;
        }
        if (i == 10) {
            showDialog();
            isload = false;
            int faceCount = util.getFaceCount();
            Log.i(TAG, "onFace: ----------------1111:"+faceCount);
            boolean issuccess = util.save(mats, rect, "s" + faceCount);
            if (issuccess) {
                Log.i(TAG, "onFace: ----------------2222:"+faceCount);
                preferences.saveName(faceCount + "", name);
            }
            hideDialog();
            i = 0;
        }
    }


    public void showDialog() {
        Message message = Message.obtain();
        message.what = SHOW_LOADING;
        mHandler.sendMessage(message);
    }

    public void hideDialog() {
        Message message = Message.obtain();
        message.what = HIDE_LOADING;
        mHandler.sendMessage(message);
    }

    public void showFaceName(String name) {
        Message message = Message.obtain();
        message.what = SHOW_FACE_NAME;
        message.obj = name;
        mHandler.sendMessage(message);
    }
}
