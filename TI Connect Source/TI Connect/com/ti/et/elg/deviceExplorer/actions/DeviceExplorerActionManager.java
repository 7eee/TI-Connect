/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.actions;

import com.ti.et.elg.deviceExplorer.actions.AddToHandheldAction;
import com.ti.et.elg.deviceExplorer.actions.CaptureScreenAction;
import com.ti.et.elg.deviceExplorer.actions.DataImportAction;
import com.ti.et.elg.deviceExplorer.actions.DeleteVarsAction;
import com.ti.et.elg.deviceExplorer.actions.DeviceRefreshAction;
import com.ti.et.elg.deviceExplorer.actions.GetHHInformationAction;
import com.ti.et.elg.deviceExplorer.actions.SaveVarsAction;
import com.ti.et.elg.deviceExplorer.actions.SelectAllAction;
import com.ti.et.elg.deviceExplorer.actions.SendOSAction;
import com.ti.et.elg.deviceExplorer.actions.SendToHHAction;
import com.ti.et.elg.deviceExplorer.actions.UnselectAllAction;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;

public class DeviceExplorerActionManager {
    private static final DeviceExplorerActionManager INSTANCE = new DeviceExplorerActionManager();
    private boolean selectedDeviceBusy = false;
    private boolean varsSelected = false;
    private boolean hasOSPresent = false;
    private boolean isADeviceSelected = false;
    private boolean isUnderOSUpdate = false;
    private boolean twoOrMoreDevicesAvailable = false;
    private boolean isDevicesAvailable = false;
    private boolean deviceExplorerActiveWorkspace = false;

    private DeviceExplorerActionManager() {
    }

    public static DeviceExplorerActionManager getInstance() {
        return INSTANCE;
    }

    public void selectedDeviceBusy(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.selectedDeviceBusy = bl;
        this.hasOSPresent = bl2;
        this.isADeviceSelected = bl3;
        this.isUnderOSUpdate = bl4;
        this.updateActionState();
    }

    public void varsSelected(boolean bl) {
        this.varsSelected = bl;
        this.updateActionState();
    }

    public void devicesAvailable(int n, int n2) {
        this.twoOrMoreDevicesAvailable = n > 1;
        this.isDevicesAvailable = n2 == 0 && n > 0;
        this.updateActionState();
    }

    public void deviceExplorerActiveWorkspace(boolean bl) {
        this.deviceExplorerActiveWorkspace = bl;
        this.updateActionState();
    }

    private void updateActionState() {
        SaveVarsAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy || !this.varsSelected);
        SendToHHAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy || !this.varsSelected || !this.twoOrMoreDevicesAvailable);
        AddToHandheldAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW && this.selectedDeviceBusy);
        DataImportAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy);
        DeleteVarsAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy || !this.varsSelected);
        DeviceRefreshAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy || !this.deviceExplorerActiveWorkspace);
        GetHHInformationAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy || DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_CONNECT && !this.deviceExplorerActiveWorkspace);
        SendOSAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.isADeviceSelected || this.selectedDeviceBusy);
        SelectAllAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy);
        UnselectAllAction.getInstance().setDisabled(this.isUnderOSUpdate || !this.hasOSPresent || !this.isADeviceSelected || this.selectedDeviceBusy || !this.varsSelected);
        CaptureScreenAction.getInstance().setDisabled(!this.isDevicesAvailable);
    }
}

