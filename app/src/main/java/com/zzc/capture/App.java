package com.zzc.capture;

import android.app.Application;

import com.wethis.library.utils.PathUtil;

/**
 * 作者: Zzc on 2018-01-05.
 * 版本: v1.0
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PathUtil.getInstance().initDirs("var1","var2",this);
    }
}
