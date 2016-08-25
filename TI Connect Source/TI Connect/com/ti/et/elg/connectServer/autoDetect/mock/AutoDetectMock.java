/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.autoDetect.mock;

import com.ti.et.elg.connectServer.autoDetect.mock.TINodeMock;
import com.ti.et.elg.connectServer.exports.AutoDetectInterface;
import com.ti.et.elg.connectServer.exports.TINode;
import com.ti.et.elg.connectServer.exports.TINodeDetectionListener;
import com.ti.et.utils.logger.TILogger;
import java.util.Vector;

public class AutoDetectMock
implements AutoDetectInterface {
    private Vector<TINodeDetectionListener> listeners = new Vector();
    private Vector<TINode> connectedNodes = new Vector();
    private int nodeID = 1;

    public void triggerConnect(final int n, final int n2) {
        new Thread("AutoDeteckMock_connect"){

            @Override
            public void run() {
                super.run();
                Vector vector = (Vector)AutoDetectMock.this.listeners.clone();
                for (int i = 0; i < n; ++i) {
                    try {
                        Thread.sleep(n2);
                        TINodeMock tINodeMock = new TINodeMock();
                        tINodeMock.setNodeParams(String.valueOf(AutoDetectMock.this.nodeID), String.valueOf(AutoDetectMock.this.nodeID));
                        AutoDetectMock.this.nodeID++;
                        AutoDetectMock.this.connectedNodes.add(tINodeMock);
                        for (TINodeDetectionListener tINodeDetectionListener : vector) {
                            tINodeDetectionListener.nodeConnected(tINodeMock);
                        }
                        continue;
                    }
                    catch (InterruptedException var3_4) {
                        TILogger.logError("AutoDetectMock", "triggerConnect", var3_4);
                    }
                }
            }
        }.start();
    }

    public void triggerDisconnect(final int n, final int n2) {
        new Thread("AutoDetectMock_disconnect"){

            @Override
            public void run() {
                super.run();
                Vector vector = (Vector)AutoDetectMock.this.listeners.clone();
                for (int i = 0; i < n; ++i) {
                    try {
                        Thread.sleep(n2);
                        if (AutoDetectMock.this.connectedNodes.size() <= 0) continue;
                        TINode tINode = (TINode)AutoDetectMock.this.connectedNodes.remove(0);
                        for (TINodeDetectionListener tINodeDetectionListener : vector) {
                            tINodeDetectionListener.nodeDisconnected(tINode);
                        }
                        continue;
                    }
                    catch (InterruptedException var3_4) {
                        TILogger.logError("AutoDetectMock", "triggerDisconnect", var3_4);
                    }
                }
            }
        }.start();
    }

    @Override
    public void addNodeConnectionListener(TINodeDetectionListener tINodeDetectionListener) {
        this.listeners.add(tINodeDetectionListener);
    }

    @Override
    public void removeNodeConnectionListener(TINodeDetectionListener tINodeDetectionListener) {
        this.listeners.remove(tINodeDetectionListener);
    }

    @Override
    public void forceDisconnect(String string) {
    }

}

