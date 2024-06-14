package com.ms.ms2160.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Process;
import com.ms.ms2160.Util.Util;
import java.util.List;

/* loaded from: classes.dex */
public class ShotApplication extends Application {
    private Intent intent;
    private List<Activity> mActivityList;
    private int result;
    private int settingChanged;
    private int useThread;
    private int videoPort = Util.DataType.VIDEO_PORT.INVALID;
    private int connectState = -1;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
    }

    public int getResult() {
        return this.result;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public int getVideoPort() {
        return this.videoPort;
    }

    public int getConnectState() {
        return this.connectState;
    }

    public int getuseThread() {
        return this.useThread;
    }

    public int getSettingChanged() {
        return this.settingChanged;
    }

    public void setResult(int i) {
        this.result = i;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void setVideoPort(int i) {
        this.videoPort = i;
    }

    public void setConnectState(int i) {
        this.connectState = i;
    }

    public void setuseThread(int i) {
        this.useThread = i;
    }

    public void setSettingChanged(int i) {
        this.settingChanged = i;
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
