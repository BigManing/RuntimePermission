package com.org.bigmaning.runtimepermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * 　申请相机权限
 */
public class MainActivity extends AppCompatActivity {
    String camera = Manifest.permission.CAMERA;
    private static final String TAG = "----------";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 点击文字触发的事件
     *
     * @param view
     */
    public void myClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i(TAG, "api>=23，需要动态申请权限");

            Log.i(TAG, "shouldShowRequestPermissionRationale: " + shouldShowRequestPermissionRationale(camera));

            if (checkSelfPermission(camera) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "相机功能没有被授权，开始申请权限");

                if (shouldShowRequestPermissionRationale(camera)) {
                    Log.i(TAG, "在请求权限之前可以解释下为什么需要这个权限");
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("权限说明")
                            .setMessage("这个APP需要相机权限，一会一定要赐予我哦")
                            .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestCameraPermission();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .setCancelable(false)
                            .show();
                } else {
                    requestCameraPermission();
                }

            } else {
                Log.i(TAG, "相机功能已被授权，可以正常使用");
                startCapture();
            }
        } else {
            startCapture();
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{camera}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100) {
            if (grantResults.length > 0) {
                boolean isOk = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                Log.i(TAG, isOk ? "授权了" : "拒绝了");
                if (isOk) {
                    startCapture();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("权限申请")
                            .setMessage("请打开相机权限，以保证功能的正常使用")
                            .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);

                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .setCancelable(false)
                            .show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 拍照
     */
    private void startCapture() {
        Intent intent = new Intent(); //调用照相机
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
}
