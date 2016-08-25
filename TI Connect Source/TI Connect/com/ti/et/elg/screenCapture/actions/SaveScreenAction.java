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

public class SaveScreenAction
extends TIAction {
    private static final SaveScreenAction INSTANCE = new SaveScreenAction();
    private final String TAG = "SaveScreenAction";

    private SaveScreenAction() {
        this.setDisabled(true);
        this.setName(SaveScreenAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail("SaveScreenAction", "Executing Save Action");
                ScreenCaptureFactory.getScreenCaptureContainer().saveSelectedItemsToDisk();
            }
        });
    }

    public static SaveScreenAction getInstance() {
        return INSTANCE;
    }

}

