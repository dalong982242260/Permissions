package com.demo.permissions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CALL_PHONE= 123;
    private static final int RC_GET_DERVICE= 124;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.call_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoe();
            }
        });
        findViewById(R.id.get_device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceId();
            }
        });
    }

    @AfterPermissionGranted(RC_CALL_PHONE)
    public void callPhoe(){
        if (EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.CALL_PHONE)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 10010));
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                EasyPermissions.requestPermissions(this, "拨打电话",
                        RC_CALL_PHONE, Manifest.permission.CALL_PHONE);
                return;
            }
            startActivity(intent);

            Toast.makeText(this, "拨打电话-有权限", Toast.LENGTH_LONG).show();

        } else {
            EasyPermissions.requestPermissions(this, "拨打电话",
                    RC_CALL_PHONE, Manifest.permission.CALL_PHONE);
            Toast.makeText(this, "拨打电话-没有权限", Toast.LENGTH_LONG).show();
        }

    }

    @AfterPermissionGranted(RC_GET_DERVICE)
    public  void  getDeviceId() {
        if (EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {

            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            Toast.makeText(this, "获取设备号-有权限"+device_id, Toast.LENGTH_LONG).show();

        } else {
            EasyPermissions.requestPermissions(this, "获取设备号",
                    RC_GET_DERVICE, Manifest.permission.READ_PHONE_STATE);
            Toast.makeText(this, "获取设备号-没有权限", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d("888888", "onPermissionsGranted:" + requestCode + ":" + perms.size());

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("888888", "onPermissionsDenied:" + requestCode + ":" + perms.size());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
