package com.ms.ms2160.myapplication;

import android.os.Bundle;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.ms.ms2160.R;
import com.ms.ms2160.crashmanager.CrashApplication;
import com.ms.ms2160.service.CaptureService;

/* loaded from: classes.dex */
public class ScreenActivity extends AppCompatActivity implements View.OnClickListener {
    Switch swVideoMute;
    Switch swh;
    EditText textView1;
    EditText textView2;
    int num = 0;
    float fnum = 0.0f;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CrashApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_screen);
        this.textView1 = (EditText) findViewById(R.id.et1);
        this.textView2 = (EditText) findViewById(R.id.et2);
        ((Button) findViewById(R.id.btnMt)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnMl)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnMr)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnMd)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnSt)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnSl)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnSr)).setOnClickListener(this);
        ((Button) findViewById(R.id.btnSd)).setOnClickListener(this);
        Switch r2 = (Switch) findViewById(R.id.switch1);
        this.swh = r2;
        r2.setText("传输开关");
        this.swh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ms.ms2160.myapplication.ScreenActivity.1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    CaptureService.Transferoff = true;
                    ScreenActivity.this.swh.setText("传输关闭");
                } else {
                    CaptureService.Transferoff = false;
                    ScreenActivity.this.swh.setText("传输打开");
                }
            }
        });
        Switch r22 = (Switch) findViewById(R.id.switch2);
        this.swVideoMute = r22;
        r22.setText("视频开关");
        this.swVideoMute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ms.ms2160.myapplication.ScreenActivity.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    CaptureService.VideoOnOff = true;
                    ScreenActivity.this.swVideoMute.setText("视频关闭");
                    return;
                }
                CaptureService.VideoOnOff = false;
                ScreenActivity.this.swVideoMute.setText("视频打开");
                Message message = new Message();
                message.what = 10093;
                CaptureService.sHandler.sendMessage(message);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (CaptureService.s_mUSBDevice == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.btnMd /* 2131296293 */:
                this.num = Integer.parseInt(this.textView1.getText().toString());
                CaptureService.s_mUSBDevice.video_shift_down(this.num);
                return;
            case R.id.btnMl /* 2131296294 */:
                this.num = Integer.parseInt(this.textView1.getText().toString());
                CaptureService.s_mUSBDevice.video_shift_left(this.num);
                return;
            case R.id.btnMr /* 2131296295 */:
                this.num = Integer.parseInt(this.textView1.getText().toString());
                CaptureService.s_mUSBDevice.video_shift_right(this.num);
                return;
            case R.id.btnMt /* 2131296296 */:
                this.num = Integer.parseInt(this.textView1.getText().toString());
                CaptureService.s_mUSBDevice.video_shift_up(this.num);
                return;
            case R.id.btnNative /* 2131296297 */:
            case R.id.btnSave /* 2131296298 */:
            default:
                return;
            case R.id.btnSd /* 2131296299 */:
                this.fnum = Float.parseFloat(this.textView2.getText().toString());
                CaptureService.s_mUSBDevice.video_stretch_down(this.fnum);
                return;
            case R.id.btnSl /* 2131296300 */:
                this.fnum = Float.parseFloat(this.textView2.getText().toString());
                CaptureService.s_mUSBDevice.video_shrink_left(this.fnum);
                return;
            case R.id.btnSr /* 2131296301 */:
                this.fnum = Float.parseFloat(this.textView2.getText().toString());
                CaptureService.s_mUSBDevice.video_stretch_right(this.fnum);
                return;
            case R.id.btnSt /* 2131296302 */:
                this.fnum = Float.parseFloat(this.textView2.getText().toString());
                CaptureService.s_mUSBDevice.video_shrink_up(this.fnum);
                return;
        }
    }
}
