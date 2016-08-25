/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import java.io.File;
import java.util.List;

abstract class TransferVarProcessing {
    TransferVarProcessing() {
    }

    public void transfer(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
        int n = 1;
        int n2 = list.size();
        int n3 = -1;
        for (TIVar tIVar : list) {
            if (tIProgressListener.hasBeenCanceled()) {
                tIProgressListener.reportError(tIDevice, this.getNonNullName(tIVar), new TIStatus(62));
                break;
            }
            if (tIDevice.isDisconnected()) {
                tIProgressListener.reportError(tIDevice, this.getNonNullName(tIVar), new TIStatus(5));
                break;
            }
            tIProgressListener.setOverallCompletionCount(n, n2);
            tIProgressListener.setPercentage(0);
            tIProgressListener.setCurrentFile(this.getNonNullName(tIVar));
            int n4 = this.performSpecificsForOneVar(tIDevice, tIVar, tIProgressListener, n3);
            if (n4 == 4) break;
            if (n4 == 3 || n4 == 2) {
                n3 = n4;
            }
            tIProgressListener.setPercentage(100);
            ++n;
        }
    }

    protected String getNonNullName(TIVar tIVar) {
        if (tIVar.isMultiVarFile()) {
            return tIVar.getHostFile() != null ? tIVar.getHostFile().getName() + " - " + tIVar.getDeviceFileName() : tIVar.getDeviceFileName();
        }
        return tIVar.getHostFile() != null ? tIVar.getHostFile().getName() : tIVar.getDeviceFileName();
    }

    protected abstract int performSpecificsForOneVar(TIDevice var1, TIVar var2, TIProgressListener var3, int var4);
}

