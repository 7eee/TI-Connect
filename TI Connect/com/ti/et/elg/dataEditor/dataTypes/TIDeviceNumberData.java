/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.SimpleDoubleProperty
 */
package com.ti.et.elg.dataEditor.dataTypes;

import javafx.beans.property.SimpleDoubleProperty;

public class TIDeviceNumberData {
    private SimpleDoubleProperty realNumber = new SimpleDoubleProperty();
    private SimpleDoubleProperty imaginaryNumber = new SimpleDoubleProperty();

    public TIDeviceNumberData() {
        this.realNumber.set(0.0);
        this.imaginaryNumber.set(0.0);
    }

    public Double getRealNumber() {
        return this.realNumber.getValue();
    }

    public void setRealNumber(Double d) {
        this.realNumber.setValue((Number)d);
    }

    public Double getImaginaryNumber() {
        return this.imaginaryNumber.getValue();
    }

    public void setImaginaryNumber(Double d) {
        this.imaginaryNumber.setValue((Number)d);
    }

    public void setData(byte[] arrby) {
    }
}

