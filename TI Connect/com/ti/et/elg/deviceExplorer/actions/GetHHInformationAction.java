/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.deviceExplorer.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.deviceList.DeviceListBase;
import com.ti.et.elg.commonUISupport.handheldInformationDialog.HandheldInformationDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class GetHHInformationAction
extends TIAction {
    private static final GetHHInformationAction INSTANCE = new GetHHInformationAction();

    private GetHHInformationAction() {
        this.setDisabled(true);
        this.setName(GetHHInformationAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TIDevice tIDevice = ((DeviceListBase)((Object)DeviceExplorerFactory.getDeviceList())).getSelectedDevice();
                HandheldInformationDialog handheldInformationDialog = new HandheldInformationDialog(tIDevice);
                handheldInformationDialog.showAndWait(DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_CONNECT);
            }
        });
    }

    public static GetHHInformationAction getInstance() {
        return INSTANCE;
    }

}

