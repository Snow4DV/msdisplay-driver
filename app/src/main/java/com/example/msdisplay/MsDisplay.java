package com.example.msdisplay;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.util.Log;
import com.example.msdisplay.Util;

/* loaded from: classes.dex */
public class MsDisplay {
    private static final boolean DEBUG = true;
    private static final String DEFAULT_USBFS = "/dev/bus/usb";
    private static final String TAG = "MsDisplay";
    protected long mNativePtr;
    int libusb_device_handle = 0;
    private final int TimeOut = 1000;
    private final byte HIDCMD_READ_XDATA = -75;
    private final byte HIDCMD_READ_EEPROM = -27;
    private final byte HIDCMD_READ_FLASH = -11;
    private final byte HIDCMD_READ_SDRAM = -43;
    private final byte HIDCMD_WRITE_XDATA = -74;
    private final byte HIDCMD_WRITE_EEPROM = -26;
    private final byte HIDCMD_WRITE_FLASH = -10;
    private final byte HIDCMD_WRITE_SDRAM = -42;
    private final byte HIDCMD_SPECIAL = -90;
    private final byte HIDCMD_SPECIAL_FRAMETRIGGER = 0;
    private final byte HIDCMD_SPECIAL_VIDEO_IN = 1;
    private final byte HIDCMD_SPECIAL_VIDEO_OUT = 2;
    private final byte HIDCMD_SPECIAL_TRANSMODE = 3;
    private final byte HIDCMD_SPECIAL_STARTTRANS = 4;
    private final byte HIDCMD_SPECIAL_VIDEOON = 5;
    private final byte HIDCMD_SPECIAL_POWERON = 7;
    private final int ram_hpd_flag = 50;
    private final int ram_video_port = 49;
    private final int ram_sdram_type = 48;
    private final int ram_display_colorspace = 51;

    public native void RGB32ToRGB24(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4);

    public native void RGB32ToRGB565(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4);

    public native void RGB32ToUYVY(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, int i3, int i4);

    public native int nativeBulkClose(long j, int i);

    public native int nativeBulkOpen(long j, int i, int i2);

    public native int nativeClaimInterface(long j, int i);

    public native int nativeClearPool();

    public native void nativeClearhalt(long j, int i);

    public final native long nativeConnect(int i, int i2, int i3, int i4, int i5, String str);

    public native int nativeControlTransfer(long j, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, int i7);

    public native int nativeControlTransferRead(long j, int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, int i7);

    public native int nativeConvertColorBulkTransfer(long j, int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z);

    public final native long nativeCreate();

    public native int nativeLibusbOpen(int i);

    public native int nativeRealseInterface(long j, int i);

    public final native void nativeRelease(long j);

    public void set_video_mute(UsbDeviceConnection usbDeviceConnection, boolean z) {
        if (z) {
            xdata_modBits(usbDeviceConnection, 61444, (byte) 0, Byte.MIN_VALUE);
        } else {
            xdata_modBits(usbDeviceConnection, 61444, Byte.MIN_VALUE, Byte.MIN_VALUE);
        }
    }

    public void setNativePtr(long j) {
        this.mNativePtr = j;
    }

    public void set_video_brightness(UsbDeviceConnection usbDeviceConnection, byte b) {
        if (b >= 100) {
            b = 99;
        }
        xdata_write(usbDeviceConnection, 62480, (byte) (((b * 256) / 100) - 128));
    }

    public void set_video_contrast(UsbDeviceConnection usbDeviceConnection, byte b) {
        if (b >= 100) {
            b = 99;
        }
        xdata_write(usbDeviceConnection, 62481, (byte) ((b * 256) / 100));
    }

    public void set_video_saturation(UsbDeviceConnection usbDeviceConnection, byte b) {
        if (b >= 100) {
            b = 99;
        }
        xdata_write(usbDeviceConnection, 62482, (byte) ((b * 256) / 100));
    }

    public void set_video_hue(UsbDeviceConnection usbDeviceConnection, byte b) {
        if (b >= 100) {
            b = 99;
        }
        xdata_write(usbDeviceConnection, 62483, (byte) (((b * 256) / 100) - 128));
    }

