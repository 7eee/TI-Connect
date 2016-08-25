/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.commonActions;

import com.ti.et.elg.commonUISupport.aboutTIConnectDialog.AboutTIConnectDialog;
import com.ti.et.elg.commonUISupport.action.TIAction;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class AboutAction
extends TIAction {
    private static final AboutAction INSTANCE = new AboutAction();

    private AboutAction() {
        this.setName(AboutAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                AboutTIConnectDialog aboutTIConnectDialog = new AboutTIConnectDialog();
                aboutTIConnectDialog.showAndWait();
            }
        });
    }

    public static AboutAction getInstance() {
        return INSTANCE;
    }

}

