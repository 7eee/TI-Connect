/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.collections.ObservableSet
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Node
 *  javafx.scene.input.DragEvent
 *  javafx.scene.input.Dragboard
 *  javafx.scene.input.TransferMode
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import com.ti.et.elg.commonUISupport.deviceList.DeviceListBase;
import com.ti.et.elg.commonUISupport.deviceList.NotifyConnectedListener;
import com.ti.et.elg.commonUISupport.deviceList.TIDeviceHolder;
import com.ti.et.elg.commonUISupport.deviceList.TakeScreenListener;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.actions.DeviceExplorerActionManager;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceListInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceSelectionListener;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.TransactionManager;
import com.ti.et.elg.transactionManager.TransactionQueueListener;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableSet;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class DeviceList
extends DeviceListBase
implements DeviceListInterface {
    private DeviceExplorerInterface deviceExplorer = null;
    private Vector<DeviceSelectionListener> deviceSelectionListeners = new Vector();
    private NotifyConnectedListener notifyConnectedListener;
    private EventHandler<Event> sendScreenCaptureEventHandler = null;
    private ArrayList<TIDevice> targetedDevices = new ArrayList();

    @Override
    public void init(DeviceExplorerInterface deviceExplorerInterface) {
        this.deviceExplorer = deviceExplorerInterface;
        super.init(CommonUISupportResourceBundle.BUNDLE);
        TransactionManager.getInstance().addTransactionQueueListener(new TransactionQueueListener(){

            @Override
            public void transactionQueueEmpty(TIDevice tIDevice) {
                DeviceList.this.busyDevice(tIDevice, false);
            }

            @Override
            public void transactionQueueNonEmpty(TIDevice tIDevice) {
                DeviceList.this.busyDevice(tIDevice, true);
            }
        });
    }

    @Override
    protected void handleDropOnDevice(TIDevice tIDevice, DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        boolean bl = false;
        if (dragboard.hasFiles()) {
            List list = dragboard.getFiles();
            if (tIDevice != null && list != null && !list.isEmpty()) {
                ArrayList<TIDevice> arrayList = new ArrayList<TIDevice>();
                arrayList.add(tIDevice);
                DeviceExplorerFactory.getDeviceExplorer().commonSendFilesToTIDevicesDropHandler(list, arrayList);
                bl = true;
            }
        } else if (dragboard.hasImage() || dragboard.hasHtml()) {
            if (dragEvent.getSource() != null && null != this.sendScreenCaptureEventHandler) {
                this.targetedDevices.clear();
                this.targetedDevices.add(tIDevice);
                this.sendScreenCaptureEventHandler.handle((Event)dragEvent);
            }
        } else {
            TIVar[] arrtIVar = DeviceExplorerFactory.getContentTable().getCurrentlyDraggingVars();
            if (arrtIVar != null && arrtIVar.length > 0) {
                ArrayList<TIDevice> arrayList = new ArrayList<TIDevice>();
                arrayList.add(tIDevice);
                this.deviceExplorer.getVarsFromDeviceToOtherDevices(this.getSelectedDevice(), arrtIVar, arrayList, SendToHandheldDialog.HandHeldTargets.SELECTED);
                bl = true;
            }
        }
        dragEvent.setDropCompleted(bl);
        dragEvent.consume();
    }

    @Override
    protected void handleDragOverDevice(TIDevice tIDevice, DragEvent dragEvent) {
        if (PromptDialog.isModalDialogActive()) {
            dragEvent.consume();
            return;
        }
        if (tIDevice != null && (dragEvent.getDragboard().hasFiles() || dragEvent.getDragboard().hasHtml() || dragEvent.getDragboard().hasImage()) && !this.getBusyDeviceSet().contains((Object)tIDevice)) {
            dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.COPY});
        } else {
            TIVar[] arrtIVar = DeviceExplorerFactory.getContentTable().getCurrentlyDraggingVars();
            if (arrtIVar != null && arrtIVar.length > 0 && this.getSelectedDevice() != tIDevice) {
                dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.COPY});
            }
        }
        dragEvent.consume();
    }

    @Override
    protected void handleEnterOnDeviceListItem(TIDevice tIDevice) {
        switch (this.getMode()) {
            case 0: {
                DeviceExplorerFactory.getContentTable().refreshCurrentTIDevice();
                break;
            }
            case 1: {
                super.notifyScreenCapture(tIDevice);
                break;
            }
        }
    }

    @Override
    protected void handleCancelOnDeviceListItem(TIDevice tIDevice) {
        if (DeviceExplorerFactory.getDeviceList().findDeviceHolder(tIDevice).cancelButtonEnabledProperty().getValue().booleanValue()) {
            DeviceExplorerFactory.getDeviceList().findDeviceHolder(tIDevice).setTransactionCanceled(true);
        }
    }

    @Override
    protected void handleDropOnPaneAllConnected(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        List<TIDevice> list = this.getConnectedDevices();
        list.removeAll(this.getBusyDeviceSet());
        boolean bl = false;
        if (dragboard.hasFiles()) {
            List list2 = dragboard.getFiles();
            if (list != null && !list.isEmpty() && list2 != null && !list2.isEmpty()) {
                DeviceExplorerFactory.getDeviceExplorer().commonSendFilesToTIDevicesDropHandler(list2, list);
                bl = true;
            }
        } else if (dragboard.hasImage() || dragboard.hasHtml()) {
            if (dragEvent.getSource() != null && null != this.sendScreenCaptureEventHandler) {
                this.targetedDevices.clear();
                this.targetedDevices.addAll(this.getConnectedDevices());
                this.sendScreenCaptureEventHandler.handle((Event)dragEvent);
            }
        } else {
            list.remove(this.getSelectedDevice());
            TIVar[] arrtIVar = DeviceExplorerFactory.getContentTable().getCurrentlyDraggingVars();
            if (arrtIVar != null && arrtIVar.length > 0 && !list.isEmpty()) {
                this.deviceExplorer.getVarsFromDeviceToOtherDevices(this.getSelectedDevice(), arrtIVar, list, SendToHandheldDialog.HandHeldTargets.ALL_CONNECTED);
                bl = true;
            }
        }
        dragEvent.setDropCompleted(bl);
        dragEvent.consume();
    }

    @Override
    protected void handleDragOverPaneAllConnected(DragEvent dragEvent) {
        if (PromptDialog.isModalDialogActive()) {
            dragEvent.consume();
            return;
        }
        List<TIDevice> list = this.getConnectedDevices();
        list.removeAll(this.getBusyDeviceSet());
        if (dragEvent.getDragboard().hasFiles() || dragEvent.getDragboard().hasImage() || dragEvent.getDragboard().hasHtml()) {
            if (!list.isEmpty()) {
                dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.COPY});
            }
        } else {
            list.remove(this.getSelectedDevice());
            TIVar[] arrtIVar = DeviceExplorerFactory.getContentTable().getCurrentlyDraggingVars();
            if (arrtIVar != null && arrtIVar.length > 0 && !list.isEmpty()) {
                dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.COPY});
            }
        }
        dragEvent.consume();
    }

    @Override
    protected boolean suppressAnimations() {
        return DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW;
    }

    @Override
    public void addDeviceSelectionListener(DeviceSelectionListener deviceSelectionListener) {
        this.deviceSelectionListeners.add(deviceSelectionListener);
    }

    @Override
    public void removeDeviceSelectionListener(DeviceSelectionListener deviceSelectionListener) {
        this.deviceSelectionListeners.remove(deviceSelectionListener);
    }

    @Override
    protected void notifyDeviceSelectionChanged(TIDevice tIDevice) {
        for (DeviceSelectionListener deviceSelectionListener : this.deviceSelectionListeners) {
            deviceSelectionListener.deviceSelected(tIDevice);
        }
        DeviceExplorerActionManager.getInstance().selectedDeviceBusy(!TransactionManager.getInstance().isTransactionQueueEmpty(tIDevice), tIDevice != null && tIDevice.isOSpresent(), tIDevice != null, tIDevice != null ? tIDevice.isUnderOSUpdate() : false);
    }

    @Override
    protected void busyDevice(TIDevice tIDevice, boolean bl) {
        super.busyDevice(tIDevice, bl);
        if (this.getSelectedDevice() == tIDevice) {
            DeviceExplorerActionManager.getInstance().selectedDeviceBusy(bl, tIDevice != null ? tIDevice.isOSpresent() : false, tIDevice != null, tIDevice != null ? tIDevice.isUnderOSUpdate() : false);
        }
        DeviceExplorerActionManager.getInstance().devicesAvailable(this.devicesAvailable(), this.getBusyDeviceSet().size());
    }

    @Override
    public int devicesAvailable() {
        List<TIDevice> list = this.getConnectedDevices();
        list.removeAll(this.getBusyDeviceSet());
        ArrayList<TIDevice> arrayList = new ArrayList<TIDevice>(list);
        for (TIDevice tIDevice : list) {
            if (tIDevice.isOSpresent() && !tIDevice.isUnderOSUpdate()) continue;
            arrayList.remove(tIDevice);
        }
        return arrayList.size();
    }

    @Override
    public int getMode() {
        return super.getMode();
    }

    @Override
    public void setMode(int n) {
        super.setMode(n);
    }

    @Override
    public void addTakeScreenListener(TakeScreenListener takeScreenListener) {
        super.addTakeScreenListener(takeScreenListener);
    }

    @Override
    public void addnotifyConnectedHandheldsListener(NotifyConnectedListener notifyConnectedListener) {
        this.notifyConnectedListener = notifyConnectedListener;
    }

    @Override
    protected void notifyConnectedHandhelds(int n) {
        if (n == 1) {
            if (this.notifyConnectedListener != null) {
                this.notifyConnectedListener.notifyConnectedOnScreenCapture();
            }
            DeviceExplorerFactory.getNothingConnectedPane().hidePane(DeviceExplorerFactory.getNothingConnectedPane().getRootNode());
        }
        DeviceExplorerActionManager.getInstance().devicesAvailable(this.devicesAvailable(), this.getBusyDeviceSet().size());
    }

    @Override
    protected void notifyDisconnectedHandhelds(int n) {
        if (n == 0) {
            if (this.notifyConnectedListener != null) {
                this.notifyConnectedListener.notifyDisconnectedOnScreenCapture();
            }
            DeviceExplorerFactory.getContentTable().showPane(DeviceExplorerFactory.getNothingConnectedPane().getRootNode());
        }
        DeviceExplorerActionManager.getInstance().devicesAvailable(this.devicesAvailable(), this.getBusyDeviceSet().size());
    }

    @Override
    public void setSendOSAction(TIAction tIAction) {
        if (tIAction != null) {
            this.sendOSMenuItem.setAction(tIAction);
        }
    }

    @Override
    public void setDeviceRefreshAction(TIAction tIAction) {
        if (tIAction != null) {
            this.refreshMenuItem.setAction(tIAction);
        }
    }

    @Override
    public void setHHInfoSAction(TIAction tIAction) {
        if (tIAction != null) {
            this.handheldInformationMenuItem.setAction(tIAction);
        }
    }

    @Override
    protected void notifyDeviceWithoutOSConnected(TIDevice tIDevice) {
        DeviceExplorerFactory.loadOverlayPane(tIDevice, IDOverlappingPanelsType.DISTRESS_DEVICE);
    }

    @Override
    protected void notifyDeviceWithoutOSDisonnected(TIDevice tIDevice) {
        DeviceExplorerFactory.unloadOverlayPane(tIDevice);
    }

    @Override
    public void setSendScreenCaptureEventHandler(EventHandler<Event> eventHandler) {
        this.sendScreenCaptureEventHandler = eventHandler;
    }

    @Override
    public ArrayList<TIDevice> getTargetedDevices() {
        return this.targetedDevices;
    }

}

