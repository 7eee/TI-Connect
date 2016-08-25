/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.transaction;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.DeviceTransactionsListener;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.screenCapture.exports.ScreenContentsInterface;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;

public class GetTIImageScreenCaptureTransaction
extends Transaction<TIDevice, TIImage> {
    private TIDevice tiDevice = null;
    private CommManagerInterface commManager = ConnectServerFactory.getCommManager();
    private ScreenContentsInterface screenCaptureContainer = ScreenCaptureFactory.getScreenCaptureContainer();

    public GetTIImageScreenCaptureTransaction(TIDevice tIDevice) {
        this.tiDevice = tIDevice;
        this.listener = new DeviceTransactionsListener(this.tiDevice, TransactionType.CAPTURING_SCREEN);
        this.listener.setTransaction(this);
    }

    @Override
    protected TIDevice doPreStep() {
        DeviceExplorerFactory.loadOverlayPane(this.tiDevice, IDOverlappingPanelsType.CAPTURING_SCREEN);
        return this.tiDevice;
    }

    @Override
    protected TIImage doInBackground(TIDevice tIDevice) {
        if (tIDevice != null) {
            TIImage tIImage = this.commManager.createTIImage(tIDevice);
            this.commManager.getScreenCapture(tIDevice, tIImage, this.getListener());
            return tIImage;
        }
        return null;
    }

    @Override
    protected void doPostStep(TIImage tIImage) {
        if (!this.getListener().getStatus().isFailure() && tIImage != null) {
            this.screenCaptureContainer.createScreenCaptureItem(tIImage);
        }
        DeviceExplorerFactory.unloadOverlayPane(this.tiDevice);
    }
}

