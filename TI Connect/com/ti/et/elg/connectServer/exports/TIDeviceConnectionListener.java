/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.TIDevice;

public interface TIDeviceConnectionListener {
    public void deviceConnected(TIDevice var1);

    public void deviceDisconnected(TIDevice var1);
}

