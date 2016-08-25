/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;

public class SetOverwriteTimeout {
    public TIStatus setOverwriteTimeout(TIDevice tIDevice, int n) {
        return ConnectServerFactory.getDeviceComm().setOverwriteTimeout(tIDevice, n);
    }
}

