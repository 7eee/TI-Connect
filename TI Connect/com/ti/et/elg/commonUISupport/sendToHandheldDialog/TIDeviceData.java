/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.IntegerProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.property.SimpleIntegerProperty
 *  javafx.beans.property.SimpleStringProperty
 *  javafx.beans.property.StringProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.scene.Node
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.connectServer.exports.TIDevice;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class TIDeviceData {
    private final StringProperty deviceName = new SimpleStringProperty("");
    private static final IntegerProperty numberOfSelectedDevices = new SimpleIntegerProperty(0);
    private final BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty busyProperty = new SimpleBooleanProperty(false);
    private TIDevice tiDevice = null;
    private Node instanceNode;

    public TIDeviceData(TIDevice tIDevice) {
        this.tiDevice = tIDevice;
        this.deviceName.setValue(tIDevice.getDeviceName());
        this.selectedProperty.addListener((ChangeListener)new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                if (bl2.booleanValue()) {
                    numberOfSelectedDevices.set(numberOfSelectedDevices.get() + 1);
                } else {
                    numberOfSelectedDevices.set(numberOfSelectedDevices.get() - 1);
                }
            }
        });
    }

    public TIDevice getTIDevice() {
        return this.tiDevice;
    }

    public StringProperty deviceNameProperty() {
        return this.deviceName;
    }

    public String toString() {
        return this.tiDevice.getDeviceName() + " - " + this.tiDevice.getConnectionIDforName();
    }

    public BooleanProperty selectedProperty() {
        return this.selectedProperty;
    }

    public static IntegerProperty numberOfSelectedDevices() {
        return numberOfSelectedDevices;
    }

    public BooleanProperty busyProperty() {
        return this.busyProperty;
    }

    public Node getInstanceNode() {
        return this.instanceNode;
    }

    public void setInstanceNode(Node node) {
        this.instanceNode = node;
    }

}

