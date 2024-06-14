package com.ms.ms2160.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/* loaded from: classes.dex */
public class KeepAliveJobService extends JobService {
    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i("KeepAliveJobService", "JobService onStartJob 开启");
        if (Build.VERSION.SDK_INT >= 24) {
            startJob(this);
        }
        if (!CaptureService.keepAliveFlag) {
            return false;
        }
        if (!ServiceUtils.isServiceRunning(this, CaptureService.class.getName())) {
            startService(new Intent(this, (Class<?>) CaptureService.class));
        }
        if (ServiceUtils.isServiceRunning(this, DaemonService.class.getName())) {
            return false;
        }
        startService(new Intent(this, (Class<?>) DaemonService.class));
        return false;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i("KeepAliveJobService", "JobService onStopJob 关闭");
        return false;
    }

    public static void startJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder persisted = new JobInfo.Builder(10, new ComponentName(context.getPackageName(), KeepAliveJobService.class.getName())).setPersisted(true);
        if (Build.VERSION.SDK_INT < 24) {
            persisted.setPeriodic(5000L);
        } else {
            persisted.setMinimumLatency(1000L);
        }
        jobScheduler.schedule(persisted.build());
    }
}
