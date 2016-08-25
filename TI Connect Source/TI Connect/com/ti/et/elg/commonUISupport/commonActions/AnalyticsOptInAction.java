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
import com.ti.et.elg.commonUISupport.analyticsOptInDialog.AnalyticsOptInDialog;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class AnalyticsOptInAction
extends TIAction {
    private static final AnalyticsOptInAction INSTANCE = new AnalyticsOptInAction();

    public static AnalyticsOptInAction getInstance() {
        return INSTANCE;
    }

    private AnalyticsOptInAction() {
        this.setName(AnalyticsOptInAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                AnalyticsOptInDialog analyticsOptInDialog = new AnalyticsOptInDialog();
                analyticsOptInDialog.showAndWait();
            }
        });
    }

}

