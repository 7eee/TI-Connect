/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;

class SimpleErrorListener
implements TIProgressListener {
    private int errorCode = 0;

    SimpleErrorListener() {
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public void reportError(TIDevice tIDevice, String string, TIStatus tIStatus) {
        this.errorCode = tIStatus.getStatusCode();
    }

    @Override
    public boolean hasBeenCanceled() {
        return false;
    }

    @Override
    public void setPercentage(int n) {
    }

    @Override
    public void setCurrentFile(String string) {
    }

    @Override
    public void setOverallCompletionCount(int n, int n2) {
    }

    @Override
    public int confirm(String string) {
        return 3;
    }
}

