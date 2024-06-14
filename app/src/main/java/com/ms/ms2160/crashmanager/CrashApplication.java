package com.ms.ms2160.crashmanager;

import android.app.Activity;
import android.app.Application;
import android.os.Process;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CrashApplication extends Application {
    private static CrashApplication instance;
    private List<Activity> mActivityList = new ArrayList();

    public static synchronized CrashApplication getInstance() {
        CrashApplication crashApplication;
        synchronized (CrashApplication.class) {
            if (instance == null) {
                instance = new CrashApplication();
            }
            crashApplication = instance;
        }
        return crashApplication;
    }

    public void addActivity(Activity activity) {
        if (this.mActivityList.contains(activity)) {
            return;
        }
        this.mActivityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (this.mActivityList.contains(activity)) {
            this.mActivityList.remove(activity);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void removeAllActivity() {
        for (Activity activity : this.mActivityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        Process.killProcess(Process.myPid());
    }
}
