/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import java.util.List;

public interface TestAutomationInterface {
    public static final int OVERWRITE_CARS_DEFAULT_TIMEOUT = -1;

    public String getVersionString();

    public void init(boolean var1, boolean var2);

    public void shutDown();

    public void useSimulator();

    public TIDevice getTIDevice(String var1);

    public TIVar createTIVar(String var1);

    public int getConnectedDevices(List<String> var1);

    public int sendKey(String var1, int var2);

    public int getScreen(String var1, String var2);

    public int checkIfDeviceIsBusy(String var1);

    public int queryLastError(String var1, DeviceErrorHolder var2);

    public int getDirectory(String var1, List<TIVar> var2);

    public int getDeviceInfo(TIDevice var1);

    public int getDeviceDynamicInfo(TIDevice var1);

    public int getVariable(String var1, TIVar var2);

    public int getANSVariable(String var1, TIVar var2);

    public int sendVariable(String var1, TIVar var2);

    public int sendOS(String var1, TIVar var2);

    public int deleteVariable(String var1, TIVar var2);

    public int executeVariable(String var1, TIVar var2);

    public int setOverwriteTimeout(String var1, int var2);

    public int setIDListValue(TIDevice var1, byte[] var2);
}

