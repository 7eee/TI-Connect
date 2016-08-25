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
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetVarsTransaction
extends Transaction<Object, TIDevice> {
    private List<TableViewData> varList = null;
    private TIDevice tiDevice;
    private File destFolder;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();

    public GetVarsTransaction(List<TableViewData> list, TIDevice tIDevice, File file, TransactionManager transactionManager) {
        this.varList = list;
        this.destFolder = file;
        this.tiDevice = tIDevice;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.SENDING);
        this.listener.setTransaction(this);
    }

    @Override
    protected Object doPreStep() {
        DeviceExplorerFactory.loadOverlayPane(this.tiDevice, IDOverlappingPanelsType.SENDING);
        ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(this.tiDevice)).setTransaction(this);
        return null;
    }

    @Override
    protected TIDevice doInBackground(Object object) {
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>(this.varList.size());
        for (TableViewData tableViewData : this.varList) {
            TIVar tIVar = tableViewData.getTiVar().clone();
            if (null == tableViewData.getTiVar().getHostFile()) {
                File file = new File(this.destFolder, tIVar.getHostFileNameFromDeviceFileName());
                tIVar.setHostFile(file);
            }
            arrayList.add(tIVar);
        }
        this.commManager.getVar(this.tiDevice, arrayList, this.getListener());
        return this.tiDevice;
    }

    @Override
    protected void doPostStep(TIDevice tIDevice) {
        DeviceExplorerFactory.unloadOverlayPane(tIDevice);
        for (TableViewData tableViewData : this.varList) {
            tableViewData.getTiVar().setHostFile(null);
        }
    }
}

