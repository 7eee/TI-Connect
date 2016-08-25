/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.TransferVarProcessing;
import com.ti.et.elg.connectServer.exports.DeviceCommInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.io.File;

class SendVarProcessing
extends TransferVarProcessing {
    SendVarProcessing() {
    }

    @Override
    protected int performSpecificsForOneVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener, int n) {
        int n2 = 1;
        TIStatus tIStatus = new TIStatus(0);
        if (tIVar.getHostFile() != null && tIVar.getData() == null) {
            tIStatus = tIVar.loadFromDisk();
        } else if (tIVar.getData() == null) {
            tIStatus = new TIStatus(-5);
        }
        if (!tIStatus.isFailure()) {
            DeviceCommInterface deviceCommInterface = ConnectServerFactory.getDeviceComm();
            tIStatus = new TIStatus(0);
            if (!tIStatus.isFailure()) {
                tIProgressListener.setCurrentFile(this.getNonNullName(tIVar));
                if (deviceCommInterface.isAlive()) {
                    tIStatus = deviceCommInterface.sendVar(tIDevice, tIVar, false, tIProgressListener);
                    if (tIStatus.getStatusCode() == 107) {
                        if (n == -1) {
                            n2 = tIProgressListener.confirm(tIVar.getDeviceFileName());
                            if (n2 == 4 || n2 == 2 || n2 == 0) {
                                if (n2 == 4) {
                                    tIProgressListener.reportError(tIDevice, this.getNonNullName(tIVar), new TIStatus(62));
                                }
                                return n2;
                            }
                        } else if (n == 2) {
                            return 2;
                        }
                        tIStatus = deviceCommInterface.sendVar(tIDevice, tIVar, true, tIProgressListener);
                    }
                } else {
                    return 4;
                }
            }
        }
        if (tIStatus.isFailure()) {
            tIProgressListener.reportError(tIDevice, this.getNonNullName(tIVar), tIStatus);
            if (tIStatus.isTerminalFailure()) {
                return 4;
            }
        } else {
            TILogger.logDetail(SendVarProcessing.class.getName(), "Sent file<" + (tIVar.getHostFile() != null ? tIVar.getHostFile().getName() : "In Memory Only") + "> to device<" + tIDevice + "> as var<" + tIVar.getDeviceFileName() + ">");
        }
        return n2;
    }
}

