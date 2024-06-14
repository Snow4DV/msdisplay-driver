package com.ms.ms2160.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.ms.ms2160.crashmanager.CrashApplication;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class FloatActivity extends Activity {
    private static PermissionListener mPermissionListener;
    private static List<PermissionListener> mPermissionListenerList;

    public static synchronized void request(Context context, PermissionListener permissionListener) {
        synchronized (FloatActivity.class) {
            if (PermissionUtil.hasPermission(context)) {
                permissionListener.onSuccess();
                return;
            }
            if (mPermissionListenerList == null) {
                mPermissionListenerList = new ArrayList();
                mPermissionListener = new PermissionListener() { // from class: com.ms.ms2160.permission.FloatActivity.1
                    @Override // com.ms.ms2160.permission.PermissionListener
                    public void onSuccess() {
                        Iterator it = FloatActivity.mPermissionListenerList.iterator();
                        while (it.hasNext()) {
                            ((PermissionListener) it.next()).onSuccess();
                        }
                        FloatActivity.mPermissionListenerList.clear();
                    }

                    @Override // com.ms.ms2160.permission.PermissionListener
                    public void onFail() {
                        Iterator it = FloatActivity.mPermissionListenerList.iterator();
                        while (it.hasNext()) {
                            ((PermissionListener) it.next()).onFail();
                        }
                        FloatActivity.mPermissionListenerList.clear();
                    }
                };
                Intent intent = new Intent(context, (Class<?>) FloatActivity.class);
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                context.startActivity(intent);
            }
            mPermissionListenerList.add(permissionListener);
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CrashApplication.getInstance().addActivity(this);
        if (Build.VERSION.SDK_INT >= 23) {
            requestAlertWindowPermission();
        }
    }

    private void requestAlertWindowPermission() {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 756232212);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 756232212) {
            if (PermissionUtil.hasPermissionOnActivityResult(this)) {
                mPermissionListener.onSuccess();
            } else {
                mPermissionListener.onFail();
            }
        }
        finish();
    }
}
