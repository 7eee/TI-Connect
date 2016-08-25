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

public class VisitActivitiesExchangeAction
extends TIAction {
    private static final VisitActivitiesExchangeAction INSTANCE = new VisitActivitiesExchangeAction();

    private VisitActivitiesExchangeAction() {
        this.setName(VisitActivitiesExchangeAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                CommonConstants.hostServices.showDocument("http://education.ti.com/go/exchange");
                TILogger.logInfo("VisitActivitiesExchangeAction", "Executing VisitActivitiesExchangeAction");
            }
        });
    }

    public static VisitActivitiesExchangeAction getInstance() {
        return INSTANCE;
    }

}

