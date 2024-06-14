package com.ms.ms2160.Util;

import android.app.ActivityManager;
import android.content.Context;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Ms2160Util {
    public static byte[] rgb8888torgb565(byte[] bArr, int i, int i2) {
        int i3 = i * i2;
        if (bArr.length < i3 * 3) {
            return null;
        }
        byte[] bArr2 = new byte[i3 * 2];
        int i4 = 0;
        for (int i5 = 0; i5 < i3 * 4; i5 += 4) {
            bArr2[i4] = (byte) (bArr[i5 + 0] >> 3);
            int i6 = i5 + 1;
            bArr2[i4] = (byte) (bArr2[i4] | ((byte) (((bArr[i6] >> 2) & 7) << 5)));
            int i7 = i4 + 1;
            bArr2[i7] = (byte) (bArr[i6] >> 5);
            bArr2[i7] = (byte) (bArr2[i7] | ((byte) (bArr[i5 + 2] & 248)));
            i4 += 2;
        }
        return bArr2;
    }

    public static byte[] rgb8888torgb888(byte[] bArr, int i, int i2, int i3) {
        int i4 = i * i2;
        int i5 = i4 * 4;
        if (bArr.length < i5) {
            return null;
        }
        byte[] bArr2 = new byte[i4 * 3];
        if (i3 == 0) {
            int i6 = 0;
            for (int i7 = 0; i7 < i5; i7 += 4) {
                bArr2[i6] = bArr[i7 + 2];
                bArr2[i6 + 1] = bArr[i7 + 1];
                bArr2[i6 + 2] = bArr[i7 + 0];
                i6 += 3;
            }
        } else {
            int i8 = 0;
            int i9 = 0;
            for (int i10 = 0; i10 < i2; i10++) {
                for (int i11 = 0; i11 < i; i11++) {
                    if (i3 == 0) {
                        bArr2[i9] = bArr[i8 + 2];
                        bArr2[i9 + 1] = bArr[i8 + 1];
                        bArr2[i9 + 2] = bArr[i8 + 0];
                        i9 += 3;
                    } else {
                        bArr2[i9] = bArr[i8 + 2];
                        bArr2[i9 + 1] = bArr[i8 + 1];
                        bArr2[i9 + 2] = bArr[i8 + 0];
                        i9 += 3;
                        if (i11 == i - 1) {
                            i8 += (i3 + 1) * 4;
                        }
                    }
                    i8 += 4;
                }
            }
        }
        return bArr2;
    }

    public static boolean isServiceRunning(Context context, String str) {
        if (!"".equals(str) && str != null) {
            ArrayList arrayList = (ArrayList) ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(60);
            for (int i = 0; i < arrayList.size(); i++) {
                if (((ActivityManager.RunningServiceInfo) arrayList.get(i)).service.getClassName().toString().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}
