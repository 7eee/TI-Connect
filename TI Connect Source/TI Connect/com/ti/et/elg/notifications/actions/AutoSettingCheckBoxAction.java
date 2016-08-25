/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.control.CheckBox
 */
package com.ti.et.elg.notifications.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class AutoSettingCheckBoxAction
extends TIAction {
    private static final AutoSettingCheckBoxAction INSTANCE = new AutoSettingCheckBoxAction();

    private AutoSettingCheckBoxAction() {
        this.setDisabled(false);
        this.setName(AutoSettingCheckBoxAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                if (actionEvent.getSource() instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox)actionEvent.getSource();
                    boolean bl = checkBox.isSelected();
                    UserPropertyManagement.setUserProperty("autoUpgradeEnabled", bl ? "1" : "0");
                }
            }
        });
    }

    public static AutoSettingCheckBoxAction getInstance() {
        return INSTANCE;
    }

}

