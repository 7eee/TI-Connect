/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.TINodeDetectionListener;

public interface AutoDetectInterface {
    public void addNodeConnectionListener(TINodeDetectionListener var1);

    public void removeNodeConnectionListener(TINodeDetectionListener var1);

    public void forceDisconnect(String var1);
}

