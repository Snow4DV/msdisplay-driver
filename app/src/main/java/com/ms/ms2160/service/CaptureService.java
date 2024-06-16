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
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaPlayer;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.ms.ms2160.BuildConfig;
import com.ms.ms2160.IMyAidlInterface;
import com.ms.ms2160.R;
import com.ms.ms2160.Util.Ms2160Util;
import com.ms.ms2160.Util.SystemUtil;
import com.ms.ms2160.Util.Util;
import com.ms.ms2160.crashmanager.CrashApplication;
import com.ms.ms2160.myapplication.HandlerThreadHandler;
import com.ms.ms2160.myapplication.MainActivity;
import com.ms.ms2160.myapplication.MediaProjectionActivity;
import com.ms.ms2160.myapplication.ShotApplication;
import com.ms.ms2160.myapplication.USBDevice;
import com.ms.ms2160.myapplication.USBMonitor;
import com.ms.ms2160.permission.FloatActivity;
import com.ms.ms2160.permission.PermissionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class CaptureService extends Service implements ImageReader.OnImageAvailableListener {
    private static final String ACTION_CAPTURE = "com.ms.ms2160.CaptureDelete";
    private static final String ACTION_DAEMON = "com.ms.ms2160.DaemonDelete";
    public static boolean Transferoff = false;
    public static boolean VideoOnOff = false;
    public static boolean VideoOnOff_old = false;
    public static boolean isStarted = false;
    public static USBDevice s_mUSBDevice;
    byte[] RGBBuffer;
    private ButtonBroadcastReceiver bReceiver;
    private Connection connection;
    private int crop_screenHeight;
    private int crop_screenWidht;
    public String file;
    Intent intent;
    private WindowManager.LayoutParams layoutParams;
    private Context mContext;
    private byte mDisplayHotPlug;
    private int mResultCode;
    private Intent mResultData;
    private int mSDRAMType;
    SettingReceiver mSettingReceiver;
    private Util.Select_tim[] mTimingList;
    private int mTransferMode;
    protected USBMonitor mUSBMonitor;
    private UsbDeviceConnection mUsbDeviceConnection;
    private int mVideoDisplayColorSpace;
    private byte mVideoDisplayVIC;
    private int mVideoInColorSpace;
    private int mVieonInMEMColorSpace;
    private MyBinder myBinder;
    Notification notification;
    private int screenDensity;
    private int screenHeight;
    private int screenWidth;
    private int src_screenHeight;
    private int src_screenWidth;
    private View view;
    private WindowManager windowManager;
    public static Boolean isUsb30 = true;
    public static Boolean isMS913x = true;
    public static Handler sHandler = null;
    public static boolean keepAliveFlag = false;
    public static int mVideoDisplayPort = Util.DataType.VIDEO_PORT.INVALID;
    private String TAG = "CaptureService";
    protected USBDevice mUSBDevice = null;
    boolean isInit = true;
    private UsbEndpoint mUsbEndpoint = null;
    private MediaProjection mMediaProjection = null;
    private ImageReader mImageReader = null;
    private MediaPlayer bgmediaPlayer = null;
    private int useThread = 0;
    private ImageView imageView = null;
    private Thread updateThread = null;
    private boolean viewAdded = false;
    private boolean viewHide = false;
    private Boolean isPermission = false;
    private Boolean isPhone = true;
    private Boolean triggerAudio = false;
    String phoneFactory = null;
    String phoneModel = null;
    private int testCount = 0;
    private boolean isStopTrans = false;
    private Handler mHandler = new Handler() { // from class: com.ms.ms2160.service.CaptureService.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 10091:
                    if (CaptureService.this.mUSBDevice == null || CaptureService.this.mUsbEndpoint == null) {
                        return;
                    }
                    Log.e("MainActivity", "Handle Message Main Thread...");
                    CaptureService.this.interfaceInit();
                    CaptureService.this.videoInit();
                    if (CaptureService.this.useThread == 1) {
                        CaptureService.this.mUSBDevice.BulkOpen();
                    }
                    CaptureService.this.displayInit();
                    CaptureService.this.homekey();
                    CaptureService.s_mUSBDevice = CaptureService.this.mUSBDevice;
                    CaptureService.keepAliveFlag = true;
                    CaptureService.this.bindService();
                    return;
                case 10092:
                    if (CaptureService.this.mUSBDevice == null || CaptureService.this.mUsbEndpoint == null) {
                        return;
                    }
                    CaptureService.this.displayUnInit();
                    CaptureService.this.videoUnInit();
                    try {
                        Thread.sleep(200L);
                    } catch (Exception unused) {
                    }
                    CaptureService.this.interfaceInit();
                    CaptureService.this.videoInit();
                    CaptureService.this.displayInit();
                    CaptureService.this.homekey();
                    CaptureService.s_mUSBDevice = CaptureService.this.mUSBDevice;
                    return;
                case 10093:
                    if (CaptureService.this.mUSBDevice == null || CaptureService.this.mUsbEndpoint == null) {
                        return;
                    }
                    CaptureService.this.displayInit();
                    return;
                default:
                    return;
            }
        }
    };
    private final String ACTION_USB_REFRESH = "com.ms.ms2160.USB_REFRESH";
    Runnable keepAliveThread = new Runnable() { // from class: com.ms.ms2160.service.CaptureService.2
        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000L);
                } catch (Exception unused) {
                }
                if (!CaptureService.isServiceRunning(CaptureService.this.getApplicationContext(), "com.ms.ms2160.service.DaemonService") && CaptureService.keepAliveFlag) {
                    Log.e(CaptureService.this.TAG, "mainactivity keepAliveThread restart");
                    new Intent(CaptureService.this.getApplicationContext(), (Class<?>) MainActivity.class).setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                }
            }
        }
    };
    private int video_on_flag = 0;
    private byte[] b = null;
    private final USBMonitor.OnDeviceConnectListener mOnDeviceConnectListener = new USBMonitor.OnDeviceConnectListener() { // from class: com.ms.ms2160.service.CaptureService.4
        @Override // com.ms.ms2160.myapplication.USBMonitor.OnDeviceConnectListener
        public void onCancel(UsbDevice usbDevice) {
        }

        @Override // com.ms.ms2160.myapplication.USBMonitor.OnDeviceConnectListener
        public void onDettach(UsbDevice usbDevice) {
        }

        @Override // com.ms.ms2160.myapplication.USBMonitor.OnDeviceConnectListener
        public void onAttach(UsbDevice usbDevice) {
            CaptureService.this.mUSBMonitor.requestPermission(usbDevice);
        }

        @Override // com.ms.ms2160.myapplication.USBMonitor.OnDeviceConnectListener
        public void onConnect(final UsbDevice usbDevice, final USBMonitor.UsbControlBlock usbControlBlock, boolean z) {
            CaptureService.isUsb30 = Boolean.valueOf(usbControlBlock.mInfo.version.equals("31.00"));
            new Thread(new Runnable() { // from class: com.ms.ms2160.service.CaptureService.4.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.e(CaptureService.this.TAG, "onConnect ...............");
                    Log.e(CaptureService.this.TAG, "run: " + CaptureService.this.mUSBDevice);
                    Log.e(CaptureService.this.TAG, "run: " + CaptureService.this.mUsbEndpoint);
                    if (CaptureService.this.mUSBDevice == null) {
                        Log.v(CaptureService.this.TAG, "onConnect:1+" + CaptureService.this.mUSBDevice);
                        CaptureService.this.mUSBDevice = new USBDevice(CaptureService.this.mContext);
                        CaptureService.this.mUSBDevice.open(usbControlBlock);
                    }
                    if (CaptureService.this.mUsbEndpoint == null) {
                        Log.v(CaptureService.this.TAG, "onConnect:2=");
                        CaptureService.this.mUsbDeviceConnection = usbControlBlock.getConnection();
                        int interfaceCount = usbDevice.getInterfaceCount();
                        UsbEndpoint usbEndpoint = null;
                        boolean z2 = false;
                        for (int i = 0; i < interfaceCount && !z2; i++) {
                            UsbInterface usbInterface = usbDevice.getInterface(i);
                            int i2 = 0;
                            while (true) {
                                if (i2 < usbInterface.getEndpointCount()) {
                                    usbEndpoint = usbInterface.getEndpoint(i2);
                                    if (usbEndpoint.getAddress() == 129) { // was 4. TODO look it up
                                        z2 = true;
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                        CaptureService.this.mUsbEndpoint = usbEndpoint;
                        if (CaptureService.this.mUSBDevice == null || CaptureService.this.mUsbEndpoint == null) {
                            return;
                        }
                        CaptureService.this.interfaceInit();
                        CaptureService.mVideoDisplayPort = CaptureService.this.mUSBDevice.getVideoPort();
                        ((ShotApplication) CaptureService.this.getApplication()).setVideoPort(CaptureService.mVideoDisplayPort);
                        CaptureService.this.intent = new Intent(CaptureService.this.getApplicationContext(), (Class<?>) MediaProjectionActivity.class);
                        CaptureService.this.intent.putExtra("messenger", new Messenger(CaptureService.this.mHandler));
                        CaptureService.this.intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                        ((ShotApplication) CaptureService.this.getApplication()).setConnectState(1);
                        Log.e(CaptureService.this.TAG, "run:  start activity");
                        CaptureService.this.startActivity(CaptureService.this.intent);
                        CaptureService.isMS913x = Boolean.valueOf(CaptureService.this.mUSBDevice.isMS913X());
                    }
                }
            }).start();
        }

        @Override // com.ms.ms2160.myapplication.USBMonitor.OnDeviceConnectListener
        public void onDisconnect(UsbDevice usbDevice, USBMonitor.UsbControlBlock usbControlBlock) throws InterruptedException {
            if (CaptureService.this.mUSBDevice == null || CaptureService.this.mUsbEndpoint == null) {
                Log.i(CaptureService.this.TAG, "onDisconnect no mUSBDeice is null");
                return;
            }
            CaptureService.this.displayUnInit();
            if (CaptureService.this.useThread == 1) {
                CaptureService.this.mUSBDevice.BulkClose();
            }
            Log.e(CaptureService.this.TAG, "onDisconnect ...............");
            ((ShotApplication) CaptureService.this.getApplication()).setConnectState(0);
            CaptureService.this.mUSBDevice.nativeClearhalt();
            CaptureService.this.mUSBDevice.releaseInterface(0);
            CaptureService.this.releaseCamera();
            Toast.makeText(CaptureService.this.getApplicationContext(), "USB_DEVICE_DETACHED", Toast.LENGTH_SHORT).show();
            CaptureService.s_mUSBDevice = null;
            CaptureService.this.killbyname();
        }
    };
    private byte frame_id = 0;
    private Util.RESOLUTION mScreenResolution = new Util.RESOLUTION();
    private byte mScreenId = 0;
    private Util.RESOLUTION mVideoInResolution = new Util.RESOLUTION();
    private Util.RESOLUTION mVideoDisplayResolution = new Util.RESOLUTION();
    public volatile boolean exit = false;
    private int count_fail = 0;

    /* loaded from: classes.dex */
    class MyBinder extends IMyAidlInterface.Stub {
        @Override // com.ms.ms2160.IMyAidlInterface
        public void basicTypes(int i, long j, boolean z, float f, double d, String str) throws RemoteException {
        }

        MyBinder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class Connection implements ServiceConnection {
        Connection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(CaptureService.this.TAG, "UnionBind CaptureService receive Daemon connect msg");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(CaptureService.this.TAG, "UnionBind CaptureService receive Daemon Disconnect msg");
            CaptureService.this.startService();
            if (CaptureService.keepAliveFlag) {
                CaptureService.this.startService(new Intent(CaptureService.this, (Class<?>) DaemonService.class));
                CaptureService.this.bindService();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindService() {
        Log.e(this.TAG, "UnionBind CaptureService bind Daemon ");
        this.connection = new Connection();
        bindService(new Intent(this, (Class<?>) DaemonService.class), this.connection, Context.BIND_AUTO_CREATE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void homekey() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
    }

    /* loaded from: classes.dex */
    private class SettingReceiver extends BroadcastReceiver {
        private SettingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                Log.e(CaptureService.this.TAG, "mUSBMonitor.registerCheckDevice");
                CaptureService.this.mUSBMonitor.registerCheckDevice();
                return;
            }
            if (action.equals("com.ms.ms2160.setting_change")) {
                if (CaptureService.this.mUSBDevice == null || CaptureService.this.mUsbEndpoint == null) {
                    return;
                }
                Log.i(CaptureService.this.TAG, "com.ms.ms2160.setting_change");
                CaptureService.this.mUSBDevice.ClearPool();
                CaptureService.this.videoUnInit();
                CaptureService.this.displayUnInit();
                Message message = new Message();
                message.what = 10091;
                CaptureService.this.mHandler.sendMessage(message);
                return;
            }
            if (action.equals("com.ms.ms2160.USB_REFRESH")) {
                Log.e(CaptureService.this.TAG, "mUSBMonitor.registerCheckDevice");
                CaptureService.this.mUSBMonitor.registerCheckDevice();
            }
        }
    }

    /* loaded from: classes.dex */
    public class ServiceBinder extends Binder {
        public ServiceBinder() {
        }

        public CaptureService getService() {
            return CaptureService.this;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        Log.e(this.TAG, "UnionBind onCreate");
        super.onCreate();
        this.myBinder = new MyBinder();
        USBMonitor uSBMonitor = new USBMonitor(this, this.mOnDeviceConnectListener);
        this.mUSBMonitor = uSBMonitor;
        uSBMonitor.register();
        this.mSettingReceiver = new SettingReceiver();
        IntentFilter intentFilter = new IntentFilter("com.ms.ms2160.setting_change");
        intentFilter.addAction("com.ms.ms2160.USB_REFRESH");
        registerReceiver(this.mSettingReceiver, intentFilter);
        isStarted = true;
        ((ShotApplication) getApplication()).setVideoPort(Util.DataType.VIDEO_PORT.INVALID);
        this.mContext = this;
        this.isPermission = false;
        if (this.isPhone.booleanValue()) {
            createFloatView();
        }
        showSystemParameter();
        sHandler = this.mHandler;
        getApplicationContext().startService(new Intent(this, (Class<?>) CaptureService.class));
        sendBroadcast(new Intent("com.ms.ms2160.USB_REFRESH"));
        startService();
        HandlerThreadHandler createHandler = HandlerThreadHandler.createHandler(this.TAG);
        if (createHandler != null) {
            createHandler.postDelayed(this.keepAliveThread, 3000L);
        }
        RegisterBroadCast();
    }

    private void RegisterBroadCast() {
        this.bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CAPTURE);
        intentFilter.addAction(ACTION_DAEMON);
        registerReceiver(this.bReceiver, intentFilter);
    }

    public static boolean isServiceRunning(Context context, String str) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).getRunningServices(50);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            if (runningServices.get(i).service.getClassName().equals(str)) {
                Log.d("", "isServiceStarted: capture find Daemon");
                return true;
            }
        }
        return false;
    }

    public boolean isPowerConnected(Context context) {
        int intExtra = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        return intExtra == 2 || intExtra == 5;
    }

    public void killbyname() throws InterruptedException {
        stopSelf();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        sendBroadcast(new Intent(ACTION_CAPTURE));
        CrashApplication.getInstance().removeAllActivity();
        activityManager.killBackgroundProcesses(BuildConfig.APPLICATION_ID);
        Process.killProcess(Process.myPid());
    }

    private void showSystemParameter() {
        Log.e("系统参数：", "手机厂商：" + SystemUtil.getDeviceBrand());
        Log.e("系统参数：", "手机型号：" + SystemUtil.getSystemModel());
        Log.e("系统参数：", "手机当前系统语言：" + SystemUtil.getSystemLanguage());
        Log.e("系统参数：", "Android系统版本号：" + SystemUtil.getSystemVersion());
        Log.d("build", "BOARD:" + Build.BOARD);
        Log.d("build", "BOOTLOADER:" + Build.BOOTLOADER);
        Log.d("build", "CPU_ABI:" + Build.CPU_ABI);
        Log.d("build", "CPU_ABI2:" + Build.CPU_ABI2);
        Log.d("build", "DEVICE:" + Build.DEVICE);
        Log.d("build", "DISPLAY:" + Build.DISPLAY);
        Log.d("build", "FINGERPRINT:" + Build.FINGERPRINT);
        Log.d("build", "HARDWARE:" + Build.HARDWARE);
        Log.d("build", "HOST:" + Build.HOST);
        Log.d("build", "ID:" + Build.ID);
        Log.d("build", "MANUFACTURER:" + Build.MANUFACTURER);
        Log.d("build", "MODEL:" + Build.MODEL);
        Log.d("build", "PRODUCT:" + Build.PRODUCT);
        Log.d("build", "RADIO:" + Build.RADIO);
        Log.d("build", "TAGS:" + Build.TAGS);
        Log.d("build", "TIME:" + Build.TIME);
        Log.d("build", "TYPE:" + Build.TYPE);
        Log.d("build", "UNKNOWN:unknown");
        Log.d("build", "USER:" + Build.USER);
        Log.d("build", "VERSION.CODENAME:" + Build.VERSION.CODENAME);
        Log.d("build", "VERSION.INCREMENTAL:" + Build.VERSION.INCREMENTAL);
        Log.d("build", "VERSION.RELEASE:" + Build.VERSION.RELEASE);
        Log.d("build", "VERSION.SDK:" + Build.VERSION.SDK);
        Log.d("build", "VERSION.SDK_INT:" + Build.VERSION.SDK_INT);
        int i = 0;
        if (Build.BOARD.toLowerCase().contains("kirin970") || Build.BOARD.toLowerCase().contains("k62v1_64_bsp") || Build.BOARD.toLowerCase().contains("tb8321p2_bsp") || Build.BOARD.toLowerCase().contains("ch009_wt")) {
            this.useThread = 0;
            ((ShotApplication) getApplication()).setuseThread(0);
        } else {
            this.useThread = 1;
            ((ShotApplication) getApplication()).setuseThread(1);
        }
        if (this.isPhone.booleanValue()) {
            this.triggerAudio = true;
            this.phoneFactory = SystemUtil.getDeviceBrand().toLowerCase();
            String lowerCase = SystemUtil.getSystemModel().toLowerCase();
            this.phoneModel = lowerCase;
            String[] split = lowerCase.split(" ");
            String r5 = "";
            if (split != null) {
                r5 = split[0] != null ? split[0] : "";
                if (split.length >= 2 && split[1] != null) {
                    try {
                        i = Integer.parseInt(split[1]);
                    } catch (Exception unused) {
                    }
                }
            }
            if (this.phoneFactory.toLowerCase().equals("xiaomi") && r5.equals("mi") && i >= 9) {
                this.triggerAudio = false;
            }
            if (isPowerConnected(this)) {
                this.triggerAudio = false;
            }
            this.triggerAudio = false;
        } else {
            this.triggerAudio = false;
        }
        Log.e("系统参数：", "triggerAudio is " + this.triggerAudio);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startService() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget);
        remoteViews.setTextViewText(R.id.wt_title, "MS");
        remoteViews.setOnClickPendingIntent(R.id.wt_delete, PendingIntent.getBroadcast(this, 1, new Intent(ACTION_CAPTURE), PendingIntent.FLAG_UPDATE_CURRENT));
        PendingIntent activity = PendingIntent.getActivity(this.mContext, 0, new Intent(this, (Class<?>) MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(new NotificationChannel("channel_1", "MS", NotificationManager.IMPORTANCE_LOW));
            Notification build = new Notification.Builder(this.mContext, "channel_1").setCategory(NotificationCompat.CATEGORY_MESSAGE).setSmallIcon(R.drawable.ic_launcher).setContentTitle("MS").setContentText("").setContentIntent(activity).setAutoCancel(true).build();
            build.contentView = remoteViews;
            startForeground(10, build);
            return;
        }
        Notification build2 = new Notification.Builder(this.mContext).setSmallIcon(R.drawable.ic_refresh_white_24dp).setWhen(System.currentTimeMillis()).setTicker("MS").setContentTitle("MS").setContentText("MS").setOngoing(true).setPriority(Notification.PRIORITY_MAX).setContentIntent(activity).setAutoCancel(false).build();
        this.notification = build2;
        startForeground(100, build2);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.e(this.TAG, "UnionBind onStartCommand");
        return super.onStartCommand(intent, i, i2);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.e(this.TAG, "onDestroy");
        isStarted = false;
        this.mUSBMonitor.unregister();
        unregisterReceiver(this.mSettingReceiver);
        ButtonBroadcastReceiver buttonBroadcastReceiver = this.bReceiver;
        if (buttonBroadcastReceiver != null) {
            unregisterReceiver(buttonBroadcastReceiver);
        }
        super.onDestroy();
        removeView();
    }

    public void removeView() {
        if (this.viewAdded) {
            this.windowManager.removeView(this.view);
            this.viewAdded = false;
        }
    }

    private void createFloatView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.uac_gameassist_float_small, (ViewGroup) null);
        this.view = inflate;
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_float);
        this.imageView = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        this.windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2010, 8, -2);
        this.layoutParams = layoutParams;
        layoutParams.gravity = 51;
        this.layoutParams.width = 1;
        this.layoutParams.height = 1;
        if (Build.VERSION.SDK_INT >= 26) {
            this.layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            this.layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        FloatActivity.request(this.mContext, new PermissionListener() { // from class: com.ms.ms2160.service.CaptureService.3
            @Override // com.ms.ms2160.permission.PermissionListener
            public void onSuccess() {
                System.out.println("----------success");
                CaptureService.this.isPermission = true;
            }

            @Override // com.ms.ms2160.permission.PermissionListener
            public void onFail() {
                System.out.println("----------fail");
                CaptureService.this.isPermission = false;
            }
        });
    }

    private void refresh() {
        if (this.viewAdded) {
            this.windowManager.updateViewLayout(this.view, this.layoutParams);
        } else {
            this.windowManager.addView(this.view, this.layoutParams);
            this.viewAdded = true;
        }
    }

    @Override // android.media.ImageReader.OnImageAvailableListener
    public void onImageAvailable(ImageReader imageReader) {
        Image acquireLatestImage;
        if (this.isStopTrans || (acquireLatestImage = imageReader.acquireLatestImage()) == null) {
            return;
        }
        int height = acquireLatestImage.getHeight();
        int width = acquireLatestImage.getWidth();
        Image.Plane[] planes = acquireLatestImage.getPlanes();
        ByteBuffer buffer = acquireLatestImage.getPlanes()[0].getBuffer();
        if (this.b == null) {
            this.b = new byte[buffer.remaining()];
        }
        byte[] bArr = this.b;
        buffer.get(bArr, 0, bArr.length);
        int pixelStride = planes[0].getPixelStride();
        int rowStride = (planes[0].getRowStride() - (this.screenWidth * pixelStride)) / pixelStride;
        Log.i("MainActivity", "b.length " + this.b.length);
        if (!Transferoff && VideoOnOff_old == VideoOnOff) {
            try {
                BulkSendSplit(this.b, this.b.length, width, height, rowStride, this.mVideoInColorSpace, this.useThread, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (VideoOnOff) {
            displayUnInit();
            if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS_SVIDEO) {
                this.mUSBDevice.xdata_clrBits(62592, (byte) 3);
            } else if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_HDMI) {
                this.mUSBDevice.set_video_on((byte) 0);
            }
            this.video_on_flag = 1;
        } else if (this.video_on_flag == 1) {
            if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS_SVIDEO) {
                this.mUSBDevice.xdata_setBits(62592, (byte) 3);
            } else if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_HDMI) {
                this.mUSBDevice.set_video_on((byte) 1);
            }
            this.video_on_flag = 0;
        }
        VideoOnOff_old = VideoOnOff;
        Log.i("MainActivity", "onImageAvailable end ");
        buffer.rewind();
        acquireLatestImage.close();
    }

    public static String save(Bitmap bitmap, Context context) {
        String str = Environment.getExternalStorageDirectory() + "/Screenshot/";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str2 = str + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format(new Date()) + ".jpg";
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str2));
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                        fileOutputStream2.close();
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        return str2;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    //th = th2;
                }
            } catch (Exception e3) {
                //e = e3;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return str2;
    }

    public void clean() {
        MediaProjection mediaProjection = this.mMediaProjection;
        if (mediaProjection != null) {
            mediaProjection.stop();
        }
        ImageReader imageReader = this.mImageReader;
        if (imageReader != null) {
            imageReader.setOnImageAvailableListener(null, null);
            this.mImageReader.close();
        }
        if (this.b != null) {
            this.b = null;
        }
    }

    private void getSize() {
        Context applicationContext = getApplicationContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) applicationContext.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
        this.screenDensity = displayMetrics.densityDpi;
        this.src_screenWidth = displayMetrics.widthPixels;
        this.src_screenHeight = displayMetrics.heightPixels;
        this.screenWidth = this.mVideoInResolution.width;
        if (this.isPhone.booleanValue()) {
            this.screenHeight = this.mVideoInResolution.height;
        } else {
            this.screenHeight = this.crop_screenHeight;
            this.screenWidth = this.crop_screenWidht;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displayUnInit() {
        if (SystemUtil.getSystemModel().equals("2106118C") || SystemUtil.getSystemModel().equals("M2102K1AC")) {
            return;
        }
        clean();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displayInit() {
        Log.e(this.TAG, "displayInit");
        this.mResultData = ((ShotApplication) getApplication()).getIntent();
        this.mResultCode = ((ShotApplication) getApplication()).getResult();
        try {
            this.mMediaProjection = ((MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE)).getMediaProjection(this.mResultCode, this.mResultData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.mMediaProjection != null) {
            try {
                getSize();
                ImageReader newInstance = ImageReader.newInstance(this.screenWidth, this.screenHeight, 1, 2); // TODO idk what should have been here
                this.mImageReader = newInstance;
                this.mMediaProjection.createVirtualDisplay("Screenshot11", this.screenWidth, this.screenHeight, this.screenDensity, 16, newInstance.getSurface(), null, null);
                this.mImageReader.setOnImageAvailableListener(this, null);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.isInit = true;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.myBinder;
    }

    protected void releaseCamera() {
        USBDevice uSBDevice = this.mUSBDevice;
        if (uSBDevice != null) {
            try {
                uSBDevice.close();
            } catch (Exception unused) {
            }
            this.mUSBDevice = null;
            this.mUsbEndpoint = null;
        }
    }

    protected void listenService() {
        if (this.bgmediaPlayer == null) {
            this.bgmediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.wusheng);
        }
        this.bgmediaPlayer.setLooping(true);
        this.bgmediaPlayer.start();
    }

    protected void unlistenService() {
        MediaPlayer mediaPlayer = this.bgmediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.bgmediaPlayer.release();
        }
    }

    public void videoInit() {
        this.isStopTrans = false;
        usb_plug(1);
    }

    public void videoUnInit() {
        this.isStopTrans = true;
        usb_plug(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int interfaceInit() {
        return this.mUSBDevice.claimInterface(0);
    }


    void BulkSendSplit(byte[] bArr, int length, int width, int height, int stride, int color, int usethread, int rgb32Type)  throws java.lang.InterruptedException {
        int i8;
        int i9 = 0;
        if (usethread == 0) {
            if (this.isInit) {
                if (color == 1) {
                    this.RGBBuffer = new byte[width * height * 3];
                } else if (color == 0) {
                    this.RGBBuffer = new byte[width * height * 3];
                }
                this.mUSBDevice.set_video_mute(false);
                this.isInit = false;
                Log.i("MainActivity", "isInit = false,stride =" + stride);
            }
            if (color == 1) {
                this.RGBBuffer = Ms2160Util.rgb8888torgb888(bArr, width, height, stride);
            } else if (color == 0) {
                this.RGBBuffer = Ms2160Util.rgb8888torgb888(bArr, width, height, stride);
            } else {
                i8 = length;
                this.mUSBDevice.frame_switch(this.frame_id);
                if (i8 > 16384) {
                    int i10 = i8 / 16384;
                    if (i8 % 16384 > 0) {
                        i10++;
                    }
                    for (int i11 = 0; i11 < i10; i11++) {
                        int i12 = i11 * 16384;
                        byte[] copyOfRange = Arrays.copyOfRange(this.RGBBuffer, i12, i12 + 16384);
                        Log.i("MainActivity", "packet_count: " + this.mUsbDeviceConnection.bulkTransfer(this.mUsbEndpoint, copyOfRange, copyOfRange.length, 1000));
                    }
                }
                Log.i("MainActivity", "packet_count1: " + this.mUsbDeviceConnection.bulkTransfer(this.mUsbEndpoint, new byte[0], 0, 1000));
                this.frame_id = (byte) (this.frame_id != 0 ? 0 : 1);
                return;
            }
            i8 = width * height * 3;
            this.mUSBDevice.frame_switch(this.frame_id);
            if (i8 > 16384) {
            }
            Log.i("MainActivity", "packet_count1: " + this.mUsbDeviceConnection.bulkTransfer(this.mUsbEndpoint, new byte[0], 0, 1000));
            this.frame_id = (byte) (this.frame_id != 0 ? 0 : 1);
            return;
        }
        USBDevice uSBDevice = this.mUSBDevice;
        if (uSBDevice != null) {
            if (this.isInit) {
                uSBDevice.set_video_mute(false);
                this.isInit = false;
            }
            i9 = this.mUSBDevice.converColortransferBulk(bArr, length, width, height, stride, color, usethread, rgb32Type, isMS913x.booleanValue());
        }
        Log.i("MainActivity", "packet_count: " + i9);
    }

    private Util.AS7160_VIDEO_TIMING as7160_get_std_timing(byte b) {
        for (byte b2 = 0; b2 < Util.g_arrTimingTable.length; b2 = (byte) (b2 + 1)) {
            if (b == Util.g_arrTimingTable[b2].u8_vic) {
                return Util.g_arrTimingTable[b2].st_timing;
            }
        }
        return null;
    }

    private void usb_plug(int i) {
        this.exit = true;
        int i2 = 0;
        if (i == 1) {
            this.count_fail = 0;
            this.mDisplayHotPlug = (byte) 1;
            updateGlobalParameters();
            updateVideoParametersByConfiguration();
            try {
                Thread.sleep(30L);
            } catch (Exception unused) {
            }
            int powerState = this.mUSBDevice.getPowerState();
            try {
                Thread.sleep(30L);
            } catch (Exception unused2) {
            }
            Log.e("MainActivity", "powerState: " + powerState);
            if (powerState == 0) {
                this.mUSBDevice.set_power_on((byte) 1);
                if (isUsb30.booleanValue()) {
                    this.mUSBDevice.xdata_write(53268, (byte) 32);
                }
                while (true) {
                    if (i2 >= 10) {
                        break;
                    }
                    if (this.mUSBDevice.getPowerOnSuccess() == 0) {
                        Log.e("MainActivity", "count: " + i2);
                        break;
                    }
                    try {
                        Thread.sleep(100L);
                    } catch (Exception unused3) {
                    }
                    i2++;
                }
            }
            this.mUSBDevice.set_video_mute(true);
            if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS_SVIDEO) {
                this.mUSBDevice.setCVBSState();
            }
            try {
                Thread.sleep(50L);
            } catch (Exception unused4) {
            }
            start_transaction();
            try {
                Thread.sleep(50L);
            } catch (Exception unused5) {
            }
            this.mUSBDevice.set_video_on((byte) 1);
            this.mUSBDevice.setAndroidHpd();
            return;
        }
        this.mDisplayHotPlug = (byte) 0;
        this.mUSBDevice.set_video_on((byte) 0);
        try {
            Thread.sleep(200L);
        } catch (Exception unused6) {
        }
        stop_transaction();
        try {
            Thread.sleep(50L);
        } catch (Exception unused7) {
        }
    }

    private void usb_extract() {
        this.exit = true;
        stop_transaction();
    }

    private void updateGlobalParameters() {
        this.mSDRAMType = this.mUSBDevice.getSDRAMType();
        this.mTransferMode = Util.DataType.TRANSFER_MODE.FRAME;
        this.mTimingList = null;
    }

    private void updateVideoParametersByConfiguration() {
        SharedPreferences sharedPreferences = getSharedPreferences("setting", 0);
        int i = sharedPreferences.getInt(getString(R.string.sharedPref_color), -1);
        int i2 = sharedPreferences.getInt(getString(R.string.sharedPref_timing), -1);
        this.mScreenResolution.framerate = 60;
        this.mVideoInResolution.framerate = 60;
        if (i == -1 || i == 0) {
            this.mVideoDisplayColorSpace = (byte) Util.DataType.COLORSPACE.RGB888;
            this.mVideoInColorSpace = Util.DataType.COLORSPACE.RGB888;
        } else if (i == 1) {
            this.mVideoDisplayColorSpace = (byte) Util.DataType.COLORSPACE.RGB565;
            this.mVideoInColorSpace = Util.DataType.COLORSPACE.RGB565;
        } else if (i == 2) {
            this.mVideoDisplayColorSpace = (byte) Util.DataType.COLORSPACE.YUV422;
            this.mVideoInColorSpace = Util.DataType.COLORSPACE.YUV422;
        }
        Log.e("CaptureService", "mVideoDisplayPort:" + mVideoDisplayPort);
        if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_HDMI || mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_VGA || mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_DIGITAL) {
            if (i2 == -1 || i2 == 0) {
                this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT;
                this.mVideoInResolution.framerate = 60;
                this.mScreenResolution.width = 1920;
                this.mScreenResolution.height = 1080;
                this.mVideoInResolution.width = 1920;
                this.mVideoInResolution.height = 1080;
                if (!isUsb30.booleanValue()) {
                    this.mScreenResolution.width = 1280;
                    this.mScreenResolution.height = 720;
                    this.mVideoInResolution.width = 1280;
                    this.mVideoInResolution.height = 720;
                }
            } else if (i2 == 1) {
                this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT;
                this.mVideoInResolution.framerate = 60;
                this.mScreenResolution.width = 1280;
                this.mScreenResolution.height = 720;
                this.mVideoInResolution.width = 1280;
                this.mVideoInResolution.height = 720;
            } else if (i2 == 2) {
                this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_VESA_66_800X600_60;
                this.mVideoInResolution.framerate = 60;
                this.mScreenResolution.width = 800;
                this.mScreenResolution.height = 600;
                this.mVideoInResolution.width = 800;
                this.mVideoInResolution.height = 600;
            }
        } else if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_YPBPR) {
            if (i2 != -1) {
                if (i2 == 0) {
                    this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_VESA_129_1920X1080_60_DMT;
                    this.mVideoInResolution.framerate = 60;
                    this.mScreenResolution.width = 1280;
                    this.mScreenResolution.height = 720;
                    this.mVideoInResolution.width = 1280;
                    this.mVideoInResolution.height = 720;
                } else if (i2 != 1) {
                    if (i2 == 2) {
                        this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ;
                        this.mVideoInResolution.framerate = 50;
                        this.mScreenResolution.width = 720;
                        this.mScreenResolution.height = 576;
                        this.mVideoInResolution.width = 720;
                        this.mVideoInResolution.height = 576;
                    } else if (i2 == 3) {
                        this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ;
                        this.mVideoInResolution.framerate = 60;
                        this.mScreenResolution.width = 720;
                        this.mScreenResolution.height = 480;
                        this.mVideoInResolution.width = 720;
                        this.mVideoInResolution.height = 480;
                    }
                }
            }
            this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_VESA_79_1280X720_60_DMT;
            this.mVideoInResolution.framerate = 60;
            this.mScreenResolution.width = 1280;
            this.mScreenResolution.height = 720;
            this.mVideoInResolution.width = 1280;
            this.mVideoInResolution.height = 720;
        } else if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS || mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_SVIDEO || mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS_SVIDEO) {
            if (i2 == -1 || i2 == 0) {
                this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_CEA_17_720x576P_50HZ;
                this.mVideoInResolution.framerate = 50;
                this.mScreenResolution.width = 720;
                this.mScreenResolution.height = 576;
                this.mVideoInResolution.width = 720;
                this.mVideoInResolution.height = 576;
            } else if (i2 == 1) {
                this.mVideoDisplayVIC = (byte) Util._E_AS7160_VIDEO_FORMAT_.VFMT_CEA_02_720x480P_60HZ;
                this.mVideoInResolution.framerate = 60;
                this.mScreenResolution.width = 720;
                this.mScreenResolution.height = 480;
                this.mVideoInResolution.width = 720;
                this.mVideoInResolution.height = 480;
            }
        }
        if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_HDMI) {
            this.mVideoDisplayColorSpace = this.mUSBDevice.getDisplayColorSpace();
        } else {
            this.mVideoDisplayColorSpace = Util.DataType.COLORSPACE.RGB888;
        }
        if (mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS || mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_SVIDEO || mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS_SVIDEO) {
            if (i == 1) {
                this.mVieonInMEMColorSpace = Util.DataType.COLORSPACE.RGB565;
            } else {
                this.mVieonInMEMColorSpace = Util.DataType.COLORSPACE.YUV422;
            }
            Log.d("CaptureService", "mVieonInMEMColorSpace:" + this.mVieonInMEMColorSpace);
            return;
        }
        if (this.mVideoInColorSpace == Util.DataType.COLORSPACE.RGB888) {
            if (this.mSDRAMType == Util.SDRAM_TYPE.SDRAM_16M) {
                this.mVieonInMEMColorSpace = this.mVideoInColorSpace;
                return;
            }
            if (this.mSDRAMType == Util.SDRAM_TYPE.SDRAM_8M) {
                this.mVieonInMEMColorSpace = ((this.mVideoInResolution.width < 1920 || this.mVideoInResolution.height < 1080) && (this.mVideoInResolution.width * this.mVideoInResolution.height) * 3 <= 4194304) ? Util.DataType.COLORSPACE.RGB888 : Util.DataType.COLORSPACE.YUV422;
                return;
            }
            if (this.mSDRAMType == Util.SDRAM_TYPE.SDRAM_4M) {
                this.mVieonInMEMColorSpace = ((this.mVideoInResolution.width < 1280 || this.mVideoInResolution.height < 720) && (this.mVideoInResolution.width * this.mVideoInResolution.height) * 3 <= 2097152) ? Util.DataType.COLORSPACE.RGB888 : Util.DataType.COLORSPACE.YUV422;
                return;
            } else if (this.mSDRAMType == Util.SDRAM_TYPE.SDRAM_2M) {
                this.mVieonInMEMColorSpace = ((this.mVideoInResolution.width < 800 || this.mVideoInResolution.height < 600) && (this.mVideoInResolution.width * this.mVideoInResolution.height) * 3 <= 1048576) ? Util.DataType.COLORSPACE.RGB888 : Util.DataType.COLORSPACE.YUV422;
                return;
            } else {
                int i3 = this.mSDRAMType;
                int i4 = Util.SDRAM_TYPE.SDRAM_NONE;
                return;
            }
        }
        this.mVieonInMEMColorSpace = this.mVideoInColorSpace;
    }

    private void stop_transaction() {
        USBDevice uSBDevice = this.mUSBDevice;
        if (uSBDevice != null) {
            uSBDevice.set_start_trans((byte) 0);
        }
    }

    private void start_transaction() {
        if (this.mTransferMode == Util.DataType.TRANSFER_MODE.FRAME) {
            this.mUSBDevice.set_transfer_mode_frame();
        } else if (this.mTransferMode != Util.DataType.TRANSFER_MODE.FIX_BLOCK_MN && this.mTransferMode != Util.DataType.TRANSFER_MODE.FIX_BLOCK_WH && this.mTransferMode != Util.DataType.TRANSFER_MODE.MANUAL_BLOCK) {
            int i = Util.DataType.TRANSFER_MODE.MEM_BYPAS_FRAME;
        }
        byte b = (byte) ((((byte) this.mVieonInMEMColorSpace) << 4) | ((byte) this.mVideoInColorSpace));
        Log.e("CaptureService", "color:" + ((int) b));
        int i2 = (this.mVideoInResolution.width + 3) & (-4);
        if (this.isPhone.booleanValue()) {
            this.mUSBDevice.set_video_in(i2, this.mVideoInResolution.height, b, (byte) 0);
            this.mUSBDevice.set_video_out(this.mVideoDisplayVIC, (byte) this.mVideoDisplayColorSpace, this.mVideoInResolution.width, this.mVideoInResolution.height);
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) this.mContext.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
            this.screenDensity = displayMetrics.densityDpi;
            this.src_screenWidth = displayMetrics.widthPixels;
            int i3 = displayMetrics.heightPixels;
            this.src_screenHeight = i3;
            int i4 = this.src_screenWidth;
            int i5 = (int) (this.mVideoInResolution.width / (i4 >= i3 ? i4 / i3 : i3 / i4));
            this.crop_screenHeight = i5;
            this.crop_screenHeight = (int) (i5 * 0.9d);
            int i6 = (int) (this.mVideoInResolution.width * 0.9d);
            this.crop_screenWidht = i6;
            this.crop_screenWidht = (i6 + 3) & (-4);
            try {
                Thread.sleep(100L);
            } catch (Exception unused) {
            }
            this.mUSBDevice.set_video_in(this.crop_screenWidht, this.crop_screenHeight, b, (byte) 0);
            try {
                Thread.sleep(100L);
            } catch (Exception unused2) {
            }
            this.mUSBDevice.set_video_out(this.mVideoDisplayVIC, (byte) this.mVideoDisplayColorSpace, this.crop_screenWidht, this.crop_screenHeight);
            try {
                Thread.sleep(200L);
            } catch (Exception unused3) {
            }
        }
        this.mUSBDevice.set_start_trans((byte) 1);
    }

    /* loaded from: classes.dex */
    public class ButtonBroadcastReceiver extends BroadcastReceiver {
        public ButtonBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(CaptureService.ACTION_CAPTURE) || action.equals(CaptureService.ACTION_DAEMON)) {
                Log.d(CaptureService.this.TAG, "onReceive: Broadcast -- CaptureServices");
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CaptureService.keepAliveFlag = false;
                try {
                    CaptureService.this.killbyname();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
