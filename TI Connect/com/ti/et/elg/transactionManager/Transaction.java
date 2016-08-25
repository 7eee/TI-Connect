/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.sun.javafx.tk.Toolkit
 *  javafx.application.Platform
 *  javafx.stage.Stage
 */
package com.ti.et.elg.transactionManager;

import com.sun.javafx.tk.Toolkit;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import java.util.List;
import javafx.application.Platform;
import javafx.stage.Stage;

public abstract class Transaction<PreStepReturnType, BackgroundStepReturnType> {
    public static final int STATE_WAITING = 0;
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_DONE = 2;
    protected TransactionListener listener = null;
    private TransactionGroup transactionGroup = null;
    private int state = 0;

    public void performTransaction() {
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Must call performTransaction() from UI thread!");
        }
        this.setTransactionState(1);
        this.listener.transactionStarted();
        final PreStepReturnType PreStepReturnType = this.doPreStep();
        new Thread(this.getClass().getSimpleName()){

            @Override
            public void run() {
                super.run();
                final Object BackgroundStepReturnType = Transaction.this.doInBackground(PreStepReturnType);
                if (Transaction.this.multiStepTransaction()) {
                    if (Transaction.this.listener.getModalStage() != null) {
                        Platform.runLater((Runnable)new Runnable(){

                            @Override
                            public void run() {
                                Toolkit.getToolkit().exitNestedEventLoop((Object)Transaction.this.listener.getModalStage(), (Object)null);
                            }
                        });
                    }
                } else {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            Transaction.this.doPostStep(BackgroundStepReturnType);
                            Transaction.this.setTransactionState(2);
                            Transaction.this.listener.setDone();
                        }
                    });
                }
            }

        }.start();
    }

    public void setTransactionGroup(TransactionGroup transactionGroup) {
        this.transactionGroup = transactionGroup;
    }

    public TransactionGroup getTransactionGroup() {
        return this.transactionGroup;
    }

    public TransactionListener getListener() {
        return this.listener;
    }

    protected abstract PreStepReturnType doPreStep();

    protected abstract BackgroundStepReturnType doInBackground(PreStepReturnType var1);

    protected abstract void doPostStep(BackgroundStepReturnType var1);

    public boolean shouldTransactionProgressBeReported() {
        return true;
    }

    protected void setTransactionState(int n) {
        this.state = n;
        if (n == 2) {
            TransactionManager.getInstance().wakeUpDispatcher();
        }
    }

    public int getTransactionState() {
        return this.state;
    }

    public boolean compressBackToBackTransactionsOfThisType() {
        return false;
    }

    public boolean multiStepTransaction() {
        return false;
    }

    public void setParameters(List<TIVar> list, TIDevice tIDevice, TransactionManager transactionManager) {
    }

}

