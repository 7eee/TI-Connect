/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIImage;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class TIImageImpl
implements TIImage {
    public static int typeTIFF = 1414088262;
    public static int typeTI84PlusCImage = -9175270;
    private byte[] imageData;
    private int pixelsWide = 320;
    private int pixelsHeight = 240;
    private int productID = -1;
    private int hardwareVersion = -1;

    public TIImageImpl(byte[] arrby) {
        this.imageData = arrby;
    }

    public TIImageImpl(int n, int n2) {
        this.pixelsWide = n;
        this.pixelsHeight = n2;
    }

    @Override
    public int[] getImageDataAsARGB() {
        if (this.imageData == null || this.imageData.length == 0) {
            return null;
        }
        IntBuffer intBuffer = IntBuffer.allocate(this.pixelsWide * this.pixelsHeight);
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.imageData.length);
        byteBuffer.put(this.imageData);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.rewind();
        for (int i = this.pixelsHeight - 1; i >= 0; --i) {
            for (int j = 0; j < this.pixelsWide; ++j) {
                short s;
                short s2 = s = byteBuffer.getShort();
                byte by = (byte)(s2 >> 11 & 31);
                byte by2 = (byte)(s2 >> 5 & 63);
                byte by3 = (byte)(s2 & 31);
                by = (byte)(by << 3 | by >> 2);
                by2 = (byte)(by2 << 2 | by2 >> 4);
                by3 = (byte)(by3 << 3 | by3 >> 2);
                intBuffer.put(-16777216 | by << 16 & 16711680 | by2 << 8 & 65280 | by3 & 255);
            }
        }
        return intBuffer.array();
    }

    @Override
    public byte[] getImageData() {
        return this.imageData;
    }

    @Override
    public void setImageData(byte[] arrby) {
        this.imageData = arrby;
    }

    @Override
    public void setPixelsWide(short s) {
        this.pixelsWide = s;
    }

    @Override
    public int getPixelsWide() {
        return this.pixelsWide;
    }

    @Override
    public void setPixelsHeight(short s) {
        this.pixelsHeight = s;
    }

    @Override
    public int getPixelsHeight() {
        return this.pixelsHeight;
    }

    public void setDeviceProductID(int n) {
        this.productID = n;
    }

    public void setDeviceHardwareVersion(int n) {
        this.hardwareVersion = n;
    }

    @Override
    public int getDeviceProductID() {
        return this.productID;
    }

    @Override
    public int getDeviceHardwareVersion() {
        return this.hardwareVersion;
    }
}

