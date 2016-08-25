/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.exports;

import com.ti.et.elg.commonUISupport.overlayPanes.OverlayBaseInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;

public interface OverlayForDeviceInterface
extends OverlayBaseInterface {
    public void showPane(TIDevice var1);

    public void hidePane(TIDevice var1);

    public void loadPane(TIDevice var1);

    public void unloadPane(TIDevice var1);

    public void addPane(TIDevice var1);
}

