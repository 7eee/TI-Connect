/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.factory;

import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayBaseInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.CapturingScreenOverlayControler;
import com.ti.et.elg.deviceExplorer.DeviceExplorerController;
import com.ti.et.elg.deviceExplorer.DeviceExplorerTableViewController;
import com.ti.et.elg.deviceExplorer.DeviceExplorerToolbar;
import com.ti.et.elg.deviceExplorer.DeviceList;
import com.ti.et.elg.deviceExplorer.NotOSPresentOverlayController;
import com.ti.et.elg.deviceExplorer.NothingConnectedOverlayController;
import com.ti.et.elg.deviceExplorer.RefreshOverlayController;
import com.ti.et.elg.deviceExplorer.SendingOSOverlayController;
import com.ti.et.elg.deviceExplorer.VarsTransactionOverlayController;
import com.ti.et.elg.deviceExplorer.exports.DeviceContentsInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerToolbarInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceListInterface;
import com.ti.et.elg.deviceExplorer.exports.OverlayForDeviceInterface;
import java.util.HashMap;

public class DeviceExplorerFactory {
    private static DeviceListInterface deviceList;
    private static DeviceContentsInterface contentTable;
    private static DeviceExplorerInterface deviceExplorer;
    private static DeviceExplorerToolbarInterface deviceExplorerToolbar;
    private static OverlayBaseInterface nothingConnectedPane;
    private static HashMap<TIDevice, OverlayForDeviceInterface> overlayPanes;
    private static TIDevice idselectedDevice;

    private DeviceExplorerFactory() {
    }

    public static void configureFactory(DeviceExplorerInterface deviceExplorerInterface, DeviceListInterface deviceListInterface, DeviceContentsInterface deviceContentsInterface, DeviceExplorerToolbarInterface deviceExplorerToolbarInterface, OverlayBaseInterface overlayBaseInterface) {
        deviceExplorer = deviceExplorerInterface;
        deviceList = deviceListInterface;
        contentTable = deviceContentsInterface;
        deviceExplorerToolbar = deviceExplorerToolbarInterface;
        nothingConnectedPane = overlayBaseInterface;
    }

    public static void configureFactory() {
        DeviceExplorerController deviceExplorerController = new DeviceExplorerController();
        DeviceExplorerTableViewController deviceExplorerTableViewController = new DeviceExplorerTableViewController();
        DeviceExplorerToolbar deviceExplorerToolbar = new DeviceExplorerToolbar();
        DeviceList deviceList = new DeviceList();
        NothingConnectedOverlayController nothingConnectedOverlayController = new NothingConnectedOverlayController();
        DeviceExplorerFactory.configureFactory(deviceExplorerController, deviceList, deviceExplorerTableViewController, deviceExplorerToolbar, nothingConnectedOverlayController);
    }

    public static DeviceExplorerInterface getDeviceExplorer() {
        return deviceExplorer;
    }

    public static DeviceExplorerToolbarInterface getDeviceExplorerToolbar() {
        return deviceExplorerToolbar;
    }

    public static DeviceListInterface getDeviceList() {
        return deviceList;
    }

    public static DeviceContentsInterface getContentTable() {
        return contentTable;
    }

    public static OverlayBaseInterface getNothingConnectedPane() {
        return nothingConnectedPane;
    }

    public static void addOverlayPanes(TIDevice tIDevice, OverlayForDeviceInterface overlayForDeviceInterface) {
        overlayPanes.put(tIDevice, overlayForDeviceInterface);
    }

    public static OverlayForDeviceInterface getOverlayPane(TIDevice tIDevice) {
        return overlayPanes.get(tIDevice);
    }

    public static void removeOverlayPane(TIDevice tIDevice) {
        overlayPanes.remove(tIDevice);
    }

    public static void loadOverlayPane(TIDevice tIDevice, OverlayForDeviceInterface overlayForDeviceInterface) {
        DeviceExplorerFactory.addOverlayPanes(tIDevice, overlayForDeviceInterface);
        overlayPanes.get(tIDevice).loadPane(tIDevice);
        if (tIDevice == idselectedDevice) {
            overlayPanes.get(tIDevice).showPane(tIDevice);
        }
    }

    public static void loadOverlayPane(TIDevice tIDevice, IDOverlappingPanelsType iDOverlappingPanelsType) {
        switch (iDOverlappingPanelsType) {
            case REFRESH: {
                RefreshOverlayController refreshOverlayController = new RefreshOverlayController();
                DeviceExplorerFactory.loadOverlayPane(tIDevice, refreshOverlayController);
                break;
            }
            case RECEIVING: 
            case SENDING: 
            case DELETING: {
                VarsTransactionOverlayController varsTransactionOverlayController = new VarsTransactionOverlayController(iDOverlappingPanelsType);
                DeviceExplorerFactory.loadOverlayPane(tIDevice, varsTransactionOverlayController);
                break;
            }
            case SENDING_OS: {
                SendingOSOverlayController sendingOSOverlayController = new SendingOSOverlayController();
                DeviceExplorerFactory.loadOverlayPane(tIDevice, sendingOSOverlayController);
                break;
            }
            case DISTRESS_DEVICE: {
                NotOSPresentOverlayController notOSPresentOverlayController = new NotOSPresentOverlayController();
                DeviceExplorerFactory.loadOverlayPane(tIDevice, notOSPresentOverlayController);
                break;
            }
            case CAPTURING_SCREEN: {
                CapturingScreenOverlayControler capturingScreenOverlayControler = new CapturingScreenOverlayControler();
                DeviceExplorerFactory.loadOverlayPane(tIDevice, capturingScreenOverlayControler);
                break;
            }
        }
    }

    public static void unloadOverlayPane(TIDevice tIDevice) {
        if (tIDevice != null && DeviceExplorerFactory.isOverlayPaneLoaded(tIDevice)) {
            overlayPanes.get(tIDevice).unloadPane(tIDevice);
            overlayPanes.remove(tIDevice);
        }
    }

    public static void deviceSelected(TIDevice tIDevice, TIDevice tIDevice2) {
        if (tIDevice != null && DeviceExplorerFactory.isOverlayPaneLoaded(tIDevice)) {
            overlayPanes.get(tIDevice).hidePane(tIDevice);
        }
        if (tIDevice2 != null && DeviceExplorerFactory.isOverlayPaneLoaded(tIDevice2)) {
            overlayPanes.get(tIDevice2).showPane(tIDevice2);
        }
        idselectedDevice = tIDevice2;
    }

    public static boolean isOverlayPaneLoaded(TIDevice tIDevice) {
        if (overlayPanes.get(tIDevice) != null) {
            return true;
        }
        return false;
    }

    static {
        overlayPanes = new HashMap();
        idselectedDevice = null;
    }

}

