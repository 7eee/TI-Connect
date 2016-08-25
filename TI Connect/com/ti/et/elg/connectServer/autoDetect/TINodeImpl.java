/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.autoDetect;

import com.ti.et.elg.connectServer.exports.TINode;

public class TINodeImpl
implements TINode {
    private String nodeID;
    private String deviceName;
    private static final int NODE_TYPE = 1;

    @Override
    public String getNodeId() {
        return this.nodeID;
    }

    @Override
    public void setNodeParams(String string, String string2) {
        this.nodeID = string;
        this.deviceName = string2;
    }

    @Override
    public String getDeviceName() {
        return this.deviceName;
    }

    @Override
    public int getNodeType() {
        return 1;
    }
}

