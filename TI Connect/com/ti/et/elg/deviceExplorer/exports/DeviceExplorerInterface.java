/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.exports;

import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.exports.TIOpenHandlerInterface;
import com.ti.et.elg.transactionManager.Transaction;
import java.io.File;
import java.util.List;

public interface DeviceExplorerInterface {
    public void init(WorkspaceManagerInterface var1);

    public void notifyIsActive(boolean var1);

    public void shutDown();

    public void addTIDevice(TIDevice var1);

    public void removeTIDevice(TIDevice var1);

    public void getVarsFromDeviceToOtherDevices(TIDevice var1, TIVar[] var2, List<TIDevice> var3, SendToHandheldDialog.HandHeldTargets var4);

    public void sendFilesToTIDevices(List<File> var1, List<TIDevice> var2, Class<? extends Transaction<Object, TIDevice>> var3);

    public void sendFilesToPromptedDevice(File[] var1);

    public boolean hasActiveTransactions();

    public WorkspaceManagerInterface getWorkSpaceManager();

    public void registerOpenHandlerForType(int var1, TIOpenHandlerInterface var2);

    public TIOpenHandlerInterface getOpenHandlerForType(int var1);

    public void setDeviceExplorerProductMode(ProductMode var1);

    public ProductMode getDeviceExplorerProductMode();

    public void commonSendFilesToTIDevicesDropHandler(List<File> var1, List<TIDevice> var2);

    public static enum ProductMode {
        TI_CONNECT,
        TI_SMARTVIEW;
        

        private ProductMode() {
        }
    }

}

