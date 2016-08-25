/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.transactionManager;

import com.ti.et.elg.connectServer.exports.TIDevice;

public interface TransactionQueueListener {
    public void transactionQueueEmpty(TIDevice var1);

    public void transactionQueueNonEmpty(TIDevice var1);
}

