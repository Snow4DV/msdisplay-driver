package com.ms.ms2160.myapplication;

import com.ms.ms2160.crashmanager.CrashApplication;
import com.ms.ms2160.crashmanager.UncaughtExceptionHandlerImpl;

/* loaded from: classes.dex */
public class App extends CrashApplication {
    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        UncaughtExceptionHandlerImpl.getInstance().init(this, true, true, 0L, MainActivity.class);
    }
}
