/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.RetrieveVarProcessing;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class RetrieveScreenProcessing {
    public void transfer(TIDevice tIDevice, TIImage tIImage, TIProgressListener tIProgressListener) {
        int n = 1;
        int n2 = 1;
        int n3 = 1;
        tIProgressListener.setOverallCompletionCount(n2, n3);
        TIStatus tIStatus = ConnectServerFactory.getDeviceComm().getScreen(tIDevice, tIImage, tIProgressListener);
        if (tIStatus.isFailure()) {
            tIProgressListener.reportError(tIDevice, null, tIStatus);
        } else {
            TILogger.logDetail(RetrieveVarProcessing.class.getName(), "Retrieved Screen Capture from device<" + tIDevice + ">");
        }
        if (n == 4) {
            tIProgressListener.reportError(tIDevice, "GetScreensnapshot", new TIStatus(62));
        }
        tIProgressListener.setPercentage(100);
        ++n2;
    }
}

