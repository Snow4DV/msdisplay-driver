package com.ms.ms2160.crashmanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.ms.ms2160.myapplication.ShotApplication;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class UncaughtExceptionHandlerImpl implements Thread.UncaughtExceptionHandler {
    private static UncaughtExceptionHandlerImpl INSTANCE = null;
    public static final String TAG = "CrashHandler";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private boolean mIsDebug;
    private boolean mIsRestartApp;
    private Class mRestartActivity;
    private long mRestartTime;
    private String mTips;
    private Map<String, String> infos = new HashMap();
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private UncaughtExceptionHandlerImpl() {
    }

    public static UncaughtExceptionHandlerImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UncaughtExceptionHandlerImpl();
        }
        return INSTANCE;
    }

    public void init(Context context, boolean z, boolean z2, long j, Class cls) {
        this.mIsRestartApp = z2;
        this.mRestartTime = j;
        this.mRestartActivity = cls;
        init(context, z);
    }

    public void init(Context context, boolean z) {
        this.mTips = "很抱歉，程序出现异常，即将退出...";
        this.mIsDebug = z;
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        if (!handleException(th) && (uncaughtExceptionHandler = this.mDefaultHandler) != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
            return;
        }
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            Log.e(TAG, "error : ", e);
        }
        if (this.mIsRestartApp) {
            ((AlarmManager) this.mContext.getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC, System.currentTimeMillis() + this.mRestartTime, PendingIntent.getActivity(this.mContext.getApplicationContext(), 0, new Intent(this.mContext.getApplicationContext(), (Class<?>) this.mRestartActivity), PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE));
        }
        ((ShotApplication) this.mContext.getApplicationContext()).removeAllActivity();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.ms.ms2160.crashmanager.UncaughtExceptionHandlerImpl$1] */
    private boolean handleException(final Throwable th) {
        if (th == null) {
            return false;
        }
        new Thread() { // from class: com.ms.ms2160.crashmanager.UncaughtExceptionHandlerImpl.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                Toast.makeText(UncaughtExceptionHandlerImpl.this.mContext, UncaughtExceptionHandlerImpl.this.getTips(th), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getTips(Throwable th) {
        if (th instanceof SecurityException) {
            if (th.getMessage().contains("android.permission.CAMERA")) {
                this.mTips = "请授予应用相机权限，程序出现异常，即将退出.";
            } else if (th.getMessage().contains("android.permission.RECORD_AUDIO")) {
                this.mTips = "请授予应用麦克风权限，程序出现异常，即将退出。";
            } else if (th.getMessage().contains("android.permission.WRITE_EXTERNAL_STORAGE")) {
                this.mTips = "请授予应用存储权限，程序出现异常，即将退出。";
            } else if (th.getMessage().contains("android.permission.READ_PHONE_STATE")) {
                this.mTips = "请授予应用电话权限，程序出现异常，即将退出。";
            } else if (th.getMessage().contains("android.permission.ACCESS_COARSE_LOCATION") || th.getMessage().contains("android.permission.ACCESS_FINE_LOCATION")) {
                this.mTips = "请授予应用位置信息权，很抱歉，程序出现异常，即将退出。";
            } else {
                this.mTips = "很抱歉，程序出现异常，即将退出，请检查应用权限设置。";
            }
        }
        return this.mTips;
    }

    public void collectDeviceInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                String str = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String str2 = packageInfo.versionCode + "";
                this.infos.put("versionName", str);
                this.infos.put("versionCode", str2);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e2) {
                Log.e(TAG, "an error occured when collect crash info", e2);
            }
        }
    }

    private String saveCrashInfo2File(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : this.infos.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "\n");
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        stringBuffer.append(stringWriter.toString());
        try {
            long currentTimeMillis = System.currentTimeMillis();
            String str = "crash-" + this.formatter.format(new Date()) + "-" + currentTimeMillis + ".log";
            if (Environment.getExternalStorageState().equals("mounted")) {
                String str2 = "/sdcard/" + this.mContext.getPackageName() + "/crash/";
                File file = new File(str2);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(str2 + str);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.close();
            }
            return str;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
            return null;
        }
    }
}