    public void video_shift_left(UsbDeviceConnection usbDeviceConnection, int i) {
        int xdata_read = (((xdata_read(usbDeviceConnection, 62369) & 255) << 8) | (xdata_read(usbDeviceConnection, 62368) & 255)) - i;
        xdata_write(usbDeviceConnection, 62369, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62368, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public void video_shift_right(UsbDeviceConnection usbDeviceConnection, int i) {
        int xdata_read = ((xdata_read(usbDeviceConnection, 62369) & 255) << 8) + (xdata_read(usbDeviceConnection, 62368) & 255) + i;
        xdata_write(usbDeviceConnection, 62369, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62368, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public void video_shift_up(UsbDeviceConnection usbDeviceConnection, int i) {
        int xdata_read = (((xdata_read(usbDeviceConnection, 62373) & 255) << 8) | (xdata_read(usbDeviceConnection, 62372) & 255)) - i;
        xdata_write(usbDeviceConnection, 62373, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62372, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public void video_shift_down(UsbDeviceConnection usbDeviceConnection, int i) {
        int xdata_read = (((xdata_read(usbDeviceConnection, 62373) & 255) << 8) | (xdata_read(usbDeviceConnection, 62372) & 255)) + i;
        xdata_write(usbDeviceConnection, 62373, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62372, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public void video_stretch_right(UsbDeviceConnection usbDeviceConnection, float f) {
        int xdata_read = (int) ((((xdata_read(usbDeviceConnection, 62341) & 255) << 8) | (xdata_read(usbDeviceConnection, 62340) & 255)) + f);
        xdata_write(usbDeviceConnection, 62341, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62340, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public void video_shrink_left(UsbDeviceConnection usbDeviceConnection, float f) {
        int xdata_read = (int) ((((xdata_read(usbDeviceConnection, 62341) & 255) << 8) | (xdata_read(usbDeviceConnection, 62340) & 255)) - f);
        xdata_write(usbDeviceConnection, 62341, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62340, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public void video_stretch_down(UsbDeviceConnection usbDeviceConnection, float f) {
        int xdata_read = (int) ((((xdata_read(usbDeviceConnection, 62345) & 255) << 8) | (xdata_read(usbDeviceConnection, 62344) & 255)) + f);
        xdata_write(usbDeviceConnection, 62345, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62344, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public void video_shrink_up(UsbDeviceConnection usbDeviceConnection, float f) {
        int xdata_read = (int) ((((xdata_read(usbDeviceConnection, 62345) & 255) << 8) | (xdata_read(usbDeviceConnection, 62344) & 255)) - f);
        xdata_write(usbDeviceConnection, 62345, (byte) (xdata_read >> 8));
        xdata_write(usbDeviceConnection, 62344, (byte) xdata_read);
        xdata_write(usbDeviceConnection, 62336, (byte) 1);
    }

    public int get_shake_id(UsbDeviceConnection usbDeviceConnection) {
        byte xdata_read = xdata_read(usbDeviceConnection, 57311);
        return (xdata_read(usbDeviceConnection, 57312) & 255) | (xdata_read << 8);
    }

    public void frame_transfer_switch(UsbDeviceConnection usbDeviceConnection, byte b) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{18, -14, 2, 0, b, 0, 0, 0}, 8, 1000);
        usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
    }

    public void xdata_write_switch(UsbDeviceConnection usbDeviceConnection, int i, byte b) {
        byte[] bArr = new byte[8];
        bArr[0] = -74;
        bArr[1] = (byte) ((i >> 8) & 255);
        bArr[2] = (byte) (i & 255);
        bArr[3] = b;
        if (i == 61954 || i == 61955) {
            bArr[4] = 1;
        } else {
            bArr[4] = 0;
        }
        bArr[5] = 0;
        bArr[6] = 0;
        bArr[7] = 0;
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, bArr, 8, 1000);
        int controlTransfer = usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
        if (controlTransfer != 8) {
            Log.d(TAG, "xdata_write addr: " + Integer.toHexString(i));
        }
        Log.d(TAG, "xdata_write_switch value: " + controlTransfer);
    }

    public void xdata_write(UsbDeviceConnection usbDeviceConnection, int i, byte b) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{-74, (byte) ((i >> 8) & 255), (byte) (i & 255), b, 0, 0, 0, 0}, 8, 1000);
        int controlTransfer = usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
        if (controlTransfer != 8) {
            Log.d(TAG, "xdata_write addr: " + Integer.toHexString(i));
        }
        Log.d(TAG, "xdata_write value: " + controlTransfer);
    }

    public void frame_trigger(UsbDeviceConnection usbDeviceConnection, byte b, byte b2) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{-90, 0, b, b2, 0, 0, 0, 0}, 8, 1000);
        Log.d(TAG, "frame_trigger value: " + nativeControlTransfer(this.mNativePtr, transferInfo.indexInterface, transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout));
    }

    public byte xdata_read(UsbDeviceConnection usbDeviceConnection, int i) {
        byte[] bArr = {-75, (byte) ((i >> 8) & 255), (byte) (i & 255), 0, 0, 0, 0, 0};
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, bArr, 8, 1000);
        int controlTransfer = usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
        if (i == 49) {
            Log.d(TAG, "nativeControlTransfer write value: " + controlTransfer);
        }
        transferInfo.requestType = 161;
        transferInfo.request = 1;
        int controlTransfer2 = usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
        if (i == 49) {
            Log.d(TAG, "nativeControlTransfer read value: " + controlTransfer2 + "videoport:" + ((int) bArr[3]));
        }
        return bArr[3];
    }

    public int xdata_read_atomic(UsbDeviceConnection usbDeviceConnection, int i) {
        byte[] bArr = {-75, (byte) ((i >> 8) & 255), (byte) (i & 255), 0, 0, 0, 0, 0};
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, bArr, 8, 5);
        int controlTransfer = usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
        if (i == 49) {
            Log.d(TAG, "nativeControlTransfer write value: " + controlTransfer);
        }
        transferInfo.requestType = 161;
        transferInfo.request = 1;
        int controlTransfer2 = usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
        if (i == 49) {
            Log.d(TAG, "nativeControlTransfer read value: " + controlTransfer2 + "videoport:" + ((int) bArr[3]));
        }
        Log.d(TAG, "xdata_read_atomic: " + controlTransfer2 + "videoport:" + ((int) bArr[3]));
        return controlTransfer2 <= 0 ? controlTransfer2 : bArr[3];
    }

    public void xdata_modBits(UsbDeviceConnection usbDeviceConnection, int i, byte b, byte b2) {
        xdata_write(usbDeviceConnection, i, (byte) (((byte) (b & b2)) | ((byte) (xdata_read(usbDeviceConnection, i) & ((byte) (~b2))))));
    }

    public void xdata_setBits(UsbDeviceConnection usbDeviceConnection, int i, byte b) {
        xdata_write(usbDeviceConnection, i, (byte) (((byte) (b & 255)) | ((byte) (xdata_read(usbDeviceConnection, i) & ((byte) (~b))))));
    }

    public void xdata_clrBits(UsbDeviceConnection usbDeviceConnection, int i, byte b) {
        xdata_write(usbDeviceConnection, i, (byte) (((byte) (b & 0)) | ((byte) (xdata_read(usbDeviceConnection, i) & ((byte) (~b))))));
    }

    public void set_start_trans(UsbDeviceConnection usbDeviceConnection, byte b) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{-90, 4, b, 0, 0, 0, 0, 0}, 8, 1000);
        Log.d(TAG, "set_start_trans value: " + usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout));
    }

    public void set_transfer_mode_frame(UsbDeviceConnection usbDeviceConnection) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{-90, 3, (byte) Util.DataType.TRANSFER_MODE.FRAME, 0, 0, 0, 0, 0}, 8, 1000);
        Log.d(TAG, "set_start_trans value: " + usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout));
    }

    public void set_video_in(UsbDeviceConnection usbDeviceConnection, int i, int i2, byte b, byte b2) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{-90, 1, (byte) (i >> 8), (byte) i, (byte) (i2 >> 8), (byte) i2, b, b2}, 8, 1000);
        usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout);
    }

    public void set_video_on(UsbDeviceConnection usbDeviceConnection, byte b) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{-90, 5, b, 0, 0, 0, 0, 0}, 8, 1000);
        Log.d(TAG, "set_video_on value: " + usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout));
    }

    public void set_power_on(UsbDeviceConnection usbDeviceConnection, byte b) {
        byte[] bArr = new byte[8];
        bArr[0] = -90;
        bArr[1] = 7;
        bArr[2] = b;
        if (b == 1) {
            bArr[3] = 2;
        } else {
            bArr[3] = 0;
        }
        bArr[4] = 0;
        bArr[5] = 0;
        bArr[6] = 0;
        bArr[7] = 0;
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, bArr, 8, 1000);
        Log.d(TAG, "set_power_on value: " + usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout));
    }

    public void set_video_out(UsbDeviceConnection usbDeviceConnection, byte b, byte b2, int i, int i2) {
        TransferInfo transferInfo = new TransferInfo(0, 33, 9, 768, 0, new byte[]{-90, 2, b, b2, (byte) (i >> 8), (byte) i, (byte) (i2 >> 8), (byte) i2}, 8, 1000);
        Log.d(TAG, "set_power_on value: " + usbDeviceConnection.controlTransfer(transferInfo.requestType, transferInfo.request, transferInfo.value, transferInfo.index, transferInfo.buffer, transferInfo.length, transferInfo.timeout));
    }

    public byte getDisplayHotPlug(UsbDeviceConnection usbDeviceConnection) {
        return (byte) xdata_read_atomic(usbDeviceConnection, 50);
    }

    public int getPowerState(UsbDeviceConnection usbDeviceConnection) {
        return xdata_read(usbDeviceConnection, 50720);
    }

    public int getPowerOnSuccess(UsbDeviceConnection usbDeviceConnection) {
        return xdata_read(usbDeviceConnection, 50260);
    }

    public void setCVBSState(UsbDeviceConnection usbDeviceConnection) {
        xdata_modBits(usbDeviceConnection, 61792, (byte) 0, (byte) 32);
        xdata_write(usbDeviceConnection, 61489, (byte) 52);
    }

    public void setAndroidHpd(UsbDeviceConnection usbDeviceConnection) {
        xdata_write(usbDeviceConnection, 57070, (byte) 1);
        xdata_modBits(usbDeviceConnection, 62976, (byte) 0, (byte) 1);
    }

    public int getVideoPort(UsbDeviceConnection usbDeviceConnection) {
        return xdata_read(usbDeviceConnection, 49);
    }

    public int getSDRAMType(UsbDeviceConnection usbDeviceConnection) {
        return xdata_read(usbDeviceConnection, 48);
    }

    public int getDisplayColorSpace(UsbDeviceConnection usbDeviceConnection) {
        return xdata_read(usbDeviceConnection, 51);
    }

    public static boolean loadUrlSoLib(Context context, String str, String str2) {
        System.load(str2 + str);
        return DEBUG;
    }
}
