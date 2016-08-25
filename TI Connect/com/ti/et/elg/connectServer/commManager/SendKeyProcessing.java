/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.DeviceCommInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class SendKeyProcessing {
    public void send(TIDevice tIDevice, int n, TIProgressListener tIProgressListener) {
        TIStatus tIStatus = new TIStatus(0);
        if (!tIStatus.isFailure()) {
            DeviceCommInterface deviceCommInterface = ConnectServerFactory.getDeviceComm();
            tIStatus = deviceCommInterface.sendKey(tIDevice, n, tIProgressListener);
        }
        if (tIStatus.isFailure()) {
            tIProgressListener.reportError(tIDevice, "" + n, tIStatus);
        } else {
            TILogger.logDetail(SendKeyProcessing.class.getName(), "Key " + n + " sent to device>");
        }
    }
}

