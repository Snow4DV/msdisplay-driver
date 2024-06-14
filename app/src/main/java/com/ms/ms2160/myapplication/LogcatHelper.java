package com.ms.ms2160.myapplication;

import android.content.Context;
import android.os.Environment;
import android.os.Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes.dex */
public class LogcatHelper {
    private static LogcatHelper INSTANCE;
    private static String PATH_LOGCAT;
    private LogDumper mLogDumper = null;
    private int mPId;

    public void init(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "XYBCLog";
        } else {
            PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + "XYBCLoged";
        }
        File file = new File(PATH_LOGCAT);
        if (file.exists()) {
            return;
        }
        file.mkdirs();
    }

    public static LogcatHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogcatHelper(context);
        }
        return INSTANCE;
    }

    private LogcatHelper(Context context) {
        init(context);
        this.mPId = Process.myPid();
    }

    public void start() {
        if (this.mLogDumper == null) {
            this.mLogDumper = new LogDumper(String.valueOf(this.mPId), PATH_LOGCAT);
        }
        this.mLogDumper.start();
    }

    public void stop() {
        LogDumper logDumper = this.mLogDumper;
        if (logDumper != null) {
            logDumper.stopLogs();
            this.mLogDumper = null;
        }
    }

    /* loaded from: classes.dex */
    private class LogDumper extends Thread {
        String cmds;
        private java.lang.Process logcatProc;
        private String mPID;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        private FileOutputStream out;

        public LogDumper(String str, String str2) {
            this.cmds = null;
            this.out = null;
            this.mPID = str;
            try {
                this.out = new FileOutputStream(new File("/sdcard", "log-" + LogcatHelper.this.getFileName() + ".txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.cmds = "logcat *:e *:i | grep \"(" + this.mPID + ")\"";
        }

        public void stopLogs() {
            this.mRunning = false;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            FileOutputStream fileOutputStream = null;
            String readLine;
            try {
                try {
                    this.logcatProc = Runtime.getRuntime().exec(this.cmds);
                    this.mReader = new BufferedReader(new InputStreamReader(this.logcatProc.getInputStream()), 1024);
                    while (this.mRunning && (readLine = this.mReader.readLine()) != null && this.mRunning) {
                        if (readLine.length() != 0 && this.out != null && readLine.contains(this.mPID)) {
                            this.out.write((readLine + "\n").getBytes());
                        }
                    }
                    java.lang.Process process = this.logcatProc;
                    if (process != null) {
                        process.destroy();
                        this.logcatProc = null;
                    }
                    BufferedReader bufferedReader = this.mReader;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                            this.mReader = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    fileOutputStream = this.out;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    java.lang.Process process2 = this.logcatProc;
                    if (process2 != null) {
                        process2.destroy();
                        this.logcatProc = null;
                    }
                    BufferedReader bufferedReader2 = this.mReader;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                            this.mReader = null;
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    FileOutputStream fileOutputStream2 = this.out;
                    if (fileOutputStream2 == null) {
                        return;
                    }
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e4) {
                        //e = e4;
                        e4.printStackTrace();
                        this.out = null;
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e5) {
                        //e = e5;
                        e5.printStackTrace();
                        this.out = null;
                    }
                    this.out = null;
                }
            } catch (Throwable th) {
                java.lang.Process process3 = this.logcatProc;
                if (process3 != null) {
                    process3.destroy();
                    this.logcatProc = null;
                }
                BufferedReader bufferedReader3 = this.mReader;
                if (bufferedReader3 != null) {
                    try {
                        bufferedReader3.close();
                        this.mReader = null;
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                FileOutputStream fileOutputStream3 = this.out;
                if (fileOutputStream3 != null) {
                    try {
                        fileOutputStream3.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                    this.out = null;
                    throw th;
                }
                throw th;
            }
        }
    }

    public String getFileName() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
    }
}
