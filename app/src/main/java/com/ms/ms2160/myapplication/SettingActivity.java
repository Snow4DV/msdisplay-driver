package com.ms.ms2160.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.ms.ms2160.R;
import com.ms.ms2160.Util.Util;
import com.ms.ms2160.crashmanager.CrashApplication;
import com.ms.ms2160.service.CaptureService;
import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SettingActivity extends AppCompatActivity {
    private Button btnSave;
    private int mConnectState;
    private int mVideoDisplayPort;
    private int settingChanged;
    private Spinner spinnerCapute;
    private Spinner spinnerColor;
    private Spinner spinnerTiming;
    private int useThread;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CrashApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_setting);
        this.mVideoDisplayPort = CaptureService.mVideoDisplayPort;
        this.spinnerColor = (Spinner) findViewById(R.id.spinner1);
        this.spinnerTiming = (Spinner) findViewById(R.id.spinner2);
        this.mConnectState = ((ShotApplication) getApplication()).getConnectState();
        this.useThread = ((ShotApplication) getApplication()).getuseThread();
        this.settingChanged = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("setting", 0);
        int i = sharedPreferences.getInt(getString(R.string.sharedPref_color), -1);
        int i2 = sharedPreferences.getInt(getString(R.string.sharedPref_timing), -1);
        Log.d("SettingActivity", "mVideoDisplayPort" + this.mVideoDisplayPort);
        Log.d("SettingActivity", "mConnectState" + this.mConnectState);
        Log.d("SettingActivity", "spinnerColor" + this.spinnerColor);
        Log.d("SettingActivity", "spinnerTiming" + this.spinnerTiming);
        if (this.mConnectState == 1) {
            ArrayList arrayList = new ArrayList();
            if (this.useThread == 0) {
                arrayList.add("High quality");
            } else {
                arrayList.add("High quality");
                if (!CaptureService.isUsb30.booleanValue()) {
                    arrayList.add("Low quality");
                }
            }
            this.spinnerColor.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList));
            if (this.mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_HDMI || this.mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_VGA || this.mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_DIGITAL) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add("1920*1080");
                arrayList2.add("1280*720");
                this.spinnerTiming.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList2));
                if (i2 == -1 || i2 == 0) {
                    this.spinnerTiming.setSelection(0);
                } else if (i2 == 1) {
                    this.spinnerTiming.setSelection(1);
                } else if (i2 == 2) {
                    this.spinnerTiming.setSelection(2);
                } else {
                    this.spinnerTiming.setSelection(1);
                }
            } else if (this.mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_YPBPR) {
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add("1920*1080");
                arrayList3.add("1280*720");
                arrayList3.add("720*576");
                arrayList3.add("720*480");
                this.spinnerTiming.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList3));
                if (i2 != -1) {
                    if (i2 == 0) {
                        this.spinnerTiming.setSelection(0);
                    } else if (i2 != 1) {
                        if (i2 == 2) {
                            this.spinnerTiming.setSelection(2);
                        } else if (i2 == 3) {
                            this.spinnerTiming.setSelection(3);
                        } else {
                            this.spinnerTiming.setSelection(1);
                        }
                    }
                }
                this.spinnerTiming.setSelection(1);
            } else if (this.mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS || this.mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_SVIDEO || this.mVideoDisplayPort == Util.DataType.VIDEO_PORT.VIDEO_OUTPUT_PORT_CVBS_SVIDEO) {
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add("720*576");
                arrayList4.add("720*480");
                this.spinnerTiming.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList4));
                if (i2 == -1 || i2 == 0) {
                    this.spinnerTiming.setSelection(0);
                } else if (i2 == 1) {
                    this.spinnerTiming.setSelection(1);
                }
            } else {
                ArrayList arrayList5 = new ArrayList();
                arrayList5.add(String.valueOf(this.mVideoDisplayPort));
                this.spinnerTiming.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList5));
                if (i2 != -1) {
                    if (i2 == 0) {
                        this.spinnerTiming.setSelection(0);
                    } else if (i2 != 1) {
                        if (i2 == 2) {
                            this.spinnerTiming.setSelection(2);
                        }
                    }
                }
                this.spinnerTiming.setSelection(1);
            }
            Spinner spinner = this.spinnerColor;
            if (i < 0) {
                i = 0;
            }
            spinner.setSelection(i);
        } else {
            this.spinnerColor.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()));
            this.spinnerTiming.setAdapter((SpinnerAdapter) new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()));
        }
        Button button = (Button) findViewById(R.id.btnSave);
        this.btnSave = button;
        button.setOnClickListener(new View.OnClickListener() { // from class: com.ms.ms2160.myapplication.SettingActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int selectedItemPosition = SettingActivity.this.spinnerColor.getSelectedItemPosition();
                int selectedItemPosition2 = SettingActivity.this.spinnerTiming.getSelectedItemPosition();
                SharedPreferences.Editor edit = SettingActivity.this.getSharedPreferences("setting", 0).edit();
                edit.putInt(SettingActivity.this.getString(R.string.sharedPref_color), selectedItemPosition);
                edit.putInt(SettingActivity.this.getString(R.string.sharedPref_timing), selectedItemPosition2);
                edit.commit();
                Message message = new Message();
                message.what = 10092;
                CaptureService.sHandler.sendMessage(message);
                SettingActivity.this.finish();
            }
        });
        if (this.mConnectState == 1) {
            this.btnSave.setEnabled(true);
        } else {
            this.btnSave.setEnabled(false);
        }
    }

    /* loaded from: classes.dex */
    public class Dict implements Serializable {
        private Integer id;
        private String text;

        public Dict() {
        }

        public Dict(Integer num, String str) {
            this.id = num;
            this.text = str;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer num) {
            this.id = num;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String str) {
            this.text = str;
        }

        public String toString() {
            return this.text;
        }
    }
}
