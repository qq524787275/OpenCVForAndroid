package com.zzc.capture.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wethis.library.base.BaseFragment;
import com.wethis.library.utils.BitmapUtils;
import com.wethis.library.utils.DensityUtils;
import com.wethis.library.utils.FileUtils;
import com.wethis.library.utils.PathUtil;
import com.wethis.library.utils.ViewUtils;
import com.zzc.capture.R;
import com.zzc.capture.activities.FaceCheckActivity;
import com.zzc.capture.activities.FaceRecognizerActivity;

import java.io.File;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-01-04.
 * 版本: v1.0
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.image)
    ImageView mImage;
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
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    private String mPath;
    private Bitmap mBitmap;
    private File mCameraFile;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ViewUtils.REQUEST_CODE_LOCAL) {
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        mPath = FileUtils.getUriPath(uri, _mActivity);
                    }
                }
            } else if (requestCode == ViewUtils.REQUEST_CODE_CAMERA) {
                if (mCameraFile != null && mCameraFile.exists()) {
                    mPath = mCameraFile.getPath();
                }
            }

            if (mPath != null) {
                if (mBitmap != null)
                    mBitmap.recycle();

                mBitmap = BitmapUtils.getSmallBitmap(mPath, DensityUtils.getScreenW(_mActivity), DensityUtils.getScreenW(_mActivity));
                mImage.setImageBitmap(mBitmap);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn1:
                ViewUtils.selectPicFromLocal(MainFragment.this);
                break;
            case R.id.btn2:
                mCameraFile = new File(PathUtil.getInstance().getImagePath(), +System.currentTimeMillis() + ".jpg");
                ViewUtils.selectPicFromCamera(MainFragment.this, mCameraFile);
                break;
            case R.id.btn3:
                startFragment(new CvtColorFragment(mBitmap));
                break;
            case R.id.btn4:
                startFragment(new BlurFragment(mBitmap));
                break;
            case R.id.btn5:
                startFragment(new CannyFragment(mBitmap));
                break;
            case R.id.btn6:
                startFragment(CaptureFragment.newInstance());
                break;
            case R.id.btn7:
                Intent intent = new Intent();
                intent.setClass(_mActivity, FaceCheckActivity.class);
                startActivity(intent);
                break;
            case R.id.btn8:
                Intent intent1 = new Intent();
                intent1.setClass(_mActivity, FaceRecognizerActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    private void startFragment(BaseFragment fragment) {
        if(mPath==null){
            Toast.makeText(_mActivity, "请先选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        start(fragment);
    }
}
