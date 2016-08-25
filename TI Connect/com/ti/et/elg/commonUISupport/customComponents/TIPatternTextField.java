/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.scene.control.TextField
 */
package com.ti.et.elg.commonUISupport.customComponents;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;

public class TIPatternTextField
extends TextField {
    private SimpleBooleanProperty validContent = new SimpleBooleanProperty(false);
    private String customNamePattern = null;
    private TextField internalValue = new TextField();

    public void setCustomNamePattern(String string) {
        this.customNamePattern = string;
        if (this.getText().matches(string)) {
            this.validContent.set(false);
        }
    }

    public void replaceText(int n, int n2, String string) {
        this.internalValue.setText(this.getText());
        this.internalValue.replaceText(n, n2, string);
        String string2 = this.internalValue.getText();
        if (string2.matches(this.customNamePattern)) {
            super.replaceText(n, n2, string);
        } else if ("".equals(string2)) {
            super.replaceText(n, n2, string);
        }
        this.validContentProperty().set(this.getText().matches(this.customNamePattern));
    }

    public void replaceSelection(String string) {
        this.internalValue.setText(this.getText());
        this.internalValue.replaceSelection(string);
        String string2 = this.internalValue.getText();
        if (string2.matches(this.customNamePattern)) {
            super.replaceSelection(string);
        } else if ("".equals(string2)) {
            super.replaceSelection(string);
        }
        this.validContentProperty().set(this.getText().matches(this.customNamePattern));
    }

    public SimpleBooleanProperty validContentProperty() {
        return this.validContent;
    }
}

