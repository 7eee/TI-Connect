/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.transactionManager;

import com.ti.et.elg.transactionManager.Transaction;
import java.util.Vector;

public class TransactionGroup {
    Vector<Transaction<?, ?>> transactions = new Vector();

    public void addTransaction(Transaction<?, ?> transaction) {
        this.transactions.add(transaction);
        transaction.setTransactionGroup(this);
    }

    public Vector<Transaction<?, ?>> getTransactions() {
        return this.transactions;
    }
}

