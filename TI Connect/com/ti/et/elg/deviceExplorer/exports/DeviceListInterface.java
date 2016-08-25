/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableSet
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Node
 */
package com.ti.et.elg.deviceExplorer.exports;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.deviceList.DeviceListDefinitions;
import com.ti.et.elg.commonUISupport.deviceList.NotifyConnectedListener;
import com.ti.et.elg.commonUISupport.deviceList.TIDeviceHolder;
import com.ti.et.elg.commonUISupport.deviceList.TakeScreenListener;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceSelectionListener;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableSet;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

public interface DeviceListInterface
extends DeviceListDefinitions {
    public void addDeviceSelectionListener(DeviceSelectionListener var1);

    public void removeDeviceSelectionListener(DeviceSelectionListener var1);

    public void addTakeScreenListener(TakeScreenListener var1);

    public void addnotifyConnectedHandheldsListener(NotifyConnectedListener var1);

    public Node getRootNode();

    public Node getFocusableNode();

    public void init(DeviceExplorerInterface var1);

    public void addTIDevice(TIDevice var1);

    public void removeTIDevice(TIDevice var1);

    public List<TIDevice> getConnectedDevices();

    public TIDeviceHolder findDeviceHolder(TIDevice var1);

    public void setMode(int var1);

    public void setSendOSAction(TIAction var1);

    public void setDeviceRefreshAction(TIAction var1);

    public void setHHInfoSAction(TIAction var1);

    public void setSendScreenCaptureEventHandler(EventHandler<Event> var1);

    public int devicesAvailable();

    public ObservableSet<TIDevice> getBusyDeviceSet();

    public ArrayList<TIDevice> getTargetedDevices();

    public void notifyScreenCapture();

    public int getMode();
}

