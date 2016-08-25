/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.HostServices
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.commonActions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.utils.logger.TILogger;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ContactTISupportAction
extends TIAction {
    private static final ContactTISupportAction INSTANCE = new ContactTISupportAction();

    private ContactTISupportAction() {
        this.setName(ContactTISupportAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                CommonConstants.hostServices.showDocument("http://education.ti.com/support");
                TILogger.logInfo("ContactTISupportAction", "Executing ContactTISupportAction");
            }
        });
    }

    public static ContactTISupportAction getInstance() {
        return INSTANCE;
    }

}

