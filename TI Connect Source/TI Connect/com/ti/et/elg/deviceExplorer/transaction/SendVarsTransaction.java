/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.transaction;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.DeviceTransactionsListener;
import com.ti.et.elg.deviceExplorer.exports.VarsTransactionOverlayInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.deviceExplorer.transaction.TIDeviceDirectoryRefreshTransaction;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import java.util.List;

public class SendVarsTransaction
extends Transaction<Object, TIDevice> {
    private List<TIVar> tiVars = null;
    private TIDevice tiDevice;
    private TransactionManager transactionManager = null;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();

    public SendVarsTransaction(List<TIVar> list, TIDevice tIDevice, TransactionManager transactionManager) {
        this.tiDevice = tIDevice;
        this.transactionManager = transactionManager;
        this.tiVars = list;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.RECEIVING);
        this.listener.setTransaction(this);
    }

    @Override
    protected Object doPreStep() {
        DeviceExplorerFactory.loadOverlayPane(this.tiDevice, IDOverlappingPanelsType.RECEIVING);
        ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(this.tiDevice)).setTransaction(this);
        return null;
    }

    @Override
    protected TIDevice doInBackground(Object object) {
        this.commManager.sendVar(this.tiDevice, this.tiVars, this.getListener());
        return this.tiDevice;
    }

    @Override
    protected void doPostStep(TIDevice tIDevice) {
        if (!this.getListener().getStatus().isFailure() || this.getListener().getStatus().getStatusCode() != 13) {
            this.transactionManager.addTransaction(tIDevice, new TIDeviceDirectoryRefreshTransaction(tIDevice), null);
        }
        DeviceExplorerFactory.unloadOverlayPane(this.tiDevice);
    }
}

