package com.ms.ms2160.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ms.ms2160.R;
import com.ms.ms2160.Util.SystemUtil;
import com.ms.ms2160.crashmanager.CrashApplication;
import com.ms.ms2160.service.CaptureService;
import com.ms.ms2160.service.KeepAliveJobService;

/* loaded from: classes.dex */
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    private String TAG = "MainActivity";
    private boolean isPhone = true;
    private boolean isPermission = false;
    Handler mAsyncHandler = null;
    Intent intent = null;
    private final String ACTION_USB_REFRESH = "com.ms.ms2160.USB_REFRESH";
    private final String ACTION_USB_PERMISSION = "com.ms.ms2160.USB_PERMISSION";
    Runnable sendBroadcastRunnable = new Runnable() { // from class: com.ms.ms2160.myapplication.MainActivity.2
        @Override // java.lang.Runnable
        public void run() {
            MainActivity.this.sendBroadcast(new Intent("com.ms.ms2160.USB_REFRESH"));
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        Log.e(this.TAG, "onCreate");
        super.onCreate(bundle);
        CrashApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);
        if (!this.isPhone) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getApplicationContext().startService(new Intent(this, (Class<?>) CaptureService.class));
        if (Build.VERSION.SDK_INT >= 21) {
            KeepAliveJobService.startJob(this);
        }
        Log.i(this.TAG, "MainActivity onCreate");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        String action = getIntent().getAction();
        Log.e("MainActivity", "onResume: " + action);
        if (action == null || action.equals("android.intent.action.MAIN")) {
            getApplicationContext().startService(new Intent(this, (Class<?>) CaptureService.class));
            if (Build.VERSION.SDK_INT >= 21) {
                KeepAliveJobService.startJob(this);
                return;
            }
            return;
        }
        if (action.equals(USBMonitor.ACTION_USB_DEVICE_ATTACHED)) {
            Log.e(this.TAG, USBMonitor.ACTION_USB_DEVICE_ATTACHED);
            if (this.mAsyncHandler == null) {
                this.mAsyncHandler = HandlerThreadHandler.createHandler(this.TAG);
            }
            if (this.intent == null) {
                this.intent = new Intent(this, (Class<?>) CaptureService.class);
                getApplicationContext().startService(this.intent);
            }
            Handler handler = this.mAsyncHandler;
            if (handler != null) {
                handler.postDelayed(this.sendBroadcastRunnable, 1000L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_settings) {
            startActivity(new Intent(this, (Class<?>) SettingActivity.class));
        } else if (itemId == R.id.menu_detect_devices) {
            sendBroadcast(new Intent("com.ms.ms2160.USB_REFRESH"));
            Log.e("MainActivity", "sendBroadcast");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.RECORD_AUDIO")) {
                Snackbar.make(findViewById(R.id.layout), "Please Grant Permissions", BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("ENABLE", new View.OnClickListener() { // from class: com.ms.ms2160.myapplication.MainActivity.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        MainActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"}, MainActivity.PERMISSIONS_MULTIPLE_REQUEST);
                    }
                }).show();
            } else {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"}, PERMISSIONS_MULTIPLE_REQUEST);
            }
        }
    }

    private void startScreenCapture() {
        getApplicationContext().startService(new Intent(this, (Class<?>) CaptureService.class));
    }

    public static boolean checkPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(activity)) {
            return true;
        }
        activity.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + activity.getPackageName())), 0);
        return false;
    }

    private boolean isIgnoringBatteryOptimizations() {
        PowerManager powerManager = (PowerManager) getSystemService("power");
        if (powerManager != null) {
            return powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return false;
    }

    public void requestIgnoreBatteryOptimizations() {
        if (SystemUtil.getDeviceBrand().equals("Xiaomi")) {
            Intent intent = new Intent(getPackageName());
            intent.setComponent(new ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"));
            intent.putExtra("package_name", getPackageName());
            intent.putExtra("package_label", getResources().getString(R.string.app_name));
            if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                startActivity(intent);
                return;
            }
            return;
        }
        if (SystemUtil.getDeviceBrand().equals("HUAWEI")) {
            Intent intent2 = new Intent(getPackageName());
            intent2.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            intent2.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"));
            if (getPackageManager().queryIntentActivities(intent2, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                startActivity(intent2);
            }
        }
    }

    public static void openStart(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        String deviceBrand = SystemUtil.getDeviceBrand();
        Intent intent = new Intent();
        if (deviceBrand.equals("Huawei")) {
            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"));
        } else if (deviceBrand.equals("Xiaomi")) {
            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
        }
        try {
            context.startActivity(intent);
        } catch (Exception unused) {
            context.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }
}
