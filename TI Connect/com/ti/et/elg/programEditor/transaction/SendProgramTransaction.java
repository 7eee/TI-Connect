/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.programEditor.transaction;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.DeviceTransactionsListener;
import com.ti.et.elg.deviceExplorer.exports.VarsTransactionOverlayInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import java.io.File;
import java.util.List;

public class SendProgramTransaction
extends Transaction<Object, TIDevice> {
    private List<TIVar> tiVars = null;
    private TIDevice tiDevice;
    private TransactionManager transactionManager = null;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();

    public SendProgramTransaction() {
    }

    public SendProgramTransaction(List<TIVar> list, TIDevice tIDevice, TransactionManager transactionManager) {
        this.tiDevice = tIDevice;
        this.setTransactionManager(transactionManager);
        this.tiVars = list;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.RECEIVING);
        this.listener.setTransaction(this);
    }

    @Override
    protected Object doPreStep() {
        ProgramEditorFactory.loadOverlayPane(this.tiDevice);
        ((VarsTransactionOverlayInterface)((Object)ProgramEditorFactory.getOverlayPane(this.tiDevice))).setItemsCount(1, 1);
        String string = this.tiVars.get(0).getHostFile() != null ? this.tiVars.get(0).getHostFile().getName() : this.tiVars.get(0).getDeviceFileName();
        ((VarsTransactionOverlayInterface)((Object)ProgramEditorFactory.getOverlayPane(this.tiDevice))).setCurrentFileName(string);
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
        ProgramEditorFactory.unloadOverlayPane(this.tiDevice);
        DeviceExplorerFactory.unloadOverlayPane(this.tiDevice);
    }

    @Override
    public void setParameters(List<TIVar> list, TIDevice tIDevice, TransactionManager transactionManager) {
        this.tiDevice = tIDevice;
        this.setTransactionManager(transactionManager);
        this.tiVars = list;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.RECEIVING);
        this.listener.setTransaction(this);
    }

    public TransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}

