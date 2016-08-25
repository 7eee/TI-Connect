/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.EventHandler
 *  javafx.scene.control.CheckBox
 */
package com.ti.et.elg.notifications.gui;

import com.ti.et.elg.notifications.AutomaticNotificationResourceBundle;
import com.ti.et.elg.notifications.actions.AutoSettingCheckBoxAction;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class AutomaticNotificationCheckBox {
    private static AutomaticNotificationCheckBox checkBox = new AutomaticNotificationCheckBox();
    private CheckBox autoSettingCheckBox = new CheckBox(AutomaticNotificationResourceBundle.BUNDLE.getString("automaticNotificationDialog.check.label"));

    public static AutomaticNotificationCheckBox getInstance() {
        return checkBox;
    }

    private AutomaticNotificationCheckBox() {
        boolean bl = false;
        String string = UserPropertyManagement.getUserProperty("autoUpgradeEnabled");
        if (string != null) {
            bl = string.equals("1");
        }
        this.autoSettingCheckBox.setOnAction(AutoSettingCheckBoxAction.getInstance().getEventHandler());
        this.autoSettingCheckBox.setSelected(bl);
        this.autoSettingCheckBox.setId("chkbAutomaticNotification");
    }

    public CheckBox getAutoSettingCheckBox() {
        return this.autoSettingCheckBox;
    }
}

