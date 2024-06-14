package com.ms.ms2160.service;

import android.app.ActivityManager;
import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.text.TextUtils;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ServiceUtils {
    public static boolean isServiceRunning(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Iterator<ActivityManager.RunningServiceInfo> it = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION).iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().service.getClassName(), str)) {
                return true;
            }
        }
        return false;
    }
}
