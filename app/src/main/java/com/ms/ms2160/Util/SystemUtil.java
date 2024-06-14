package com.ms.ms2160.Util;

import android.os.Build;
import java.util.Locale;

/* loaded from: classes.dex */
public class SystemUtil {
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSystemModel() {
        return Build.MODEL;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }
}
