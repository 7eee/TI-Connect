/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.dataEditor.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SaveDataAction
extends TIAction {
    private static final SaveDataAction INSTANCE = new SaveDataAction();
    private static final String TAG = SaveDataAction.class.getSimpleName();

    private SaveDataAction() {
        this.setDisabled(false);
        this.setName(TAG);
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
            }
        });
    }

    public static SaveDataAction getInstance() {
        return INSTANCE;
    }

}

