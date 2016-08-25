/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class SetDeviceInfoProcessing {
    public TIStatus setIDListValue(TIDevice tIDevice, byte[] arrby) {
        TILogger.logInfo("SetDeviceInfoProcessing", "Set the IDList Value for device: " + tIDevice.getNodeId());
        return ConnectServerFactory.getDeviceComm().setIDListValue(tIDevice, arrby);
    }
}

