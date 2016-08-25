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
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SelectAllAction
extends TIAction {
    private static final SelectAllAction INSTANCE = new SelectAllAction();

    private SelectAllAction() {
        this.setDisabled(true);
        this.setName(SelectAllAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                DeviceExplorerFactory.getContentTable().selectAll();
            }
        });
    }

    public static SelectAllAction getInstance() {
        return INSTANCE;
    }

}

