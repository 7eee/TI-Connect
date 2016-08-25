/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.StringProperty
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.deviceList.TIDeviceHolder;
import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.prompt.PromptFromBackgroundThread;
import com.ti.et.elg.commonUISupport.prompt.PromptResult;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.exports.VarsTransactionOverlayInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionProgressData;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public class DeviceTransactionsListener
extends TransactionListener {
    private TIDeviceHolder deviceHolder = null;
    private boolean didErrorOccur = false;
    private TransactionType transactionType = TransactionType.NONE;

    public DeviceTransactionsListener(TIDevice tIDevice) {
        this(tIDevice, TransactionType.NONE);
    }

    public DeviceTransactionsListener(TIDevice tIDevice, TransactionType transactionType) {
        super(null);
        this.deviceHolder = DeviceExplorerFactory.getDeviceList().findDeviceHolder(tIDevice);
        if (this.deviceHolder != null) {
            this.transactionType = transactionType;
            if (transactionType != TransactionType.SEND_OS && transactionType != TransactionType.CAPTURING_SCREEN) {
                this.deviceHolder.setCancelButtonEnabled(true);
            } else {
                this.deviceHolder.setCancelButtonEnabled(true);
                this.deviceHolder.setCancelButtonEnabled(false);
            }
        }
    }

    @Override
    public void transactionStarted() {
        if (this.deviceHolder != null) {
            this.deviceHolder.setTransactionType(this.transactionType);
        }
    }

    @Override
    public void setPercentage(final int n) {
        super.setPercentage(n);
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                float f;
                float f2 = DeviceTransactionsListener.this.getTransactionProgressData().getItemsTotal();
                float f3 = DeviceTransactionsListener.this.getTransactionProgressData().getItemBeingDone() - 1;
                float f4 = f = f2 != 0.0f ? (float)n / f2 + f3 / f2 * 100.0f : 0.0f;
                if (DeviceTransactionsListener.this.deviceHolder != null) {
                    DeviceTransactionsListener.this.deviceHolder.setTransactionProgress(f);
                }
            }
        });
    }

    @Override
    public void setDone() {
        super.setDone();
        if (this.deviceHolder != null) {
            this.deviceHolder.setProgressBarEnabled(true);
            this.deviceHolder.setCancelButtonEnabled(false);
            this.deviceHolder.setTransactionCanceled(false);
            if (this.deviceHolder.screenCaptureEnabledProperty().getValue().booleanValue()) {
                if (this.deviceHolder.getTIDevice().isOSpresent()) {
                    this.deviceHolder.setScreenCaptureEnabled(false);
                    this.deviceHolder.setScreenCaptureEnabled(true);
                }
            } else if (null != this.deviceHolder.getTIDevice() && DeviceExplorerFactory.getDeviceList().getMode() == 1) {
                this.deviceHolder.setScreenCaptureEnabled(!this.deviceHolder.isSendOSTransferInProgress() && this.deviceHolder.getTIDevice().isOSpresent());
            }
            if (this.deviceHolder.isSendOSTransferInProgress() && !this.didErrorOccur) {
                this.deviceHolder.setDistressDeviceProperty(false);
            }
        }
    }

    @Override
    public boolean hasBeenCanceled() {
        if (super.hasBeenCanceled() || this.deviceHolder != null && this.deviceHolder.hasBeenCanceled()) {
            return true;
        }
        return false;
    }

    @Override
    public void setOverallCompletionCount(final int n, final int n2) {
        super.setOverallCompletionCount(n, n2);
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                if (DeviceTransactionsListener.this.deviceHolder != null && DeviceTransactionsListener.this.deviceHolder.getTIDevice() != null && DeviceExplorerFactory.isOverlayPaneLoaded(DeviceTransactionsListener.this.deviceHolder.getTIDevice()) && DeviceExplorerFactory.getOverlayPane(DeviceTransactionsListener.this.deviceHolder.getTIDevice()) instanceof VarsTransactionOverlayInterface) {
                    int n3 = n;
                    int n22 = n2;
                    if (DeviceTransactionsListener.this.itemBeingDoneOverride != -1) {
                        --n3;
                        n3 += DeviceTransactionsListener.this.itemBeingDoneOverride;
                    }
                    if (DeviceTransactionsListener.this.itemsInTotalOverride != -1) {
                        n22 = DeviceTransactionsListener.this.itemsInTotalOverride;
                    }
                    ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(DeviceTransactionsListener.this.deviceHolder.getTIDevice())).setItemsCount(n3, n22);
                }
            }
        });
    }

    @Override
    public void setCurrentFile(final String string) {
        super.setCurrentFile(string);
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                if (DeviceTransactionsListener.this.deviceHolder != null && DeviceTransactionsListener.this.deviceHolder.getTIDevice() != null && DeviceExplorerFactory.isOverlayPaneLoaded(DeviceTransactionsListener.this.deviceHolder.getTIDevice()) && DeviceExplorerFactory.getOverlayPane(DeviceTransactionsListener.this.deviceHolder.getTIDevice()) instanceof VarsTransactionOverlayInterface) {
                    ((VarsTransactionOverlayInterface)DeviceExplorerFactory.getOverlayPane(DeviceTransactionsListener.this.deviceHolder.getTIDevice())).setCurrentFileName(string);
                }
            }
        });
    }

    @Override
    public void reportError(TIDevice tIDevice, String string, TIStatus tIStatus) {
        super.reportError(tIDevice, string, tIStatus);
        this.didErrorOccur = tIStatus.isFailure();
        TransactionType transactionType = TransactionType.NONE;
        TIDevice tIDevice2 = null;
        if (this.deviceHolder != null) {
            transactionType = TransactionType.valueOf(this.deviceHolder.transactionTypeProperty().getValue());
            tIDevice2 = this.deviceHolder.getTIDevice();
            if (ConnectServerFactory.getDeviceComm().isAlive()) {
                this.runOnUIThread(new Runnable(){

                    @Override
                    public void run() {
                        DeviceTransactionsListener.this.deviceHolder.setErrorReported(true);
                    }
                });
            }
        }
        String[] arrstring = ErrorTranslator.errorCodeToMessage(transactionType, tIStatus, tIDevice2, string);
        final String string2 = arrstring[0];
        final String string3 = arrstring[1];
        if (string2 != null && string3 != null && ConnectServerFactory.getDeviceComm().isAlive()) {
            PromptFromBackgroundThread promptFromBackgroundThread = new PromptFromBackgroundThread(){

                @Override
                public void showPrompt(PromptResult promptResult) {
                    PromptDialog.showUserError(string2, string3);
                    promptResult.setResult(0);
                    if (DeviceTransactionsListener.this.deviceHolder != null) {
                        DeviceTransactionsListener.this.deviceHolder.setErrorReported(false);
                    }
                }
            };
            promptFromBackgroundThread.promptAndWaitForResult();
        }
    }

    private void runOnUIThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater((Runnable)runnable);
        }
    }

}

