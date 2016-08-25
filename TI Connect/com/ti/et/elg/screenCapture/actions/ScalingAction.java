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

public abstract class ScalingAction
extends TIAction {
    protected int scale;
    protected String tag;

    protected void init() {
        this.setDisabled(false);
        this.setName(this.tag);
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                ScreenCaptureFactory.getScreenCaptureToolbar().changeScreenCaptureScaling(ScalingAction.this.scale);
                TILogger.logDetail(ScalingAction.this.tag, "Executing " + ScalingAction.this.tag);
            }
        });
    }

}

