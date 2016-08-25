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
import com.ti.et.elg.deviceExplorer.exports.OSOverlayControllerInterface;
import com.ti.et.elg.deviceExplorer.exports.OverlayForDeviceInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;

public class TransferOSTransaction
extends Transaction<Object, TIDevice> {
    private TIVar osVar = null;
    private TIDevice tiDevice;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();

    public TransferOSTransaction(TIVar tIVar, TIDevice tIDevice, TransactionManager transactionManager) {
        this.tiDevice = tIDevice;
        this.osVar = tIVar;
        this.listener = new DeviceTransactionsListener(tIDevice, TransactionType.SEND_OS);
        this.listener.setTransaction(this);
    }

    @Override
    protected Object doPreStep() {
        DeviceExplorerFactory.unloadOverlayPane(this.tiDevice);
        DeviceExplorerFactory.loadOverlayPane(this.tiDevice, IDOverlappingPanelsType.SENDING_OS);
        OverlayForDeviceInterface overlayForDeviceInterface = DeviceExplorerFactory.getOverlayPane(this.tiDevice);
        if (overlayForDeviceInterface != null && overlayForDeviceInterface instanceof OSOverlayControllerInterface) {
            ((OSOverlayControllerInterface)((Object)overlayForDeviceInterface)).setOSVersion(this.osVar.getRevisionNumberMajor(), this.osVar.getRevisionNumberMinor());
        }
        return null;
    }

    @Override
    protected TIDevice doInBackground(Object object) {
        this.commManager.sendOSUpdate(this.tiDevice, this.osVar, this.getListener());
        return this.tiDevice;
    }

    @Override
    protected void doPostStep(TIDevice tIDevice) {
        if (tIDevice != null && tIDevice.getHardwareVersion() <= 6) {
            if (!this.getListener().getStatus().isFailure()) {
                OverlayForDeviceInterface overlayForDeviceInterface = DeviceExplorerFactory.getOverlayPane(tIDevice);
                if (overlayForDeviceInterface != null && overlayForDeviceInterface instanceof OSOverlayControllerInterface) {
                    ((OSOverlayControllerInterface)((Object)overlayForDeviceInterface)).setValidationLabel();
                }
            } else if (this.getListener().getStatus().getStatusCode() == 54) {
                if (tIDevice != null) {
                    tIDevice.setUnderOSUpdate(false);
                }
                DeviceExplorerFactory.unloadOverlayPane(tIDevice);
            }
        } else {
            if (tIDevice != null) {
                tIDevice.setUnderOSUpdate(false);
            }
            DeviceExplorerFactory.unloadOverlayPane(tIDevice);
        }
    }
}

