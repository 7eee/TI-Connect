/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.deviceComm;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.deviceComm.NativeDeviceComm;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.DeviceCommInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;

public class DeviceComm
implements DeviceCommInterface {
    private boolean isAlive = true;
    private static NativeDeviceComm nativeDeviceComm;

    public DeviceComm() {
        TILogger.logInfo(DeviceComm.class.getSimpleName(), "Loading NativeConnectServer");
        System.loadLibrary("NativeConnectServer");
    }

    @Override
    public void init(boolean bl, boolean bl2, boolean bl3) {
        TILogger.logInfo(DeviceComm.class.getSimpleName(), "Initializing NativeConnectServer -> autoDetect84Pfamily:" + bl + " & getDeviceNameFromDriver:" + bl2);
        nativeDeviceComm = NativeDeviceComm.initNativeDeviceComm(bl, bl2, bl3);
    }

    @Override
    public TIStatus sendVar(TIDevice tIDevice, TIVar tIVar, boolean bl, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.sendObject(ConnectServerFactory.getCommManager(), tIDevice, tIVar, bl, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus sendOSUpdate(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.sendOSUpdate(ConnectServerFactory.getCommManager(), tIDevice, tIVar, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus executeVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.executeVar(ConnectServerFactory.getCommManager(), tIDevice, tIVar, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus sendKey(TIDevice tIDevice, int n, TIProgressListener tIProgressListener) {
        int n2 = this.isAlive ? nativeDeviceComm.sendKey(ConnectServerFactory.getCommManager(), tIDevice, n, tIProgressListener) : 62;
        return new TIStatus(n2);
    }

    @Override
    public TIStatus getVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.getObject(ConnectServerFactory.getCommManager(), tIDevice, tIVar, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus getANSVariable(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.getANSObject(ConnectServerFactory.getCommManager(), tIDevice, tIVar, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus getDirectory(TIDevice tIDevice, TIDirectory tIDirectory, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.getDirectory(ConnectServerFactory.getCommManager(), tIDevice, tIDirectory, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus deleteVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.deleteObject(ConnectServerFactory.getCommManager(), tIDevice, tIVar, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus getStaticDeviceInfo(TIDevice tIDevice) {
        int n = this.isAlive ? nativeDeviceComm.getDeviceInfo(ConnectServerFactory.getCommManager(), tIDevice) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus getDynamicDeviceInfo(TIDevice tIDevice) {
        int n = this.isAlive ? nativeDeviceComm.getDeviceDynamicInfo(ConnectServerFactory.getCommManager(), tIDevice) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus getIDDeviceInfo(TIDevice tIDevice) {
        int n = this.isAlive ? nativeDeviceComm.getIDDeviceInfo(ConnectServerFactory.getCommManager(), tIDevice) : 62;
        return new TIStatus(n);
    }

    @Override
    public void shutDown() {
        this.stopTransactions();
        nativeDeviceComm.shutDown();
    }

    @Override
    public TIStatus getScreen(TIDevice tIDevice, TIImage tIImage, TIProgressListener tIProgressListener) {
        int n = this.isAlive ? nativeDeviceComm.getScreen(ConnectServerFactory.getCommManager(), tIDevice, tIImage, tIProgressListener) : 62;
        return new TIStatus(n);
    }

    @Override
    public TIStatus singleQuery(TIDevice tIDevice, int n, DeviceErrorHolder deviceErrorHolder) {
        int n2 = this.isAlive ? nativeDeviceComm.singleQuery(ConnectServerFactory.getCommManager(), tIDevice, n, deviceErrorHolder) : 62;
        return new TIStatus(n2);
    }

    @Override
    public TIStatus setOverwriteTimeout(TIDevice tIDevice, int n) {
        int n2 = this.isAlive ? nativeDeviceComm.setCommConfiguration(ConnectServerFactory.getCommManager(), tIDevice, n) : 62;
        return new TIStatus(n2);
    }

    @Override
    public TIStatus setIDListValue(TIDevice tIDevice, byte[] arrby) {
        int n = this.isAlive ? nativeDeviceComm.setIDListValue(ConnectServerFactory.getCommManager(), tIDevice, arrby) : 62;
        return new TIStatus(n);
    }

    @Override
    public void stopTransactions() {
        this.isAlive = false;
    }

    @Override
    public void continueTransactions() {
        this.isAlive = true;
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }
}

