/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.HostServices
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.commonActions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.utils.logger.TILogger;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class OnlineTroubleshootingAction
extends TIAction {
    private static final OnlineTroubleshootingAction INSTANCE = new OnlineTroubleshootingAction();

    private OnlineTroubleshootingAction() {
        this.setName(OnlineTroubleshootingAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                CommonConstants.hostServices.showDocument("http://education.ti.com/go/kbase");
                TILogger.logInfo("OnlineTroubleshootingAction", "Executing OnlineTroubleshootingAction");
            }
        });
    }

    public static OnlineTroubleshootingAction getInstance() {
        return INSTANCE;
    }

}

