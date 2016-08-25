/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.stage.Stage
 */
package com.ti.et.elg.transactionManager;

import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.prompt.PromptFromBackgroundThread;
import com.ti.et.elg.commonUISupport.prompt.PromptResult;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionProgressData;
import com.ti.et.utils.logger.TILogger;
import java.util.Vector;
import javafx.application.Platform;
import javafx.stage.Stage;

public class TransactionListener
implements TIProgressListener {
    private TIStatus status = new TIStatus(0);
    private TransactionProgressData transactionProgressData = null;
    private Stage modalStage;
    private Transaction<?, ?> transaction;
    private int confirmOverride = -1;
    protected int itemsInTotalOverride = -1;
    protected int itemBeingDoneOverride = -1;

    public void transactionStarted() {
    }

    public void setItemsInTotalOverride(int n) {
        this.itemsInTotalOverride = n;
    }

    public void setItemBeingDoneOverride(int n) {
        this.itemBeingDoneOverride = n;
    }

    public TransactionListener(Transaction<?, ?> transaction) {
        this.transaction = transaction;
    }

    public TransactionProgressData getTransactionProgressData() {
        return this.transactionProgressData;
    }

    public void setTransactionProgressData(TransactionProgressData transactionProgressData) {
        this.transactionProgressData = transactionProgressData;
    }

    public void setTransaction(Transaction<?, ?> transaction) {
        this.transaction = transaction;
    }

    public TIStatus getStatus() {
        return this.status;
    }

    @Override
    public boolean hasBeenCanceled() {
        return this.transactionProgressData.getCancel();
    }

    @Override
    public void setPercentage(final int n) {
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                TransactionListener.this.transactionProgressData.setPercentage(n);
            }
        });
    }

    @Override
    public void setCurrentFile(final String string) {
        if (string != null) {
            this.runOnUIThread(new Runnable(){

                @Override
                public void run() {
                    TransactionListener.this.transactionProgressData.setCurrentFileName(string);
                }
            });
        }
    }

    @Override
    public void setOverallCompletionCount(final int n, final int n2) {
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                if (TransactionListener.this.transactionProgressData != null) {
                    if (TransactionListener.this.itemBeingDoneOverride != -1) {
                        TransactionListener.this.transactionProgressData.setItemBeingDone(n - 1 + TransactionListener.this.itemBeingDoneOverride);
                    } else {
                        TransactionListener.this.transactionProgressData.setItemBeingDone(n);
                    }
                    if (TransactionListener.this.itemsInTotalOverride != -1) {
                        TransactionListener.this.transactionProgressData.setItemsTotal(TransactionListener.this.itemsInTotalOverride);
                    } else {
                        TransactionListener.this.transactionProgressData.setItemsTotal(n2);
                    }
                }
            }
        });
    }

    @Override
    public void reportError(TIDevice tIDevice, String string, final TIStatus tIStatus) {
        String string2 = tIDevice == null ? "NULL" : tIDevice.toString();
        TILogger.logError("TransactionListener", "For Device<" + string2 + "> Error code reported is: " + tIStatus.getStatusCode());
        this.status = tIStatus;
        this.runOnUIThread(new Runnable(){

            @Override
            public void run() {
                TransactionListener.this.transactionProgressData.setStatus(tIStatus.getStatusCode());
            }
        });
    }

    @Override
    public int confirm(final String string) {
        if (this.confirmOverride != -1) {
            return this.confirmOverride;
        }
        PromptFromBackgroundThread promptFromBackgroundThread = new PromptFromBackgroundThread(){

            @Override
            public void showPrompt(PromptResult promptResult) {
                if (TransactionListener.this.confirmOverride != -1) {
                    promptResult.setResult(TransactionListener.this.confirmOverride);
                } else {
                    PromptDialog.showReplaceFilePrompt(string, promptResult);
                }
            }
        };
        int n = promptFromBackgroundThread.promptAndWaitForResult();
        if ((n == 3 || n == 4 || n == 2) && this.transaction.getTransactionGroup() != null && this.confirmOverride == -1) {
            Vector vector = this.transaction.getTransactionGroup().getTransactions();
            for (Transaction transaction : vector) {
                transaction.getListener().setConfirmOverride(n);
            }
        }
        return n;
    }

    public void setDone() {
        if (this.transactionProgressData != null) {
            this.transactionProgressData.setDone();
        }
    }

    public void setModalStage(Stage stage) {
        this.modalStage = stage;
    }

    public Stage getModalStage() {
        return this.modalStage;
    }

    public void setConfirmOverride(int n) {
        this.confirmOverride = n;
    }

    public int getConfirmOverride() {
        return this.confirmOverride;
    }

    private void runOnUIThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater((Runnable)runnable);
        }
    }

}

