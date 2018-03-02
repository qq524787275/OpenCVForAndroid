package com.zzc.capture.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者: Zzc on 2018-03-02.
 * 版本: v1.0
 */

public class FaceInfoPreferences {
    private static volatile SharedPreferences mPreferences;
    private static FaceInfoPreferences sInstance;
    private FaceInfoPreferences() {
    }

    public FaceInfoPreferences(final Context context) {
        mPreferences = context.getSharedPreferences("faceinfo",Context.MODE_PRIVATE);
    }

    public static FaceInfoPreferences getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (FaceInfoPreferences.class) {
                if (sInstance == null) {
                    sInstance = new FaceInfoPreferences(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public void saveName(String labe,String name){
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString(labe,name);
        edit.commit();
    }

    public String getName(String labe){
        String name = mPreferences.getString(labe,null);
        return name;
    }
}
