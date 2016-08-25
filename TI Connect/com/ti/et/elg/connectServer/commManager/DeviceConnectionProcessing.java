/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDeviceConnectionListener;
import com.ti.et.elg.connectServer.exports.TINode;
import com.ti.et.elg.connectServer.exports.TINodeDetectionListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DeviceConnectionProcessing {
    private TINodeDetectionListener tiNodeDetectionListener;
    private Map<String, TIDevice> nodeToDevice = Collections.synchronizedMap(new HashMap());
    private Vector<TIDeviceConnectionListener> deviceConnectionListeners = new Vector();
    private static final int DEVICE_INFO_MAX_TRY_COUNT = 2;
    private final String LOG_TAG = DeviceConnectionProcessing.class.getSimpleName();

    public void init(final CommManagerInterface commManagerInterface) {
        this.tiNodeDetectionListener = new TINodeDetectionListener(){

            @Override
            public void nodeConnected(TINode tINode) {
                TILogger.logInfo("DeviceConnectionProcessing", "Create a TIDevice");
                final TIDevice tIDevice = commManagerInterface.createTIDevice(tINode);
                TILogger.logInfo("DeviceConnectionProcessing", "nodeToDevice.put");
                DeviceConnectionProcessing.this.nodeToDevice.put(tINode.getNodeId(), tIDevice);
                new Thread("AutoDetect_getDeviceInfo"){

                    @Override
                    public void run() {
                        super.run();
                        TILogger.logInfo("DeviceConnectionProcessing", "GetDeviceInfo");
                        int n = 0;
                        while (n < 2) {
                            TIStatus tIStatus = commManagerInterface.getDeviceInfo(tIDevice);
                            tIDevice.setDeviceID("");
                            if (!tIStatus.isFailure()) {
                                Vector vector = (Vector)DeviceConnectionProcessing.this.deviceConnectionListeners.clone();
                                for (TIDeviceConnectionListener tIDeviceConnectionListener : vector) {
                                    tIDeviceConnectionListener.deviceConnected(tIDevice);
                                }
                                n = 2;
                                continue;
                            }
                            TILogger.logError("On try " + n + " DeviceConnectionProcessing", "getDeviceInfo() failed with status: " + tIStatus.getStatusCode());
                            ++n;
                        }
                    }
                }.start();
            }

            @Override
            public void nodeDisconnected(TINode tINode) {
                TIDevice tIDevice = (TIDevice)DeviceConnectionProcessing.this.nodeToDevice.get(tINode.getNodeId());
                if (tIDevice == null) {
                    TILogger.logError(DeviceConnectionProcessing.this.LOG_TAG, "nodeDisconnected: No TIDevice found with TINode: " + tINode.getNodeId());
                } else {
                    tIDevice.setDisconnected();
                    Vector vector = (Vector)DeviceConnectionProcessing.this.deviceConnectionListeners.clone();
                    for (TIDeviceConnectionListener tIDeviceConnectionListener : vector) {
                        tIDeviceConnectionListener.deviceDisconnected(tIDevice);
                    }
                    DeviceConnectionProcessing.this.nodeToDevice.remove(tINode.getNodeId());
                }
            }

        };
        ConnectServerFactory.getAutoDetect().addNodeConnectionListener(this.tiNodeDetectionListener);
    }

    public TIDevice getTIDeviceForConnectionID(String string) {
        for (TIDevice tIDevice : this.nodeToDevice.values()) {
            if (tIDevice.getConnectionID() == null || tIDevice.getConnectionID().equals("")) {
                ConnectServerFactory.getCommManager().getIDDeviceInfo(tIDevice);
            }
            if (!tIDevice.getConnectionID().equalsIgnoreCase(string)) continue;
            return tIDevice;
        }
        return null;
    }

    public Collection<TIDevice> getConnectedDevices() {
        for (TIDevice tIDevice : this.nodeToDevice.values()) {
            if (tIDevice.getConnectionID() == null || !tIDevice.getConnectionID().equals("")) continue;
            ConnectServerFactory.getCommManager().getIDDeviceInfo(tIDevice);
        }
        return this.nodeToDevice.values();
    }

    public void shutDown() {
        ConnectServerFactory.getAutoDetect().removeNodeConnectionListener(this.tiNodeDetectionListener);
    }

    public void addDeviceConnectionListener(TIDeviceConnectionListener tIDeviceConnectionListener) {
        this.deviceConnectionListeners.add(tIDeviceConnectionListener);
    }

    public void removeDeviceConnectionListener(TIDeviceConnectionListener tIDeviceConnectionListener) {
        this.deviceConnectionListeners.remove(tIDeviceConnectionListener);
    }

}

