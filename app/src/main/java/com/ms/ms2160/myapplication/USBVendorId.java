package com.ms.ms2160.myapplication;

import android.util.SparseArray;

/* loaded from: classes.dex */
public class USBVendorId {
    private static final SparseArray<String> IDS;

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        IDS = sparseArray;
        sparseArray.put(21325, "MacroSilicon");
        IDS.put(13407, "MacroSilicon");
    }

    public static String vendorName(int i) {
        return IDS.get(i);
    }
}
