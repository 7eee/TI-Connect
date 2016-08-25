/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.TransferVarProcessing;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.io.File;

class RetrieveVarProcessing
extends TransferVarProcessing {
    RetrieveVarProcessing() {
    }

    @Override
    protected int performSpecificsForOneVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener, int n) {
        int n2 = 1;
        if (tIVar.getHostFile() != null && tIVar.getHostFile().exists()) {
            if (n == -1) {
                n2 = tIProgressListener.confirm(this.getNonNullName(tIVar));
                if (n2 == 4 || n2 == 2 || n2 == 0) {
                    if (n2 == 4) {
                        tIProgressListener.reportError(tIDevice, this.getNonNullName(tIVar), new TIStatus(62));
                    }
                    return n2;
                }
            } else if (n == 2) {
                return 2;
            }
        }
        tIProgressListener.setCurrentFile(this.getNonNullName(tIVar));
        TIStatus tIStatus = ConnectServerFactory.getDeviceComm().getVar(tIDevice, tIVar, tIProgressListener);
        if (!tIStatus.isFailure() && tIVar.getHostFile() != null) {
            byte[] arrby = tIVar.frameDataWithDataFormat();
            tIStatus = tIVar.saveToDisk(arrby);
            arrby = null;
        }
        if (tIStatus.isFailure()) {
            if (tIStatus.getStatusCode() == -10) {
                tIProgressListener.reportError(tIDevice, tIVar.getHostFile() != null ? tIVar.getHostFile().getParent() : this.getNonNullName(tIVar), tIStatus);
            } else {
                tIProgressListener.reportError(tIDevice, this.getNonNullName(tIVar), tIStatus);
            }
            if (tIStatus.isTerminalFailure()) {
                return 4;
            }
        } else {
            TILogger.logDetail(RetrieveVarProcessing.class.getName(), "Retrieved var<" + tIVar.getDeviceFileName() + "> from device<" + tIDevice + "> to file<" + (tIVar.getHostFile() != null ? tIVar.getHostFile().getName() : "In Memory Only") + ">");
        }
        return n2;
    }
}

