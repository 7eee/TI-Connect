/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;

public class SingleQueryProcessing {
    public TIStatus singleQuery(TIDevice tIDevice, int n) {
        return ConnectServerFactory.getDeviceComm().singleQuery(tIDevice, n, null);
    }

    public TIStatus singleQuery(TIDevice tIDevice, int n, DeviceErrorHolder deviceErrorHolder) {
        return ConnectServerFactory.getDeviceComm().singleQuery(tIDevice, n, deviceErrorHolder);
    }
}

