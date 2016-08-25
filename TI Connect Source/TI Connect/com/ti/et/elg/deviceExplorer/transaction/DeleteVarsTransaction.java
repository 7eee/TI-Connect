/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.transaction;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.DeviceTransactionsListener;
import com.ti.et.elg.deviceExplorer.TableViewData;
import com.ti.et.elg.deviceExplorer.exports.VarsTransactionOverlayInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.deviceExplorer.transaction.TIDeviceDirectoryRefreshTransaction;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import java.util.ArrayList;
import java.util.List;

public class DeleteVarsTransaction
extends Transaction<Object, TIDevice> {
    private List<TableViewData> varList = null;
    private TIDevice tiDevice;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();
    private TransactionManager transactionManager;

    public DeleteVarsTransaction(List<TableViewData> list, TIDevice tIDevice, TransactionManager transactionManager) {
        this.varList = list;
        this.tiDevice = tIDevice;
        this.transactionManager = transactionManager;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.DELETING);
        this.listener.setTransaction(this);
    }

    @Override
    protected Object doPreStep() {
        DeviceExplorerFactory.loadOverlayPane(this.tiDevice, IDOverlappingPanelsType.DELETING);
        ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(this.tiDevice)).setTransaction(this);
        return null;
    }

    @Override
    protected TIDevice doInBackground(Object object) {
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>(this.varList.size());
        for (TableViewData tableViewData : this.varList) {
            arrayList.add(tableViewData.getTiVar());
        }
        this.commManager.deleteVar(this.tiDevice, arrayList, this.getListener());
        return this.tiDevice;
    }

    @Override
    protected void doPostStep(TIDevice tIDevice) {
        this.transactionManager.addTransaction(tIDevice, new TIDeviceDirectoryRefreshTransaction(tIDevice), null);
        DeviceExplorerFactory.unloadOverlayPane(this.tiDevice);
    }
}

