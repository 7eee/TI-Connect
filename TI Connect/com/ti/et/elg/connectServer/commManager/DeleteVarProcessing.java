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
import java.util.List;

public class DeleteVarProcessing {
    public void delete(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
        int n = 1;
        int n2 = list.size();
        for (TIVar tIVar : list) {
            if (tIProgressListener.hasBeenCanceled()) {
                tIProgressListener.reportError(tIDevice, tIVar.getDeviceFileName(), new TIStatus(62));
                break;
            }
            tIProgressListener.setOverallCompletionCount(n, n2);
            tIProgressListener.setPercentage(0);
            tIProgressListener.setCurrentFile(tIVar.getDeviceFileName());
            TIStatus tIStatus = ConnectServerFactory.getDeviceComm().deleteVar(tIDevice, tIVar, tIProgressListener);
            TILogger.logDetail(DeleteVarProcessing.class.getName(), "Deleted var<" + tIVar.getDeviceFileName() + "> from device<" + tIDevice + ">");
            if (tIStatus.isFailure()) {
                tIProgressListener.reportError(tIDevice, tIVar.getDeviceFileName(), tIStatus);
                if (tIStatus.isTerminalFailure()) break;
            }
            tIProgressListener.setPercentage(100);
            ++n;
        }
    }
}

