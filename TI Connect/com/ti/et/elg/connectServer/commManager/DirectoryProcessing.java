/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class DirectoryProcessing {
    public void populate(TIDevice tIDevice, TIDirectory tIDirectory, TIProgressListener tIProgressListener) {
        TILogger.logDetail("DirectoryProcessing", "Get directory from device <" + tIDevice + ">");
        ConnectServerFactory.getDeviceComm().getDirectory(tIDevice, tIDirectory, tIProgressListener);
    }
}

