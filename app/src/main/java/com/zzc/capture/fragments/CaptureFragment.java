package com.zzc.capture.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.wethis.library.base.BaseFragment;
import com.zzc.capture.R;
import com.zzc.capture.widget.FishDrawable;

import butterknife.BindView;

/**
 * 作者: Zzc on 2018-01-09.
 * 版本: v1.0
 */

public class CaptureFragment extends BaseFragment {
    @BindView(R.id.img)
    ImageView img;
    private FishDrawable drawable;

    public static CaptureFragment newInstance() {

        Bundle args = new Bundle();

        CaptureFragment fragment = new CaptureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_capture;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        drawable = new FishDrawable(_mActivity);
        img.setImageDrawable(drawable);
        drawable.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        drawable.stop();
        drawable.release();
    }
}
