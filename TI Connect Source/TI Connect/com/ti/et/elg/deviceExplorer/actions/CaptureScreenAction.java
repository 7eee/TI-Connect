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
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.utils.logger.TILogger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CaptureScreenAction
extends TIAction {
    private static final CaptureScreenAction INSTANCE = new CaptureScreenAction();
    private WorkspaceManagerInterface wsManager;

    private CaptureScreenAction() {
        this.setDisabled(true);
        this.setName(CaptureScreenAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                CaptureScreenAction.this.wsManager.switchToWorkspace(WorkspaceManagerInterface.WorkspaceType.SCREEN_CAPTURE);
                DeviceExplorerFactory.getDeviceList().notifyScreenCapture();
                TILogger.logInfo("CaptureScreenAction", "Executing CaptureScreenAction");
            }
        });
    }

    public static CaptureScreenAction getInstance() {
        return INSTANCE;
    }

    public void setWorkSpacesManager(WorkspaceManagerInterface workspaceManagerInterface) {
        this.wsManager = workspaceManagerInterface;
    }

}

