/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import com.ti.et.elg.screenCapture.actions.AddScreenBorderAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class RemoveScreenBorderAction
extends TIAction {
    private static final RemoveScreenBorderAction INSTANCE = new RemoveScreenBorderAction();
    private static final String ADD_BORDERS_MENU_TITLE = CommonUISupportResourceBundle.BUNDLE.getString("topLevelMenuItem.actions.captureWithoutBorders");

    private RemoveScreenBorderAction() {
        this.setDisabled(false);
        this.setName(RemoveScreenBorderAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                ScreenCaptureFactory.getScreenCaptureContainer().setScreenBorders(false);
                RemoveScreenBorderAction.getInstance().getMenuItem().setTitle(ADD_BORDERS_MENU_TITLE);
                RemoveScreenBorderAction.getInstance().getMenuItem().setAction(AddScreenBorderAction.getInstance());
                TILogger.logInfo("RemoveScreenBorderAction", "Executing RemoveScreenBorderAction");
            }
        });
    }

    public static RemoveScreenBorderAction getInstance() {
        return INSTANCE;
    }

}

