/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.transaction;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.DeviceTransactionsListener;
import com.ti.et.elg.deviceExplorer.exports.TIOpenHandlerInterface;
import com.ti.et.elg.deviceExplorer.exports.VarsTransactionOverlayInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import com.ti.et.utils.logger.TILogger;
import java.util.ArrayList;
import java.util.List;

public class OpenFromDeviceTransaction
extends Transaction<Object, List<TIVar>> {
    private TIVar tiVar = null;
    private TIDevice srcDevice;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();
    private static final String LOG_TAG = OpenFromDeviceTransaction.class.getSimpleName();
    private TIOpenHandlerInterface openHandler;

    public OpenFromDeviceTransaction(TIDevice tIDevice, TIVar tIVar, TransactionManager transactionManager) {
        this.srcDevice = tIDevice;
        this.tiVar = tIVar;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.DATA_EDITING);
        this.listener.setTransaction(this);
    }

    @Override
    protected Object doPreStep() {
        TIOpenHandlerInterface tIOpenHandlerInterface = DeviceExplorerFactory.getDeviceExplorer().getOpenHandlerForType(this.tiVar.getVarType());
        if (tIOpenHandlerInterface == null) {
            this.getListener().reportError(this.srcDevice, this.tiVar.getHostFileNameFromDeviceFileName(), new TIStatus(111));
        } else {
            this.setVarOpenHandler(tIOpenHandlerInterface);
            DeviceExplorerFactory.loadOverlayPane(this.srcDevice, IDOverlappingPanelsType.SENDING);
            ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(this.srcDevice)).setTransaction(this);
        }
        return null;
    }

    @Override
    protected List<TIVar> doInBackground(Object object) {
        if (!this.getListener().getStatus().isFailure()) {
            ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
            TIVar tIVar = this.tiVar.clone();
            arrayList.add(tIVar);
            this.commManager.getVar(this.srcDevice, arrayList, this.getListener());
            return arrayList;
        }
        return null;
    }

    @Override
    protected void doPostStep(List<TIVar> list) {
        TIVar tIVar;
        if (!(this.getListener().getStatus().isFailure() || (tIVar = list.get(0)).getVarType() != 5 && tIVar.getVarType() != 6)) {
            TILogger.logDetail(LOG_TAG, "Opening program:" + tIVar.getHostFileNameFromDeviceFileName());
            DeviceExplorerFactory.getDeviceExplorer().getWorkSpaceManager().switchToWorkspace(WorkspaceManagerInterface.WorkspaceType.PROGRAM_EDITOR);
            TIOpenHandlerInterface tIOpenHandlerInterface = this.getVarOpenHandler();
            if (tIOpenHandlerInterface != null) {
                tIOpenHandlerInterface.openVar(tIVar);
            }
        }
        DeviceExplorerFactory.unloadOverlayPane(this.srcDevice);
    }

    private void setVarOpenHandler(TIOpenHandlerInterface tIOpenHandlerInterface) {
        this.openHandler = tIOpenHandlerInterface;
    }

    private TIOpenHandlerInterface getVarOpenHandler() {
        return this.openHandler;
    }
}

