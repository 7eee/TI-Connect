/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.autoDetect;

import com.ti.et.elg.connectServer.autoDetect.mock.TINodeMock;
import com.ti.et.elg.connectServer.exports.AutoDetectInterface;
import com.ti.et.elg.connectServer.exports.TINode;
import com.ti.et.elg.connectServer.exports.TINodeDetectionListener;
import com.ti.et.utils.logger.TILogger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AutoDetect
implements AutoDetectInterface {
    private Vector<TINodeDetectionListener> listeners = new Vector();
    private Map<String, TINode> connectedNodes = Collections.synchronizedMap(new HashMap());
    private static AutoDetect instance = new AutoDetect();

    private AutoDetect() {
    }

    public static AutoDetect getInstance() {
        return instance;
    }

    public void triggerConnect(final String string, final String string2) {
        TILogger.logInfo(AutoDetect.class.getSimpleName(), "triggerConnect with deviceName: " + string + "[" + string2 + "]");
        new Thread("AutoDetect_connect"){

            @Override
            public void run() {
                super.run();
                TILogger.logInfo("AutoDetect_connect", "Let's add the new Device");
                Vector vector = (Vector)AutoDetect.this.listeners.clone();
                TINodeMock tINodeMock = new TINodeMock();
                tINodeMock.setNodeParams(string2, string);
                AutoDetect.this.connectedNodes.put(string2, tINodeMock);
                TILogger.logInfo("AutoDetect_connect", "firing listeners for " + string + ": " + AutoDetect.this.listeners.size());
                for (TINodeDetectionListener tINodeDetectionListener : vector) {
                    TILogger.logInfo("AutoDetect_connect", "we got some listeners....");
                    tINodeDetectionListener.nodeConnected(tINodeMock);
                }
                TILogger.logInfo("AutoDetect_connect", "it bail out!!");
            }
        }.start();
    }

    public void triggerDisconnect(final String string, final String string2) {
        TILogger.logInfo(AutoDetect.class.getSimpleName(), "triggerDisconnect with deviceName: " + string + "[" + string2 + "]");
        new Thread("AutoDetect_disconnect"){

            @Override
            public void run() {
                super.run();
                Vector vector = (Vector)AutoDetect.this.listeners.clone();
                TINodeMock tINodeMock = new TINodeMock();
                tINodeMock.setNodeParams(string2, string);
                if (AutoDetect.this.connectedNodes.size() > 0) {
                    if (AutoDetect.this.connectedNodes.containsKey(string2)) {
                        AutoDetect.this.connectedNodes.remove(string2);
                    }
                    for (TINodeDetectionListener tINodeDetectionListener : vector) {
                        tINodeDetectionListener.nodeDisconnected(tINodeMock);
                    }
                }
            }
        }.start();
    }

    @Override
    public void addNodeConnectionListener(TINodeDetectionListener tINodeDetectionListener) {
        TILogger.logInfo("AutoDetect_connect", "addNodeConnectionListener " + tINodeDetectionListener + "size=" + this.listeners.size());
        this.listeners.add(tINodeDetectionListener);
    }

    @Override
    public void removeNodeConnectionListener(TINodeDetectionListener tINodeDetectionListener) {
        TILogger.logInfo("AutoDetect_connect", "About to call listeners.remove " + tINodeDetectionListener + "size=" + this.listeners.size());
        this.listeners.remove(tINodeDetectionListener);
    }

    @Override
    public void forceDisconnect(String string) {
    }

}

