package com.ms.ms2160.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.ms.ms2160.R;
import com.ms.ms2160.crashmanager.CrashApplication;

/* loaded from: classes.dex */
public class MediaProjectionActivity extends Activity {
    public static final int REQUEST_CODE_CAPTURE_IMAGE = 789;
    private MediaProjection mMediaProjection;
    private MediaProjectionManager mMediaProjectionManager;
    private String TAG = "MediaProjectionActivity";
    Messenger mMessenger = null;

    @Override // android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 789) {
            Log.e(this.TAG, "Unknown request code: " + i);
            return;
        }
        if (i2 != -1) {
            Log.e(this.TAG, "Screen Cast Permission Denied");
            finish();
            return;
        }
        ((ShotApplication) getApplication()).setResult(i2);
        ((ShotApplication) getApplication()).setIntent(intent);
        Message message = new Message();
        message.what = 10091;
        try {
            this.mMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CrashApplication.getInstance().addActivity(this);
        this.mMessenger = (Messenger) getIntent().getExtras().get("messenger");
        setContentView(R.layout.activity_mediaprojection);
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        this.mMediaProjectionManager = mediaProjectionManager;
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_CODE_CAPTURE_IMAGE);
    }
}
