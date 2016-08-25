/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.sun.javafx.tk.Toolkit
 *  javafx.application.Platform
 *  javafx.collections.ObservableList
 *  javafx.stage.Stage
 */
package com.ti.et.elg.transactionManager;

import com.sun.javafx.tk.Toolkit;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionProgressData;
import com.ti.et.elg.transactionManager.TransactionQueueListener;
import com.ti.et.utils.logger.TILogger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class TransactionManager {
    private ObservableList<TransactionProgressData> transactionProgressDataList = null;
    private Map<TIDevice, List<Transaction<?, ?>>> deviceToTransactions = new HashMap();
    private Vector<TransactionQueueListener> transactionQueueListeners = new Vector();
    private DispatcherThread dispatcher;
    private boolean dispatcherAwake = false;
    private static final TransactionManager INSTANCE = new TransactionManager();

    public static TransactionManager getInstance() {
        return INSTANCE;
    }

    private TransactionManager() {
        this.dispatcher = new DispatcherThread();
        this.dispatcher.start();
    }

    public void shutDown() {
        if (this.dispatcher.isAlive()) {
            this.dispatcher.shutdown();
            this.wakeUpDispatcher();
        }
    }

    public synchronized void wakeUpDispatcher() {
        this.setDispatcherAwake(true);
        this.notifyAll();
    }

    public synchronized boolean isTransactionQueueEmpty(TIDevice tIDevice) {
        List list = this.deviceToTransactions.get(tIDevice);
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public synchronized boolean hasActiveTransactions() {
        boolean bl = false;
        Set<TIDevice> set = null;
        if (this.deviceToTransactions == null) {
            return bl;
        }
        set = this.deviceToTransactions.keySet();
        if (null != set && set.size() > 0) {
            for (TIDevice tIDevice : set) {
                bl = bl || !this.isTransactionQueueEmpty(tIDevice);
            }
        }
        return bl;
    }

    public void addTransaction(TIDevice tIDevice, Transaction<?, ?> transaction, TransactionGroup transactionGroup) {
        TransactionListener transactionListener = transaction.getListener();
        if (transactionListener.getTransactionProgressData() == null) {
            transactionListener.setTransactionProgressData(this.createTransactionProgressData());
        }
        if (transaction.shouldTransactionProgressBeReported()) {
            this.transactionProgressDataList.add(0, (Object)transactionListener.getTransactionProgressData());
        }
        this.enqueueTransaction(tIDevice, transaction);
        if (transactionGroup != null) {
            transactionGroup.addTransaction(transaction);
        }
    }

    public synchronized boolean runTransactionStepNowAndWait(TIDevice tIDevice, Transaction<?, ?> transaction) {
        if (transaction.multiStepTransaction() && transaction.getTransactionState() == 1) {
            Stage stage = new Stage();
            transaction.getListener().setModalStage(stage);
            transaction.performTransaction();
            Toolkit.getToolkit().enterNestedEventLoop((Object)stage);
            return true;
        }
        return false;
    }

    public void setTransactionProgressDataList(ObservableList<TransactionProgressData> observableList) {
        this.transactionProgressDataList = observableList;
    }

    public void addTransactionQueueListener(TransactionQueueListener transactionQueueListener) {
        this.transactionQueueListeners.add(transactionQueueListener);
    }

    public void removeTransactionQueueListener(TransactionQueueListener transactionQueueListener) {
        this.transactionQueueListeners.remove(transactionQueueListener);
    }

    private TransactionProgressData createTransactionProgressData() {
        return new TransactionProgressData();
    }

    private synchronized void enqueueTransaction(TIDevice tIDevice, Transaction<?, ?> transaction) {
        List list = this.deviceToTransactions.get(tIDevice);
        if (list == null) {
            list = new LinkedList();
            this.deviceToTransactions.put(tIDevice, list);
        }
        if (list.size() > 0 && transaction.compressBackToBackTransactionsOfThisType()) {
            Transaction transaction2 = list.get(list.size() - 1);
            if (transaction2.getClass() != transaction.getClass()) {
                list.add(transaction);
                TILogger.logDetail(TransactionManager.class.getSimpleName(), "Transaction <" + transaction + "> added to queue for device <" + tIDevice + ">");
            }
            this.fireTransactionQueueNonEmpty(tIDevice);
        } else {
            list.add(transaction);
            TILogger.logDetail(TransactionManager.class.getSimpleName(), "Transaction <" + transaction + "> added to queue for device <" + tIDevice + ">");
            this.fireTransactionQueueNonEmpty(tIDevice);
        }
    }

    private void fireTransactionQueueNonEmpty(TIDevice tIDevice) {
        Vector vector = (Vector)this.transactionQueueListeners.clone();
        for (TransactionQueueListener transactionQueueListener : vector) {
            transactionQueueListener.transactionQueueNonEmpty(tIDevice);
        }
        this.wakeUpDispatcher();
    }

    private void fireTransactionQueueEmpty(final TIDevice tIDevice) {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                if (!TransactionManager.this.isTransactionQueueEmpty(tIDevice)) {
                    return;
                }
                Vector vector = (Vector)TransactionManager.this.transactionQueueListeners.clone();
                for (TransactionQueueListener transactionQueueListener : vector) {
                    transactionQueueListener.transactionQueueEmpty(tIDevice);
                }
            }
        });
    }

    public boolean isDispatcherAwake() {
        return this.dispatcherAwake;
    }

    public void setDispatcherAwake(boolean bl) {
        this.dispatcherAwake = bl;
    }

    class DispatcherThread
    extends Thread {
        private boolean done;

        public DispatcherThread() {
            super("TransactionManager.DispatcherThread");
            this.done = false;
        }

        public void shutdown() {
            this.done = true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            while (!this.done) {
                TransactionManager transactionManager = TransactionManager.this;
                synchronized (transactionManager) {
                    try {
                        TransactionManager.this.setDispatcherAwake(false);
                        TransactionManager.this.wait(10000);
                    }
                    catch (InterruptedException var2_3) {
                        TransactionManager.this.setDispatcherAwake(true);
                    }
                    if (this.done) {
                        break;
                    }
                    Set set = TransactionManager.this.deviceToTransactions.keySet();
                    for (TIDevice tIDevice : set) {
                        List list = (List)TransactionManager.this.deviceToTransactions.get(tIDevice);
                        if (list == null || list.size() <= 0) continue;
                        Transaction transaction = (Transaction)list.get(0);
                        if (transaction.getTransactionState() == 2) {
                            TILogger.logDetail(TransactionManager.class.getSimpleName(), "Transaction <" + transaction + "> removed from queue for device <" + tIDevice + ">");
                            list.remove(0);
                            if (list.isEmpty()) {
                                TransactionManager.this.fireTransactionQueueEmpty(tIDevice);
                            } else {
                                transaction = (Transaction)list.get(0);
                            }
                        }
                        if (transaction.getTransactionState() != 0) continue;
                        transaction.setTransactionState(1);
                        final Transaction transaction2 = transaction;
                        Platform.runLater((Runnable)new Runnable(){

                            @Override
                            public void run() {
                                transaction2.performTransaction();
                            }
                        });
                    }
                    continue;
                }
            }
        }

    }

}

