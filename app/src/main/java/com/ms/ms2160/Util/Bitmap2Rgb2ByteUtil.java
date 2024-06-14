package com.ms.ms2160.Util;

import android.graphics.Bitmap;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class Bitmap2Rgb2ByteUtil {
    public static byte[] getRGBByBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return convertColorToByte(iArr);
    }

    public static byte[] convertColorToByte(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        byte[] bArr = new byte[iArr.length * 3];
        for (int i = 0; i < iArr.length; i++) {
            int i2 = i * 3;
            bArr[i2] = (byte) ((iArr[i] >> 16) & 255);
            bArr[i2 + 1] = (byte) ((iArr[i] >> 8) & 255);
            bArr[i2 + 2] = (byte) (iArr[i] & 255);
        }
        return bArr;
    }

    public static Bitmap createMyBitmap(byte[] bArr, int i, int i2) {
        int[] convertByteToColor = convertByteToColor(bArr);
        if (convertByteToColor == null) {
            return null;
        }
        try {
            return Bitmap.createBitmap(convertByteToColor, 0, i, i, i2, Bitmap.Config.ARGB_8888);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bitmap createMyBitmapRGBA(byte[] bArr, int i, int i2) {
        int[] convertByteToColorRGBA = convertByteToColorRGBA(bArr);
        if (convertByteToColorRGBA == null) {
            return null;
        }
        try {
            return Bitmap.createBitmap(convertByteToColorRGBA, 0, i, i, i2, Bitmap.Config.ARGB_8888);
        } catch (Exception unused) {
            return null;
        }
    }

    private static int[] convertByteToColorRGBA(byte[] bArr) {
        int length = bArr.length;
        if (length == 0) {
            return null;
        }
        int i = (length / 4) + 0;
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 4;
            iArr[i2] = (bArr[i3 + 2] & 255) | ((bArr[i3] << 16) & 16711680) | ((bArr[i3 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ViewCompat.MEASURED_STATE_MASK;
        }
        return iArr;
    }

    public static Bitmap createMyBitmap565(byte[] bArr, int i, int i2) {
        int[] convertByteToColor565 = convertByteToColor565(bArr);
        if (convertByteToColor565 == null) {
            return null;
        }
        try {
            return Bitmap.createBitmap(convertByteToColor565, 0, i, i, i2, Bitmap.Config.RGB_565);
        } catch (Exception unused) {
            return null;
        }
    }

    private static int[] convertByteToColor565(byte[] bArr) {
        int length = bArr.length;
        if (length == 0) {
            return null;
        }
        int i = (length / 4) + 0;
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = i2 * 4;
            iArr[i2] = (bArr[i3 + 2] & 255) | ((bArr[i3] << 16) & 16711680) | ((bArr[i3 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ViewCompat.MEASURED_STATE_MASK;
        }
        return iArr;
    }

    private static int[] convertByteToColor(byte[] bArr) {
        int i;
        int length = bArr.length;
        int i2 = 0;
        System.arraycopy(bArr, 384000, new byte[1200], 0, 1200);
        if (length == 0) {
            return null;
        }
        int i3 = length % 3 != 0 ? 1 : 0;
        int i4 = (length / 3) + i3;
        int[] iArr = new int[i4];
        if (i3 == 0) {
            while (i2 < i4) {
                int i5 = i2 * 3;
                iArr[i2] = (bArr[i5 + 2] & 255) | ((bArr[i5] << 16) & 16711680) | ((bArr[i5 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ViewCompat.MEASURED_STATE_MASK;
                i2++;
            }
        } else {
            while (true) {
                i = i4 - 1;
                if (i2 >= i) {
                    break;
                }
                int i6 = i2 * 3;
                iArr[i2] = (bArr[i6 + 2] & 255) | ((bArr[i6] << 16) & 16711680) | ((bArr[i6 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ViewCompat.MEASURED_STATE_MASK;
                i2++;
            }
            iArr[i] = -16777216;
        }
        return iArr;
    }

    public static byte[] convertByte4ToByte3(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[(bArr.length / 4) * 3];
        for (int i = 0; i < bArr.length / 4; i++) {
            int i2 = i * 3;
            int i3 = i * 4;
            bArr2[i2] = bArr[i3];
            bArr2[i2 + 1] = bArr[i3 + 1];
            bArr2[i2 + 2] = bArr[i3 + 2];
        }
        return bArr2;
    }

    public static byte[] convertByte4ToByte2(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[(bArr.length / 4) * 2];
        for (int i = 0; i < bArr.length / 4; i++) {
            int i2 = i * 4;
            int i3 = bArr[i2 + 2] & 63488;
            int i4 = bArr[i2 + 1] & 2016;
            int i5 = bArr[i2 + 0] & 31;
            int i6 = i * 2;
            int i7 = i3 | i4;
            bArr2[i6] = (byte) ((i5 & 255) | i7);
            bArr2[i6 + 1] = (byte) ((i5 & 0) | i7);
        }
        return bArr2;
    }
}
