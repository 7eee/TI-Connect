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
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.utils.logger.TILogger;
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
                ScreenCaptureFactory.getScreenCaptureContainer().sendSelectedItemsToHandhelds((Event)actionEvent);
                TILogger.logInfo("SendToHHAction", "Executing SendToHHAction");
            }
        });
    }

    public static SendToHHAction getInstance() {
        return INSTANCE;
    }

}

