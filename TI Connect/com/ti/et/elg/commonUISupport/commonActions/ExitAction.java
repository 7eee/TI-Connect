/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.commonActions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ExitAction
extends TIAction {
    private static final ExitAction INSTANCE = new ExitAction();
    private Runnable closeRoutine;

    private ExitAction() {
        this.setName(ExitAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                if (ExitAction.this.closeRoutine != null) {
                    Platform.runLater((Runnable)ExitAction.this.closeRoutine);
                }
            }
        });
    }

    public static ExitAction getInstance() {
        return INSTANCE;
    }

    public void setClosingRoutine(Runnable runnable) {
        this.closeRoutine = runnable;
    }

}

