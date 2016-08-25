/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.commonActions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.utils.logger.TILogger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class GetHHInformationAction
extends TIAction {
    private static final GetHHInformationAction INSTANCE = new GetHHInformationAction();

    private GetHHInformationAction() {
        this.setDisabled(true);
        this.setName(GetHHInformationAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logInfo("GetHHInformationAction", "Executing GetHHInformationAction");
            }
        });
    }

    public static GetHHInformationAction getInstance() {
        return INSTANCE;
    }

}

