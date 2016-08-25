/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport;

import com.ti.et.elg.connectServer.autoDetect.mock.TINodeMock;
import com.ti.et.elg.connectServer.commManager.TIDeviceImpl;
import com.ti.et.elg.connectServer.exports.TINode;

class TIDeviceMock
extends TIDeviceImpl {
    public TIDeviceMock() {
        super(new TINodeMock());
        this.getNode().setNodeParams("1", "TI-83 Premium CE");
        this.setDeviceID("1234567890");
    }
}

