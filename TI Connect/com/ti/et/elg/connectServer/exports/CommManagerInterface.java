/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.exports.IOStreamInterface;
import com.ti.et.elg.connectServer.exports.TICertificate;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDeviceConnectionListener;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TINode;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import java.util.Collection;
import java.util.List;

public interface CommManagerInterface {
    public void init();

    public void shutDown();

    public TIDevice addEmulatedDevice(IOStreamInterface var1);

    public void removeEmulatedDevice(TIDevice var1);

    public void getScreenCapture(TIDevice var1, TIImage var2, TIProgressListener var3);

    public void getVar(TIDevice var1, List<TIVar> var2, TIProgressListener var3);

    public void getANSVar(TIDevice var1, TIVar var2, TIProgressListener var3);

    public void sendVar(TIDevice var1, List<TIVar> var2, TIProgressListener var3);

    public void executeVar(TIDevice var1, TIVar var2, TIProgressListener var3);

    public void sendOSUpdate(TIDevice var1, TIVar var2, TIProgressListener var3);

    public void deleteVar(TIDevice var1, List<TIVar> var2, TIProgressListener var3);

    public TIStatus isDeviceBusy(TIDevice var1);

    public TIStatus queryLastError(TIDevice var1, DeviceErrorHolder var2);

    public TIStatus getDeviceInfo(TIDevice var1);

    public TIStatus getDeviceDynamicInfo(TIDevice var1);

    public TIStatus getIDDeviceInfo(TIDevice var1);

    public TIStatus setOverwriteTimeout(TIDevice var1, int var2);

    public TIStatus setIDListValue(TIDevice var1, byte[] var2);

    public void sendTICertificate(TIDevice var1, TICertificate var2, TIProgressListener var3);

    public void getDirectory(TIDevice var1, TIDirectory var2, TIProgressListener var3);

    public void sendKey(TIDevice var1, int var2, TIProgressListener var3);

    public void addDeviceConnectionListener(TIDeviceConnectionListener var1);

    public void removeDeviceConnectionListener(TIDeviceConnectionListener var1);

    public TIDevice createTIDevice(TINode var1);

    public TIVar createTIVar(String var1, String var2, byte[] var3, int var4, int var5, int var6, int var7, boolean var8, int var9, int var10, boolean var11);

    public List<TIVar> createTIVar(String var1);

    public List<TIVar> createTIVar(String var1, boolean var2);

    public TIDirectory createTIDirectory();

    public TIImage createTIImage(TIDevice var1);

    public TIDevice getTIDeviceForConnectionID(String var1);

    public Collection<TIDevice> getConnectedDevices();
}

