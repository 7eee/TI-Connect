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
import com.ti.et.elg.screenCapture.actions.RemoveScreenBorderAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class AddScreenBorderAction
extends TIAction {
    private static final AddScreenBorderAction INSTANCE = new AddScreenBorderAction();
    private static final String REMOVE_BORDERS_MENU_TITLE = CommonUISupportResourceBundle.BUNDLE.getString("topLevelMenuItem.actions.captureWithBorders");

    private AddScreenBorderAction() {
        this.setDisabled(false);
        this.setName(AddScreenBorderAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                ScreenCaptureFactory.getScreenCaptureContainer().setScreenBorders(true);
                AddScreenBorderAction.getInstance().getMenuItem().setTitle(REMOVE_BORDERS_MENU_TITLE);
                AddScreenBorderAction.getInstance().getMenuItem().setAction(RemoveScreenBorderAction.getInstance());
                TILogger.logInfo("AddScreenBorderAction", "Executing AddScreenBorderAction");
            }
        });
    }

    public static AddScreenBorderAction getInstance() {
        return INSTANCE;
    }

}

