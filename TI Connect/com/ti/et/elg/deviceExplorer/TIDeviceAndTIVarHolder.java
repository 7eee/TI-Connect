/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.SimpleStringProperty
 *  javafx.beans.property.StringProperty
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TIDeviceAndTIVarHolder {
    boolean deviceRefreshing = false;
    private String originalName = null;
    private StringProperty name = new SimpleStringProperty("");
    private int type;
    private TIDevice tiDevice = null;
    private TIVar tiVar = null;
    public static final int ALL_DEVICES = 0;
    public static final int DEVICE = 1;
    public static final int VAR = 2;

    public TIDeviceAndTIVarHolder(String string, int n) {
        this.originalName = string;
        this.name.setValue(string);
        this.type = n;
    }

    public TIDeviceAndTIVarHolder(String string, int n, TIDevice tIDevice) {
        this.originalName = string;
        this.name.setValue(string);
        this.type = n;
        this.tiDevice = tIDevice;
    }

    public TIDeviceAndTIVarHolder(String string, int n, TIVar tIVar) {
        this.originalName = string;
        this.name.setValue(string);
        this.type = n;
        this.tiVar = tIVar;
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getName() {
        return this.name.getValue();
    }

    public int getType() {
        return this.type;
    }

    public TIDevice getTiDevice() {
        return this.tiDevice;
    }

    public TIVar getTiVar() {
        return this.tiVar;
    }

    public void setRefreshing(boolean bl) {
        this.deviceRefreshing = bl;
        if (bl) {
            this.name.setValue(this.originalName + " - refreshing...");
        } else {
            this.name.setValue(this.originalName);
        }
    }

    public String toString() {
        return "Name: " + (Object)this.name + " Type: " + this.type;
    }
}

