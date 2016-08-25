/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager.mock;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
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

public class CommManagerMock
implements CommManagerInterface {
    @Override
    public TIDevice addEmulatedDevice(IOStreamInterface iOStreamInterface) {
        return null;
    }

    @Override
    public void removeEmulatedDevice(TIDevice tIDevice) {
    }

    @Override
    public TIDevice getTIDeviceForConnectionID(String string) {
        return null;
    }

    @Override
    public Collection<TIDevice> getConnectedDevices() {
        return null;
    }

    @Override
    public TIDevice createTIDevice(TINode tINode) {
        return null;
    }

    @Override
    public TIVar createTIVar(String string, String string2, byte[] arrby, int n, int n2, int n3, int n4, boolean bl, int n5, int n6, boolean bl2) {
        return null;
    }

    @Override
    public TIDirectory createTIDirectory() {
        return null;
    }

    @Override
    public TIImage createTIImage(TIDevice tIDevice) {
        return null;
    }

    @Override
    public void init() {
    }

    @Override
    public void shutDown() {
    }

    @Override
    public void getScreenCapture(TIDevice tIDevice, TIImage tIImage, TIProgressListener tIProgressListener) {
    }

    @Override
    public void getVar(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
    }

    @Override
    public void sendVar(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
    }

    @Override
    public void deleteVar(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
    }

    @Override
    public TIStatus getDeviceInfo(TIDevice tIDevice) {
        return null;
    }

    @Override
    public void sendOSUpdate(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
    }

    @Override
    public void sendTICertificate(TIDevice tIDevice, TICertificate tICertificate, TIProgressListener tIProgressListener) {
    }

    @Override
    public void getDirectory(TIDevice tIDevice, TIDirectory tIDirectory, TIProgressListener tIProgressListener) {
    }

    @Override
    public void sendKey(TIDevice tIDevice, int n, TIProgressListener tIProgressListener) {
    }

    @Override
    public void addDeviceConnectionListener(TIDeviceConnectionListener tIDeviceConnectionListener) {
    }

    @Override
    public void removeDeviceConnectionListener(TIDeviceConnectionListener tIDeviceConnectionListener) {
    }

    @Override
    public TIStatus isDeviceBusy(TIDevice tIDevice) {
        return null;
    }

    @Override
    public TIStatus queryLastError(TIDevice tIDevice, DeviceErrorHolder deviceErrorHolder) {
        return null;
    }

    @Override
    public TIStatus setOverwriteTimeout(TIDevice tIDevice, int n) {
        return null;
    }

    @Override
    public void executeVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
    }

    @Override
    public void getANSVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
    }

    @Override
    public TIStatus getDeviceDynamicInfo(TIDevice tIDevice) {
        return null;
    }

    @Override
    public TIStatus getIDDeviceInfo(TIDevice tIDevice) {
        return null;
    }

    @Override
    public TIStatus setIDListValue(TIDevice tIDevice, byte[] arrby) {
        return null;
    }

    @Override
    public List<TIVar> createTIVar(String string) {
        return null;
    }

    @Override
    public List<TIVar> createTIVar(String string, boolean bl) {
        return null;
    }
}

