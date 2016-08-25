/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.transaction;

import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.exports.DeviceContentsInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;

public class TIDeviceDirectoryRefreshTransaction
extends Transaction<TIDevice, TIDirectory> {
    private TIDevice tiDevice = null;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();
    private DeviceContentsInterface deviceContentsTable = DeviceExplorerFactory.getContentTable();

    public TIDeviceDirectoryRefreshTransaction(TIDevice tIDevice) {
        this.tiDevice = tIDevice;
        this.listener = new TransactionListener(this);
        this.listener.setTransaction(this);
    }

    @Override
    protected TIDevice doPreStep() {
        DeviceExplorerFactory.loadOverlayPane(this.tiDevice, IDOverlappingPanelsType.REFRESH);
        return this.tiDevice;
    }

    @Override
    protected TIDirectory doInBackground(TIDevice tIDevice) {
        if (tIDevice != null) {
            TIDirectory tIDirectory = this.commManager.createTIDirectory();
            this.commManager.getDirectory(tIDevice, tIDirectory, this.getListener());
            this.commManager.getDeviceDynamicInfo(tIDevice);
            return tIDirectory;
        }
        return null;
    }

    @Override
    protected void doPostStep(TIDirectory tIDirectory) {
        if (!this.getListener().getStatus().isFailure() && tIDirectory != null) {
            this.deviceContentsTable.updateDirectory(this.tiDevice, tIDirectory);
        }
        DeviceExplorerFactory.unloadOverlayPane(this.tiDevice);
    }

    @Override
    public boolean shouldTransactionProgressBeReported() {
        return false;
    }

    @Override
    public boolean compressBackToBackTransactionsOfThisType() {
        return true;
    }
}

