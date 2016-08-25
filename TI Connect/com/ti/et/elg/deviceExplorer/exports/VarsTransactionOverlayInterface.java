/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.exports;

import com.ti.et.elg.deviceExplorer.exports.OverlayForDeviceInterface;
import com.ti.et.elg.transactionManager.Transaction;

public interface VarsTransactionOverlayInterface
extends OverlayForDeviceInterface {
    public void setItemsCount(int var1, int var2);

    public void setCurrentFileName(String var1);

    public void setTransaction(Transaction<?, ?> var1);
}

