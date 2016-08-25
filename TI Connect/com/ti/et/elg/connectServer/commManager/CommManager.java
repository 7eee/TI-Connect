/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.DeleteVarProcessing;
import com.ti.et.elg.connectServer.commManager.DeviceConnectionProcessing;
import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.commManager.DirectoryProcessing;
import com.ti.et.elg.connectServer.commManager.ExecuteVarProcessing;
import com.ti.et.elg.connectServer.commManager.GetANSObjectProcessing;
import com.ti.et.elg.connectServer.commManager.GetDeviceInfoProcessing;
import com.ti.et.elg.connectServer.commManager.NullListener;
import com.ti.et.elg.connectServer.commManager.OSTransferProcessing;
import com.ti.et.elg.connectServer.commManager.OneOrMultipleVarsInHostfileProcessing;
import com.ti.et.elg.connectServer.commManager.RetrieveScreenProcessing;
import com.ti.et.elg.connectServer.commManager.RetrieveVarProcessing;
import com.ti.et.elg.connectServer.commManager.SendKeyProcessing;
import com.ti.et.elg.connectServer.commManager.SendVarProcessing;
import com.ti.et.elg.connectServer.commManager.SetDeviceInfoProcessing;
import com.ti.et.elg.connectServer.commManager.SetOverwriteTimeout;
import com.ti.et.elg.connectServer.commManager.SingleQueryProcessing;
import com.ti.et.elg.connectServer.commManager.TIDeviceImpl;
import com.ti.et.elg.connectServer.commManager.TIDirectoryImpl;
import com.ti.et.elg.connectServer.commManager.TIImageImpl;
import com.ti.et.elg.connectServer.commManager.TIVarImpl;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CommManager
implements CommManagerInterface {
    private int carsDeviceAttributeID_AreYouBusy = 56;
    private int carsDeviceAttributeID_ErrorNumber = 74;
    private SendVarProcessing sendVarProcessing = new SendVarProcessing();
    private SendKeyProcessing sendKeyProcessing = new SendKeyProcessing();
    private RetrieveVarProcessing retrieveVarProcessing = new RetrieveVarProcessing();
    private RetrieveScreenProcessing retrieveScreenProcessing = new RetrieveScreenProcessing();
    private DeviceConnectionProcessing deviceConnectionProcessing = new DeviceConnectionProcessing();
    private DirectoryProcessing directoryProcessing = new DirectoryProcessing();
    private DeleteVarProcessing deleteVarProcessing = new DeleteVarProcessing();
    private GetDeviceInfoProcessing getDeviceInfoProcessing = new GetDeviceInfoProcessing();
    private SingleQueryProcessing singleQueryProcessing = new SingleQueryProcessing();
    private SetOverwriteTimeout setOverwriteTimeout = new SetOverwriteTimeout();
    private OSTransferProcessing osTransferProcessing = new OSTransferProcessing();
    private ExecuteVarProcessing executeVarProcessing = new ExecuteVarProcessing();
    private GetANSObjectProcessing transferANSObject = new GetANSObjectProcessing();
    private SetDeviceInfoProcessing setDeviceInfoProcessing = new SetDeviceInfoProcessing();
    private OneOrMultipleVarsInHostfileProcessing oneOrMultipleVars = new OneOrMultipleVarsInHostfileProcessing();

    @Override
    public TIDevice addEmulatedDevice(IOStreamInterface iOStreamInterface) {
        return new TIDeviceImpl(iOStreamInterface);
    }

    @Override
    public void removeEmulatedDevice(TIDevice tIDevice) {
    }

    @Override
    public TIDevice getTIDeviceForConnectionID(String string) {
        return this.deviceConnectionProcessing.getTIDeviceForConnectionID(string);
    }

    @Override
    public Collection<TIDevice> getConnectedDevices() {
        return this.deviceConnectionProcessing.getConnectedDevices();
    }

    @Override
    public TIDevice createTIDevice(TINode tINode) {
        return new TIDeviceImpl(tINode);
    }

    @Override
    public TIVar createTIVar(String string, String string2, byte[] arrby, int n, int n2, int n3, int n4, boolean bl, int n5, int n6, boolean bl2) {
        return new TIVarImpl(string, string2, arrby, n, n2, n3, n4, bl, n5, n6, bl2);
    }

    @Override
    public List<TIVar> createTIVar(String string) {
        return this.oneOrMultipleVars.createTIVarsFromFile(string);
    }

    @Override
    public List<TIVar> createTIVar(String string, boolean bl) {
        return this.oneOrMultipleVars.createTIVarsFromFile(string, bl);
    }

    @Override
    public TIDirectory createTIDirectory() {
        return new TIDirectoryImpl();
    }

    @Override
    public TIImage createTIImage(TIDevice tIDevice) {
        TIImageImpl tIImageImpl = new TIImageImpl(tIDevice.getDeviceWidth(), tIDevice.getDeviceHeight());
        tIImageImpl.setDeviceProductID(tIDevice.getProductID());
        tIImageImpl.setDeviceHardwareVersion(tIDevice.getHardwareVersion());
        return tIImageImpl;
    }

    @Override
    public void init() {
        this.deviceConnectionProcessing.init(this);
    }

    @Override
    public void shutDown() {
        this.deviceConnectionProcessing.shutDown();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void getScreenCapture(TIDevice tIDevice, TIImage tIImage, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            this.retrieveScreenProcessing.transfer(tIDevice, tIImage, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void getVar(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            this.retrieveVarProcessing.transfer(tIDevice, list, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void getANSVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            ArrayList<TIVar> arrayList = new ArrayList<TIVar>(1);
            arrayList.add(tIVar);
            this.transferANSObject.transfer(tIDevice, arrayList, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sendVar(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            if (tIDevice.getHardwareVersion() <= 6) {
                this.getDirectory(tIDevice, this.createTIDirectory(), new NullListener());
            }
            this.sendVarProcessing.transfer(tIDevice, list, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void executeVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            this.executeVarProcessing.executeVar(tIDevice, tIVar, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteVar(TIDevice tIDevice, List<TIVar> list, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            this.deleteVarProcessing.delete(tIDevice, list, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TIStatus getDeviceInfo(TIDevice tIDevice) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            TIStatus tIStatus = this.getDeviceInfoProcessing.getStaticDeviceInfo(tIDevice);
            return tIStatus;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TIStatus getDeviceDynamicInfo(TIDevice tIDevice) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            TIStatus tIStatus = this.getDeviceInfoProcessing.getDynamicDeviceInfo(tIDevice);
            return tIStatus;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TIStatus getIDDeviceInfo(TIDevice tIDevice) {
        if (tIDevice.getConnectionID() == null || tIDevice.getConnectionID().equals("")) {
            ReentrantLock reentrantLock = tIDevice.getLock();
            reentrantLock.lock();
            try {
                TIStatus tIStatus = this.getDeviceInfoProcessing.getIDDeviceInfo(tIDevice);
                return tIStatus;
            }
            finally {
                reentrantLock.unlock();
            }
        }
        return new TIStatus(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sendOSUpdate(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            ArrayList<TIVar> arrayList = new ArrayList<TIVar>(1);
            arrayList.add(tIVar);
            this.osTransferProcessing.transfer(tIDevice, arrayList, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void sendTICertificate(TIDevice tIDevice, TICertificate tICertificate, TIProgressListener tIProgressListener) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void getDirectory(TIDevice tIDevice, TIDirectory tIDirectory, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            this.directoryProcessing.populate(tIDevice, tIDirectory, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void sendKey(TIDevice tIDevice, int n, TIProgressListener tIProgressListener) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            this.sendKeyProcessing.send(tIDevice, n, tIProgressListener);
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public void addDeviceConnectionListener(TIDeviceConnectionListener tIDeviceConnectionListener) {
        this.deviceConnectionProcessing.addDeviceConnectionListener(tIDeviceConnectionListener);
    }

    @Override
    public void removeDeviceConnectionListener(TIDeviceConnectionListener tIDeviceConnectionListener) {
        this.deviceConnectionProcessing.removeDeviceConnectionListener(tIDeviceConnectionListener);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TIStatus isDeviceBusy(TIDevice tIDevice) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            TIStatus tIStatus = this.singleQueryProcessing.singleQuery(tIDevice, this.carsDeviceAttributeID_AreYouBusy);
            return tIStatus;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TIStatus queryLastError(TIDevice tIDevice, DeviceErrorHolder deviceErrorHolder) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            TIStatus tIStatus = this.singleQueryProcessing.singleQuery(tIDevice, this.carsDeviceAttributeID_ErrorNumber, deviceErrorHolder);
            return tIStatus;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TIStatus setOverwriteTimeout(TIDevice tIDevice, int n) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            TIStatus tIStatus = this.setOverwriteTimeout.setOverwriteTimeout(tIDevice, n);
            return tIStatus;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public TIStatus setIDListValue(TIDevice tIDevice, byte[] arrby) {
        ReentrantLock reentrantLock = tIDevice.getLock();
        reentrantLock.lock();
        try {
            TIStatus tIStatus = this.setDeviceInfoProcessing.setIDListValue(tIDevice, arrby);
            return tIStatus;
        }
        finally {
            reentrantLock.unlock();
        }
    }
}

