package com.example.msdisplay;

/* loaded from: classes.dex */
public class TransferInfo {
    byte[] buffer;
    int index;
    int indexInterface;
    int length;
    int request;
    int requestType;
    int timeout;
    int value;

    public TransferInfo(int i, int i2, int i3, int i4, int i5, byte[] bArr, int i6, int i7) {
        this.indexInterface = i;
        this.requestType = i2;
        this.request = i3;
        this.value = i4;
        this.index = i5;
        this.buffer = bArr;
        this.length = i6;
        this.timeout = i7;
    }

    public int getIndexInterface() {
        return this.indexInterface;
    }

    public void setIndexInterface(int i) {
        this.indexInterface = i;
    }

    public int getRequestType() {
        return this.requestType;
    }

    public void setRequestType(int i) {
        this.requestType = i;
    }

    public int getRequest() {
        return this.request;
    }

    public void setRequest(int i) {
        this.request = i;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public void setBuffer(byte[] bArr) {
        this.buffer = bArr;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int i) {
        this.length = i;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int i) {
        this.timeout = i;
    }
}
