/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;

public interface TIProgressListener {
    public static final int NO = 0;
    public static final int YES = 1;
    public static final int NOTOALL = 2;
    public static final int YESTOALL = 3;
    public static final int CANCEL = 4;

    public boolean hasBeenCanceled();

    public void setPercentage(int var1);

    public void setCurrentFile(String var1);

    public void setOverallCompletionCount(int var1, int var2);

    public void reportError(TIDevice var1, String var2, TIStatus var3);

    public int confirm(String var1);
}

