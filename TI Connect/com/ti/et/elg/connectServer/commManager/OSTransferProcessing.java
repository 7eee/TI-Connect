/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.SendVarProcessing;
import com.ti.et.elg.connectServer.commManager.TransferVarProcessing;
import com.ti.et.elg.connectServer.exports.DeviceCommInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.io.File;

class OSTransferProcessing
extends TransferVarProcessing {
    OSTransferProcessing() {
    }

    @Override
    protected int performSpecificsForOneVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener, int n) {
        int n2 = 1;
        TIStatus tIStatus = new TIStatus(0);
        DeviceCommInterface deviceCommInterface = ConnectServerFactory.getDeviceComm();
        tIStatus = deviceCommInterface.sendOSUpdate(tIDevice, tIVar, tIProgressListener);
        if (tIStatus.isFailure()) {
            String string = this.getNonNullName(tIVar);
            if (tIStatus.getStatusCode() == 54) {
                string = "" + tIVar.getRevisionNumberMajor() + "." + tIVar.getRevisionNumberMinor();
            }
            tIProgressListener.reportError(tIDevice, string, tIStatus);
            if (tIStatus.isTerminalFailure()) {
                return 4;
            }
        } else {
            TILogger.logDetail(SendVarProcessing.class.getName(), "Sent OS<" + (tIVar.getHostFile() != null ? tIVar.getHostFile().getName() : "In Memory Only") + "> to device<" + tIDevice + "> as var<" + tIVar.getDeviceFileName() + ">");
        }
        return n2;
    }
}

