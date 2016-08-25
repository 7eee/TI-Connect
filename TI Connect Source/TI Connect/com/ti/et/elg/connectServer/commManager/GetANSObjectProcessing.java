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

class GetANSObjectProcessing
extends TransferVarProcessing {
    GetANSObjectProcessing() {
    }

    @Override
    protected int performSpecificsForOneVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener, int n) {
        int n2 = 1;
        TIStatus tIStatus = new TIStatus(0);
        DeviceCommInterface deviceCommInterface = ConnectServerFactory.getDeviceComm();
        tIStatus = deviceCommInterface.getANSVariable(tIDevice, tIVar, tIProgressListener);
        if (tIStatus.isFailure()) {
            tIProgressListener.reportError(tIDevice, this.getNonNullName(tIVar), tIStatus);
            if (tIStatus.isTerminalFailure()) {
                return 4;
            }
        } else {
            TILogger.logDetail(GetANSObjectProcessing.class.getName(), "Retrieved var<" + tIVar.getDeviceFileName() + "> from device<" + tIDevice + ">");
        }
        return n2;
    }
}

