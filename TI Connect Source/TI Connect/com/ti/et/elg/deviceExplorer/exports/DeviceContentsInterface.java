/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Node
 */
package com.ti.et.elg.deviceExplorer.exports;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import java.io.File;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;

public interface DeviceContentsInterface {
    public Node getRootNode();

    public void init(DeviceExplorerInterface var1);

    public void updateDirectory(TIDevice var1, TIDirectory var2);

    public byte[] getDataForFileName(String var1);

    public void writeSelectedDeviceFilesToURL(String var1);

    public TIVar[] getCurrentlyDraggingVars();

    public TIVar[] getCurrentlySelectedVars();

    public void deleteSelectedFilesFromTIDevice();

    public void getSelectedFilesFromTIDevice(File var1);

    public void getSelectedFileFromTIDevice(File var1);

    public void refreshCurrentTIDevice();

    public void setParent(OverlayStackPaneBase var1);

    public void hidePane(TIDevice var1);

    public void showPane(Node var1);

    public OverlayStackPaneBase getStackPane();

    public void setDeleteAction(TIAction var1);

    public void setSendToComputerAction(TIAction var1);

    public void setDeviceRefreshAction(TIAction var1);

    public void setSelectAllAction(TIAction var1);

    public void setSendToHHAction(TIAction var1);

    public void sendOSToSelectedDevice(File var1);

    public void selectAll();

    public void unselectAll();

    public Node getFocusableNode();

    public void setSendScreenCaptureEventHandler(EventHandler<Event> var1);

    public TIDevice getSelectedDevice();
}

