/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.programEditor.exports;

import com.ti.et.elg.programEditor.exports.OverlayForDeviceInterface;
import com.ti.et.elg.transactionManager.Transaction;

public interface VarsTransactionOverlayInterface
extends OverlayForDeviceInterface {
    public void setItemsCount(int var1, int var2);

    public void setCurrenFileName(String var1);

    public void setTransaction(Transaction<?, ?> var1);
}

