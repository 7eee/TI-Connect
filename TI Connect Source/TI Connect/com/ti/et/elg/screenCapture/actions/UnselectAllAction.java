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

public class UnselectAllAction
extends TIAction {
    private static final UnselectAllAction INSTANCE = new UnselectAllAction();

    private UnselectAllAction() {
        this.setDisabled(true);
        this.setName(UnselectAllAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                ScreenCaptureFactory.getScreenCaptureContainer().unselectAllItems();
                TILogger.logInfo("UnselectAllAction", "Executing UnselectAllAction");
            }
        });
    }

    public static UnselectAllAction getInstance() {
        return INSTANCE;
    }

}

