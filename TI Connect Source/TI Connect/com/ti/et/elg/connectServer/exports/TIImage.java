/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

public interface TIImage {
    public static final int COLOR_SCREEN_WIDTH = 320;
    public static final int COLOR_SCREEN_HEIGHT = 240;
    public static final int COLOR_SCREEN_BITSPERPIXEL = 16;
    public static final int BW_SCREEN_WIDTH = 192;
    public static final int BW_SCREEN_HEIGHT = 128;
    public static final int BW_SCREEN_BITSPERPIXEL = 1;

    public byte[] getImageData();

    public int[] getImageDataAsARGB();

    public void setImageData(byte[] var1);

    public void setPixelsWide(short var1);

    public int getPixelsWide();

    public void setPixelsHeight(short var1);

    public int getPixelsHeight();

    public int getDeviceProductID();

    public int getDeviceHardwareVersion();
}

