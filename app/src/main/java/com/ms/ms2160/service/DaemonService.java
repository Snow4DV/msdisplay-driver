package com.ms.ms2160.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.ms.ms2160.BuildConfig;
import com.ms.ms2160.IMyAidlInterface;
import com.ms.ms2160.R;
import com.ms.ms2160.myapplication.HandlerThreadHandler;
import com.ms.ms2160.myapplication.MainActivity;

/* loaded from: classes.dex */
public class DaemonService extends Service {
    private static final String ACTION_CAPTURE = "com.ms.ms2160.CaptureDelete";
    private static final String ACTION_DAEMON = "com.ms.ms2160.DaemonDelete";
    private static final int PRIORITY_MIN = -2;
    private ButtonBroadcastReceiver bReceiver;
    private Connection connection;
    private MyBinder myBinder;
    private String TAG = "DaemonService";
    private boolean keepAliveFlag = true;
    Runnable keepAliveThread = new Runnable() { // from class: com.ms.ms2160.service.DaemonService.1
        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                if (!DaemonService.this.isServiceStarted() && DaemonService.this.keepAliveFlag) {
                    Log.e(DaemonService.this.TAG, "DaemonService keepAliveThread restart");
                }
                try {
                    Thread.sleep(3000L);
                } catch (Exception unused) {
                }
            }
        }
    };

    /* loaded from: classes.dex */
    class MyBinder extends IMyAidlInterface.Stub {
        @Override // com.ms.ms2160.IMyAidlInterface
        public void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException {
        }

        MyBinder() {
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.myBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.e(this.TAG, "UnionBind onCreate: ");
        super.onCreate();
        this.myBinder = new MyBinder();
        startService();
        HandlerThreadHandler createHandler = HandlerThreadHandler.createHandler(this.TAG);
        if (createHandler != null) {
            createHandler.postDelayed(this.keepAliveThread, 3000L);
        }
        RegisterBroadCast();
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.e(this.TAG, "onDestroy");
        ButtonBroadcastReceiver buttonBroadcastReceiver = this.bReceiver;
        if (buttonBroadcastReceiver != null) {
            unregisterReceiver(buttonBroadcastReceiver);
        }
        super.onDestroy();
    }

    private void RegisterBroadCast() {
        this.bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CAPTURE);
        intentFilter.addAction(ACTION_DAEMON);
        registerReceiver(this.bReceiver, intentFilter);
    }

    public boolean isServiceStarted() {
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE)).getRunningTasks(100)) {
            if (runningTaskInfo.topActivity.getPackageName().equals(BuildConfig.APPLICATION_ID) && runningTaskInfo.baseActivity.getPackageName().equals(BuildConfig.APPLICATION_ID)) {
                Log.d(this.TAG, "isServiceStarted: Daemon find Capture");
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startService() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget);
        remoteViews.setTextViewText(R.id.wt_title, "MS");
        remoteViews.setOnClickPendingIntent(R.id.wt_delete, PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(ACTION_DAEMON), PendingIntent.FLAG_IMMUTABLE));
        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, (Class<?>) MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(new NotificationChannel("channel_1", "MS", NotificationManager.IMPORTANCE_LOW));
            Notification build = new Notification.Builder(this, "channel_1").setCategory(NotificationCompat.CATEGORY_MESSAGE).setSmallIcon(R.drawable.ic_launcher).setContentTitle("MS").setContentText("").setContentIntent(activity).setAutoCancel(true).build();
            build.contentView = remoteViews;
            startForeground(10, build);
            return;
        }
        startForeground(100, new Notification.Builder(this).setSmallIcon(R.drawable.ic_refresh_white_24dp).setWhen(System.currentTimeMillis()).setTicker("MS").setContentTitle("MS").setContentText("MS").setOngoing(true).setPriority(Notification.PRIORITY_MAX).setContentIntent(activity).setAutoCancel(false).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindService() {
        Log.e(this.TAG, "UnionBind DaemonService bind Capture");
        this.connection = new Connection();
        bindService(new Intent(this, (Class<?>) CaptureService.class), this.connection, BIND_AUTO_CREATE);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.e(this.TAG, "UnionBind onStartCommand: ");
        bindService();
        return super.onStartCommand(intent, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class Connection implements ServiceConnection {
        Connection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(DaemonService.this.TAG, "UnionBind DaemonService receive Capture connect msg");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(DaemonService.this.TAG, "UnionBind DaemonService receive Capture Disconnect msg");
            DaemonService.this.startService();
            if (DaemonService.this.keepAliveFlag) {
                DaemonService.this.startService(new Intent(DaemonService.this, (Class<?>) CaptureService.class));
                DaemonService.this.bindService();
            }
        }
    }

    /* loaded from: classes.dex */
    public static class CancelNotificationService extends Service {
        @Override // android.app.Service
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override // android.app.Service
        public void onCreate() {
            super.onCreate();
            startForeground(10, new Notification());
            stopSelf();
        }
    }

    /* loaded from: classes.dex */
    public class ButtonBroadcastReceiver extends BroadcastReceiver {
        public ButtonBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(DaemonService.ACTION_CAPTURE) || action.equals(DaemonService.ACTION_DAEMON)) {
                Log.d(DaemonService.this.TAG, "onReceive: Broadcast -- DaemonServices");
                DaemonService.this.keepAliveFlag = false;
                Process.killProcess(Process.myPid());
            }
        }
    }
}
