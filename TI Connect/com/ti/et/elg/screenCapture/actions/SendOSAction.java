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

public class SendOSAction
extends TIAction {
    private static final SendOSAction INSTANCE = new SendOSAction();

    private SendOSAction() {
        this.setDisabled(true);
        this.setName(SendOSAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logInfo("SendOSAction", "Executing SendOSAction");
            }
        });
    }

    public static SendOSAction getInstance() {
        return INSTANCE;
    }

}

