package com.ms.ms2160.permission;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

/* loaded from: classes.dex */
public class MobileInfoUtils {
    private static String getMobileType() {
        return Build.MANUFACTURER;
    }

    public void jumpStartInterface(Context context) {
        Intent intent = new Intent();
        try {
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            Log.e("HLQ_Struggle", "******************当前手机型号为：" + getMobileType());
            ComponentName componentName = null;
            if (getMobileType().equals("Xiaomi")) {
                componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            } else if (getMobileType().equals("Letv")) {
                intent.setAction("com.letv.android.permissionautoboot");
            } else if (getMobileType().equals("samsung")) {
                componentName = ComponentName.unflattenFromString("com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity");
            } else if (getMobileType().equals("HUAWEI")) {
                componentName = ComponentName.unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity");
            } else if (getMobileType().equals("vivo")) {
                componentName = ComponentName.unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");
            } else if (getMobileType().equals("Meizu")) {
                componentName = ComponentName.unflattenFromString("com.meizu.safe/.permission.SmartBGActivity");
            } else if (getMobileType().equals("OPPO")) {
                componentName = ComponentName.unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity");
            } else if (getMobileType().equals("ulong")) {
                componentName = new ComponentName("com.yulong.android.coolsafe", ".ui.activity.autorun.AutoRunListActivity");
            } else if (Build.VERSION.SDK_INT >= 9) {
                Log.e("HLQ_Struggle", "APPLICATION_DETAILS_SETTINGS");
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                intent.setAction("android.intent.action.VIEW");
                intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("HLQ_Struggle", e.getLocalizedMessage());
            context.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }
}
