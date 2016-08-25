/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableSet
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.deviceExplorer.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.deviceList.DeviceListBase;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.utils.logger.TILogger;
import java.util.Collection;
import java.util.List;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SendToHHAction
extends TIAction {
    private static final SendToHHAction INSTANCE = new SendToHHAction();

    private SendToHHAction() {
        this.setDisabled(true);
        this.setName(SendToHHAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logInfo("SendToHHAction", "Executing SendToHHAction");
                TIVar[] arrtIVar = DeviceExplorerFactory.getContentTable().getCurrentlySelectedVars();
                if (arrtIVar.length > 0) {
                    List<TIDevice> list = DeviceExplorerFactory.getDeviceList().getConnectedDevices();
                    list.removeAll(((DeviceListBase)((Object)DeviceExplorerFactory.getDeviceList())).getBusyDeviceSet());
                    TIDevice tIDevice = ((DeviceListBase)((Object)DeviceExplorerFactory.getDeviceList())).getSelectedDevice();
                    list.remove(tIDevice);
                    if (!list.isEmpty()) {
                        DeviceExplorerFactory.getDeviceExplorer().getVarsFromDeviceToOtherDevices(tIDevice, arrtIVar, list, SendToHandheldDialog.HandHeldTargets.ALL_CONNECTED);
                    }
                }
            }
        });
    }

    public static SendToHHAction getInstance() {
        return INSTANCE;
    }

}

