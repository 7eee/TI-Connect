/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;

class NullListener
implements TIProgressListener {
    NullListener() {
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
    public void reportError(TIDevice tIDevice, String string, TIStatus tIStatus) {
    }

    @Override
    public int confirm(String string) {
        return 0;
    }
}

