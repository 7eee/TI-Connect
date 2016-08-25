/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class GetDeviceInfoProcessing {
    public TIStatus getStaticDeviceInfo(TIDevice tIDevice) {
        TILogger.logDetail("GetDeviceInfoProcessing", "Get Static Device Info from device <" + tIDevice + ">");
        return ConnectServerFactory.getDeviceComm().getStaticDeviceInfo(tIDevice);
    }

    public TIStatus getDynamicDeviceInfo(TIDevice tIDevice) {
        TILogger.logDetail("GetDeviceInfoProcessing", "Get Dynamic Device Info from device <" + tIDevice + ">");
        return ConnectServerFactory.getDeviceComm().getDynamicDeviceInfo(tIDevice);
    }

    public TIStatus getIDDeviceInfo(TIDevice tIDevice) {
        TILogger.logDetail("GetDeviceInfoProcessing", "Get Device ID from device <" + tIDevice + ">");
        return ConnectServerFactory.getDeviceComm().getIDDeviceInfo(tIDevice);
    }
}

