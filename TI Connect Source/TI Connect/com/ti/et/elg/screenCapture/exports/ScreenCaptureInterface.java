/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.exports;

import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;

public interface ScreenCaptureInterface {
    public void init(WorkspaceManagerInterface var1);

    public void notifyIsActive(boolean var1);

    public boolean shutdown();

    public void setScreenCaptureProductMode(DeviceExplorerInterface.ProductMode var1);

    public DeviceExplorerInterface.ProductMode getScreenCaptureProductMode();
}

