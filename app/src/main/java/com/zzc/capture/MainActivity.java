package com.zzc.capture;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.wethis.library.base.BaseActivity;
import com.wethis.library.base.BaseFragment;
import com.wethis.library.runtimepermissions.PermissionsManager;
import com.wethis.library.runtimepermissions.PermissionsResultAction;
import com.zzc.capture.fragments.MainFragment;

public class MainActivity extends BaseActivity {

    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("opencv_java");
    }

    @Override
    public BaseFragment setRootFragment() {
        requestPermissions();
        return MainFragment.newInstance();
    }


    public native String stringFromJNI();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
