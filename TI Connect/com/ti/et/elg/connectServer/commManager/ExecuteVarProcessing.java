/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class ExecuteVarProcessing {
    public TIStatus executeVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        TILogger.logDetail("ExecuteVarProcessing", "Execute var named <" + tIVar.getDeviceFileName() + "> on device <" + tIDevice + ">");
        return ConnectServerFactory.getDeviceComm().executeVar(tIDevice, tIVar, tIProgressListener);
    }
}

