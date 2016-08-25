/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;

public interface DeviceCommInterface {
    public void init(boolean var1, boolean var2, boolean var3);

    public TIStatus sendVar(TIDevice var1, TIVar var2, boolean var3, TIProgressListener var4);

    public TIStatus sendOSUpdate(TIDevice var1, TIVar var2, TIProgressListener var3);

    public TIStatus executeVar(TIDevice var1, TIVar var2, TIProgressListener var3);

    public TIStatus getVar(TIDevice var1, TIVar var2, TIProgressListener var3);

    public TIStatus getANSVariable(TIDevice var1, TIVar var2, TIProgressListener var3);

    public TIStatus getScreen(TIDevice var1, TIImage var2, TIProgressListener var3);

    public TIStatus getDirectory(TIDevice var1, TIDirectory var2, TIProgressListener var3);

    public TIStatus deleteVar(TIDevice var1, TIVar var2, TIProgressListener var3);

    public TIStatus getStaticDeviceInfo(TIDevice var1);

    public TIStatus getDynamicDeviceInfo(TIDevice var1);

    public TIStatus getIDDeviceInfo(TIDevice var1);

    public TIStatus sendKey(TIDevice var1, int var2, TIProgressListener var3);

    public TIStatus singleQuery(TIDevice var1, int var2, DeviceErrorHolder var3);

    public TIStatus setOverwriteTimeout(TIDevice var1, int var2);

    public TIStatus setIDListValue(TIDevice var1, byte[] var2);

    public void shutDown();

    public void stopTransactions();

    public void continueTransactions();

    public boolean isAlive();
}

