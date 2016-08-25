/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.Property
 *  javafx.beans.value.ObservableValue
 *  javafx.scene.Node
 *  javafx.scene.control.CheckBox
 *  javafx.scene.control.ListCell
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIDeviceData;
import com.ti.et.elg.connectServer.exports.TIDevice;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;

class DeviceCheckList
extends ListCell<TIDeviceData> {
    private boolean isSendOS = false;

    public DeviceCheckList(boolean bl) {
        this.isSendOS = bl;
    }

    protected void updateItem(TIDeviceData tIDeviceData, boolean bl) {
        super.updateItem((Object)tIDeviceData, bl);
        if (tIDeviceData != null) {
            CheckBox checkBox = new CheckBox();
            if (tIDeviceData.getTIDevice().isOSpresent()) {
                checkBox.setText(tIDeviceData.getTIDevice().getDeviceName() + "-" + tIDeviceData.getTIDevice().getConnectionIDforName());
                checkBox.disableProperty().bind((ObservableValue)tIDeviceData.busyProperty());
            } else {
                checkBox.setText(tIDeviceData.getTIDevice().getDeviceName());
                if (!this.isSendOS) {
                    checkBox.setDisable(true);
                }
            }
            checkBox.selectedProperty().bindBidirectional((Property)tIDeviceData.selectedProperty());
            tIDeviceData.setInstanceNode((Node)checkBox);
            this.setGraphic((Node)checkBox);
        }
    }
}

