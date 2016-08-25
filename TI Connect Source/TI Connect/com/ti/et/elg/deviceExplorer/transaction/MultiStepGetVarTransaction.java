/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 */
package com.ti.et.elg.deviceExplorer.transaction;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.DeviceTransactionsListener;
import com.ti.et.elg.deviceExplorer.exports.VarsTransactionOverlayInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

public class MultiStepGetVarTransaction
extends Transaction<Object, Object> {
    private List<TIVar> listOfOneVar = null;
    private TIDevice tiDevice;
    private int iteration = 0;

    public MultiStepGetVarTransaction(TIDevice tIDevice, int n) {
        this.tiDevice = tIDevice;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.SENDING);
        this.listener.setTransaction(this);
        this.listener.setItemsInTotalOverride(n);
        this.listener.setItemBeingDoneOverride(this.iteration + 1);
    }

    public TIDevice getTIDevice() {
        return this.tiDevice;
    }

    @Override
    protected Object doPreStep() {
        if (this.iteration == 1) {
            DeviceExplorerFactory.loadOverlayPane(this.tiDevice, IDOverlappingPanelsType.SENDING);
            ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(this.tiDevice)).setTransaction(this);
        }
        return null;
    }

    @Override
    protected Object doInBackground(Object object) {
        ConnectServerFactory.getCommManager().getVar(this.tiDevice, this.listOfOneVar, this.getListener());
        return null;
    }

    @Override
    protected void doPostStep(Object object) {
    }

    @Override
    public boolean multiStepTransaction() {
        return true;
    }

    public void setNextVarToGet(TIVar tIVar) {
        this.listOfOneVar = new ArrayList<TIVar>();
        this.listOfOneVar.add(tIVar);
        this.listener.setItemBeingDoneOverride(this.iteration + 1);
        ++this.iteration;
    }

    @Override
    public boolean shouldTransactionProgressBeReported() {
        return this.iteration == 0;
    }

    public void makeDone() {
        this.getListener().setDone();
        super.setTransactionState(2);
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                DeviceExplorerFactory.unloadOverlayPane(MultiStepGetVarTransaction.this.tiDevice);
            }
        });
    }

    public void makeActive() {
        super.setTransactionState(1);
    }

}

