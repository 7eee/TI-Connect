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
import com.ti.et.elg.deviceExplorer.transaction.SendVarsTransaction;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import java.util.ArrayList;
import java.util.List;

public class GetThenSendVarsTransaction
extends Transaction<Object, List<TIVar>> {
    private TIVar[] tiVars = null;
    private TIDevice srcDevice;
    private List<TIDevice> destDevices;
    private TransactionManager transactionManager = null;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();

    public GetThenSendVarsTransaction(TIDevice tIDevice, TIVar[] arrtIVar, List<TIDevice> list, TransactionManager transactionManager) {
        this.srcDevice = tIDevice;
        this.tiVars = arrtIVar;
        this.destDevices = list;
        this.transactionManager = transactionManager;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.SENDING);
        this.listener.setTransaction(this);
    }

    @Override
    protected Object doPreStep() {
        DeviceExplorerFactory.loadOverlayPane(this.srcDevice, IDOverlappingPanelsType.SENDING);
        ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(this.srcDevice)).setTransaction(this);
        return null;
    }

    @Override
    protected List<TIVar> doInBackground(Object object) {
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>(this.tiVars.length);
        for (TIVar tIVar : this.tiVars) {
            TIVar tIVar2 = tIVar.clone();
            arrayList.add(tIVar2);
        }
        this.commManager.getVar(this.srcDevice, arrayList, this.getListener());
        return arrayList;
    }

    @Override
    protected void doPostStep(List<TIVar> list) {
        if (!this.getListener().getStatus().isFailure()) {
            TransactionGroup transactionGroup = new TransactionGroup();
            for (TIDevice tIDevice : this.destDevices) {
                if (tIDevice == this.srcDevice || !tIDevice.isOSpresent()) continue;
                SendVarsTransaction sendVarsTransaction = new SendVarsTransaction(list, tIDevice, this.transactionManager);
                sendVarsTransaction.getListener().setConfirmOverride(this.listener.getConfirmOverride());
                this.transactionManager.addTransaction(tIDevice, sendVarsTransaction, transactionGroup);
            }
        }
        DeviceExplorerFactory.unloadOverlayPane(this.srcDevice);
    }
}

