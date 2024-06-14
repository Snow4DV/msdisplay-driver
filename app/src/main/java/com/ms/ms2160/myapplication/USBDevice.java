package com.ms.ms2160.myapplication;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import com.example.msdisplay.MsDisplay;
import com.ms.ms2160.myapplication.USBMonitor;

/* loaded from: classes.dex */
public class USBDevice {
    private static final boolean DEBUG = true;
    private static final String DEFAULT_USBFS = "/dev/bus/usb";
    private static final String TAG = USBDevice.class.getSimpleName();
    private static boolean isLoaded;
    private static Object obj;
    public MsDisplay mMsDisplay;
    int libusb_device_handle = 0;
    private final int TimeOut = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    private final int indexEndpoint = 4;
    private USBMonitor.UsbControlBlock mCtrlBlock = null;
    protected long mNativePtr = 0;
    private final int ram_hpd_flag = 50;

    static {
        System.loadLibrary("usb101");
        System.loadLibrary("msusb");
    }

    public USBDevice(Context context) {
        this.mMsDisplay = null;
        this.mMsDisplay = new MsDisplay();
    }

    public static boolean loadUrlSoLib(String str, String str2) {
        System.load(str2 + str);
        return DEBUG;
    }

    public synchronized void open(USBMonitor.UsbControlBlock usbControlBlock) {
        try {
            USBMonitor.UsbControlBlock m7clone = usbControlBlock.m7clone();
            this.mCtrlBlock = m7clone;
            this.mNativePtr = this.mMsDisplay.nativeConnect(m7clone.getVenderId(), this.mCtrlBlock.getProductId(), this.mCtrlBlock.getFileDescriptor(), this.mCtrlBlock.getBusNum(), this.mCtrlBlock.getDevNum(), getUSBFSName(this.mCtrlBlock));
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        if (this.mNativePtr == 0) {
            throw new UnsupportedOperationException("open failed");
        }
        this.mMsDisplay.setNativePtr(this.mNativePtr);
    }

    public synchronized void close() {
        if (this.mNativePtr != 0) {
            this.mMsDisplay.nativeRelease(this.mNativePtr);
            this.mNativePtr = 0L;
        }
        Log.v(TAG, "close:finished");
    }

    public UsbDevice getDevice() {
        USBMonitor.UsbControlBlock usbControlBlock = this.mCtrlBlock;
        if (usbControlBlock != null) {
            return usbControlBlock.getDevice();
        }
        return null;
    }

    public String getDeviceName() {
        USBMonitor.UsbControlBlock usbControlBlock = this.mCtrlBlock;
        if (usbControlBlock != null) {
            return usbControlBlock.getDeviceName();
        }
        return null;
    }

    public USBMonitor.UsbControlBlock getUsbControlBlock() {
        return this.mCtrlBlock;
    }

    private final String getUSBFSName(USBMonitor.UsbControlBlock usbControlBlock) {
        String deviceName = usbControlBlock.getDeviceName();
        String str = null;
        String[] split = !TextUtils.isEmpty(deviceName) ? deviceName.split("/") : null;
        if (split != null && split.length > 2) {
            StringBuilder sb = new StringBuilder(split[0]);
            for (int i = 1; i < split.length - 2; i++) {
                sb.append("/");
                sb.append(split[i]);
            }
            str = sb.toString();
        }
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        Log.w(TAG, "failed to get USBFS path, try to use default path:" + deviceName);
        return DEFAULT_USBFS;
    }

    public boolean isMS913X() {
        byte[] bArr = {this.mMsDisplay.xdata_read(this.mCtrlBlock.getConnection(), MotionEventCompat.ACTION_POINTER_INDEX_MASK), this.mMsDisplay.xdata_read(this.mCtrlBlock.getConnection(), 65281), this.mMsDisplay.xdata_read(this.mCtrlBlock.getConnection(), 65282)};
        if ((bArr[0] == -89 || bArr[0] == -73) && bArr[1] == 19 && bArr[2] == 10) {
            return DEBUG;
        }
        return false;
    }

    public void setUdiskdisable() {
        this.mMsDisplay.xdata_write(this.mCtrlBlock.getConnection(), 32512, (byte) 1);
    }

    public void set_video_brightness(byte b) {
        this.mMsDisplay.set_video_brightness(this.mCtrlBlock.getConnection(), b);
    }

    public void set_video_contrast(byte b) {
        this.mMsDisplay.set_video_contrast(this.mCtrlBlock.getConnection(), b);
    }

    public void set_video_saturation(byte b) {
        this.mMsDisplay.set_video_saturation(this.mCtrlBlock.getConnection(), b);
    }

    public void set_video_hue(byte b) {
        this.mMsDisplay.set_video_hue(this.mCtrlBlock.getConnection(), b);
    }

    public void video_shift_left(int i) {
        this.mMsDisplay.video_shift_left(this.mCtrlBlock.getConnection(), i);
    }

    public void video_shift_right(int i) {
        this.mMsDisplay.video_shift_right(this.mCtrlBlock.getConnection(), i);
    }

    public void video_shift_up(int i) {
        this.mMsDisplay.video_shift_up(this.mCtrlBlock.getConnection(), i);
    }

    public void video_shift_down(int i) {
        this.mMsDisplay.video_shift_down(this.mCtrlBlock.getConnection(), i);
    }

    public void video_stretch_right(float f) {
        this.mMsDisplay.video_stretch_right(this.mCtrlBlock.getConnection(), f);
    }

    public void video_shrink_left(float f) {
        this.mMsDisplay.video_shrink_left(this.mCtrlBlock.getConnection(), f);
    }

    public void video_stretch_down(float f) {
        this.mMsDisplay.video_stretch_down(this.mCtrlBlock.getConnection(), f);
    }

    public void video_shrink_up(float f) {
        this.mMsDisplay.video_shrink_up(this.mCtrlBlock.getConnection(), f);
    }

    public int get_shake_id() {
        return this.mMsDisplay.get_shake_id(this.mCtrlBlock.getConnection());
    }

    public void frame_transfer_switch(byte b) {
        this.mMsDisplay.frame_transfer_switch(this.mCtrlBlock.getConnection(), b);
    }

    public void xdata_write_switch(int i, byte b) {
        this.mMsDisplay.xdata_write_switch(this.mCtrlBlock.getConnection(), i, b);
    }

    public void xdata_write(int i, byte b) {
        this.mMsDisplay.xdata_write(this.mCtrlBlock.getConnection(), i, b);
    }

    public void frame_trigger(byte b, byte b2) {
        this.mMsDisplay.frame_trigger(this.mCtrlBlock.getConnection(), b, b2);
    }

    public byte xdata_read(int i) {
        return this.mMsDisplay.xdata_read(this.mCtrlBlock.getConnection(), i);
    }

    public int xdata_read_atomic(int i) {
        return this.mMsDisplay.xdata_read_atomic(this.mCtrlBlock.getConnection(), i);
    }

    public void xdata_modBits(int i, byte b, byte b2) {
        this.mMsDisplay.xdata_write(this.mCtrlBlock.getConnection(), i, (byte) (((byte) (b & b2)) | ((byte) (this.mMsDisplay.xdata_read(this.mCtrlBlock.getConnection(), i) & ((byte) (~b2))))));
    }

    public void xdata_setBits(int i, byte b) {
        this.mMsDisplay.xdata_write(this.mCtrlBlock.getConnection(), i, (byte) (((byte) (b & 255)) | ((byte) (this.mMsDisplay.xdata_read(this.mCtrlBlock.getConnection(), i) & ((byte) (~b))))));
    }

    public void xdata_clrBits(int i, byte b) {
        this.mMsDisplay.xdata_write(this.mCtrlBlock.getConnection(), i, (byte) (((byte) (b & 0)) | ((byte) (this.mMsDisplay.xdata_read(this.mCtrlBlock.getConnection(), i) & ((byte) (~b))))));
    }

    public void set_start_trans(byte b) {
        this.mMsDisplay.set_start_trans(this.mCtrlBlock.getConnection(), b);
    }

    public void set_transfer_mode_frame() {
        this.mMsDisplay.set_transfer_mode_frame(this.mCtrlBlock.getConnection());
    }

    public void set_video_in(int i, int i2, byte b, byte b2) {
        this.mMsDisplay.set_video_in(this.mCtrlBlock.getConnection(), i, i2, b, b2);
    }

    public void set_video_on(byte b) {
        this.mMsDisplay.set_video_on(this.mCtrlBlock.getConnection(), b);
    }

    public void set_power_on(byte b) {
        this.mMsDisplay.set_power_on(this.mCtrlBlock.getConnection(), b);
    }

    public void set_video_out(byte b, byte b2, int i, int i2) {
        this.mMsDisplay.set_video_out(this.mCtrlBlock.getConnection(), b, b2, i, i2);
    }

    public void set_video_mute(boolean z) {
        this.mMsDisplay.set_video_mute(this.mCtrlBlock.getConnection(), z);
    }

    public void frame_switch(byte b) {
        frame_transfer_switch(b);
        xdata_write_switch(61954, (byte) 1);
    }

    public byte getDisplayHotPlug() {
        return this.mMsDisplay.getDisplayHotPlug(this.mCtrlBlock.getConnection());
    }

    public int getPowerState() {
        return this.mMsDisplay.getPowerState(this.mCtrlBlock.getConnection());
    }

    public int getPowerOnSuccess() {
        return this.mMsDisplay.getPowerOnSuccess(this.mCtrlBlock.getConnection());
    }

    public void setCVBSState() {
        this.mMsDisplay.setCVBSState(this.mCtrlBlock.getConnection());
    }

    public void setAndroidHpd() {
        this.mMsDisplay.setAndroidHpd(this.mCtrlBlock.getConnection());
    }

    public int BulkOpen() {
        return this.mMsDisplay.nativeBulkOpen(this.mNativePtr, 4, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
    }

    public int BulkClose() {
        return this.mMsDisplay.nativeBulkClose(this.mNativePtr, 4);
    }

    public int getVideoPort() {
        return this.mMsDisplay.getVideoPort(this.mCtrlBlock.getConnection());
    }

    public int getSDRAMType() {
        return this.mMsDisplay.getSDRAMType(this.mCtrlBlock.getConnection());
    }

    public int getDisplayColorSpace() {
        return this.mMsDisplay.getDisplayColorSpace(this.mCtrlBlock.getConnection());
    }

    public void nativeClearhalt() {
        this.mMsDisplay.nativeClearhalt(this.mNativePtr, 4);
    }

    public int converColortransferBulk(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        long j = this.mNativePtr;
        if (j == 0) {
            return 0;
        }
        return this.mMsDisplay.nativeConvertColorBulkTransfer(j, 4, bArr, i, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, i2, i3, i4, i5, i6, i7, z);
    }

    public int ClearPool() {
        return this.mMsDisplay.nativeClearPool();
    }

    public int claimInterface(int i) {
        Log.e(TAG, "claimInterface");
        this.mCtrlBlock.claimInterface(this.mCtrlBlock.getDevice().getInterface(0), DEBUG);
        return 0;
    }

    public int releaseInterface(int i) {
        Log.e(TAG, "releaseInterface");
        this.mCtrlBlock.releaseInterface(this.mCtrlBlock.getDevice().getInterface(0));
        return 0;
    }
}
