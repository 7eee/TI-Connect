/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.input.Clipboard
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.utils.logger.TILogger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.Clipboard;

public class CopyAction
extends TIAction {
    private static final CopyAction INSTANCE = new CopyAction();

    private CopyAction() {
        this.setDisabled(true);
        this.setName(CopyAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                ScreenCaptureFactory.getScreenCaptureContainer().copySelectedItemsToClipboard(null);
                TILogger.logInfo("CopyAction", "Executing CopyAction");
            }
        });
    }

    public static CopyAction getInstance() {
        return INSTANCE;
    }

}

