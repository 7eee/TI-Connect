/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.deviceComm;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class NativeDeviceComm {
    private static NativeDeviceComm mInstance;

    private native void initializeDeviceCommLibraryN(String var1, int var2, Class<?> var3, boolean var4, boolean var5, boolean var6);

    private native int getDirectoryN(CommManagerInterface var1, TIDevice var2, TIDirectory var3, TIProgressListener var4);

    private native int getObjectN(CommManagerInterface var1, TIDevice var2, TIVar var3, TIProgressListener var4);

    private native int getScreenN(CommManagerInterface var1, TIDevice var2, TIImage var3, TIProgressListener var4);

    private native int deleteObjectN(CommManagerInterface var1, TIDevice var2, TIVar var3, TIProgressListener var4);

    private native int getDeviceInfoN(CommManagerInterface var1, TIDevice var2, String var3);

    private native int sendObjectN(CommManagerInterface var1, TIDevice var2, TIVar var3, boolean var4, TIProgressListener var5);

    private native int sendOSUpdateN(CommManagerInterface var1, TIDevice var2, TIVar var3, TIProgressListener var4);

    private native int sendKeyN(CommManagerInterface var1, TIDevice var2, int var3, TIProgressListener var4);

    private native int singleQueryN(CommManagerInterface var1, TIDevice var2, int var3, DeviceErrorHolder var4);

    private native int setCommConfigurationN(CommManagerInterface var1, TIDevice var2, int var3);

    private native int executeCommandN(CommManagerInterface var1, TIDevice var2, TIVar var3, TIProgressListener var4);

    private native int getANSObjectN(CommManagerInterface var1, TIDevice var2, TIVar var3, TIProgressListener var4);

    private native int getDeviceDynamicInfoN(CommManagerInterface var1, TIDevice var2, String var3);

    private native int getIDDeviceInfoN(CommManagerInterface var1, TIDevice var2, String var3);

    private native int setIDListValueN(CommManagerInterface var1, TIDevice var2, byte[] var3);

    private native void shutDownDeviceCommLibraryN();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static NativeDeviceComm initNativeDeviceComm(boolean bl, boolean bl2, boolean bl3) {
        reference var3_3 = NativeDeviceComm.class;
        synchronized (NativeDeviceComm.class) {
            if (mInstance == null) {
                mInstance = new NativeDeviceComm(bl, bl2, bl3);
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return mInstance;
        }
    }

    private NativeDeviceComm(boolean bl, boolean bl2, boolean bl3) {
        this.initializeDeviceCommLibrary(bl, bl2, bl3);
    }

    private void initializeDeviceCommLibrary(final boolean bl, final boolean bl2, final boolean bl3) {
        final int n = TILogger.getCLogLevel();
        final String string = TILogger.getLogFolderPath() + "cLog.txt";
        new Thread("NativeConnectServer-JNI Thread"){

            @Override
            public void run() {
                NativeDeviceComm.this.initializeDeviceCommLibraryN(string, n, ConnectServerFactory.class, bl, bl2, bl3);
            }
        }.start();
    }

    public int getDirectory(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIDirectory tIDirectory, TIProgressListener tIProgressListener) {
        return this.getDirectoryN(commManagerInterface, tIDevice, tIDirectory, tIProgressListener);
    }

    public int getObject(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return this.getObjectN(commManagerInterface, tIDevice, tIVar, tIProgressListener);
    }

    public int getANSObject(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return this.getANSObjectN(commManagerInterface, tIDevice, tIVar, tIProgressListener);
    }

    public int getScreen(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIImage tIImage, TIProgressListener tIProgressListener) {
        return this.getScreenN(commManagerInterface, tIDevice, tIImage, tIProgressListener);
    }

    public int deleteObject(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return this.deleteObjectN(commManagerInterface, tIDevice, tIVar, tIProgressListener);
    }

    public int getDeviceInfo(CommManagerInterface commManagerInterface, TIDevice tIDevice) {
        return this.getDeviceInfoN(commManagerInterface, tIDevice, tIDevice.getNodeId());
    }

    public int getDeviceDynamicInfo(CommManagerInterface commManagerInterface, TIDevice tIDevice) {
        return this.getDeviceDynamicInfoN(commManagerInterface, tIDevice, tIDevice.getNodeId());
    }

    public int getIDDeviceInfo(CommManagerInterface commManagerInterface, TIDevice tIDevice) {
        return this.getIDDeviceInfoN(commManagerInterface, tIDevice, tIDevice.getNodeId());
    }

    public int sendObject(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIVar tIVar, boolean bl, TIProgressListener tIProgressListener) {
        return this.sendObjectN(commManagerInterface, tIDevice, tIVar, bl, tIProgressListener);
    }

    public int sendOSUpdate(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return this.sendOSUpdateN(commManagerInterface, tIDevice, tIVar, tIProgressListener);
    }

    public int sendKey(CommManagerInterface commManagerInterface, TIDevice tIDevice, int n, TIProgressListener tIProgressListener) {
        return this.sendKeyN(commManagerInterface, tIDevice, n, tIProgressListener);
    }

    public int singleQuery(CommManagerInterface commManagerInterface, TIDevice tIDevice, int n, DeviceErrorHolder deviceErrorHolder) {
        return this.singleQueryN(commManagerInterface, tIDevice, n, deviceErrorHolder);
    }

    public int executeVar(CommManagerInterface commManagerInterface, TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return this.executeCommandN(commManagerInterface, tIDevice, tIVar, tIProgressListener);
    }

    public int setCommConfiguration(CommManagerInterface commManagerInterface, TIDevice tIDevice, int n) {
        return this.setCommConfigurationN(commManagerInterface, tIDevice, n);
    }

    public int setIDListValue(CommManagerInterface commManagerInterface, TIDevice tIDevice, byte[] arrby) {
        return this.setIDListValueN(commManagerInterface, tIDevice, arrby);
    }

    public void shutDown() {
        this.shutDownDeviceCommLibraryN();
    }

}

