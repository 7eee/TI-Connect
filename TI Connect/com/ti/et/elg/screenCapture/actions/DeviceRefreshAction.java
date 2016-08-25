/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.utils.logger.TILogger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class DeviceRefreshAction
extends TIAction {
    private static final DeviceRefreshAction INSTANCE = new DeviceRefreshAction();

    private DeviceRefreshAction() {
        this.setDisabled(true);
        this.setName(DeviceRefreshAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logInfo("DeviceRefreshAction", "Executing DeviceRefreshAction");
            }
        });
    }

    public static DeviceRefreshAction getInstance() {
        return INSTANCE;
    }

}

