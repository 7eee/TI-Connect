/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.value.ObservableValue
 *  javafx.event.ActionEvent
 *  javafx.event.EventHandler
 *  javafx.scene.control.Button
 */
package com.ti.et.elg.commonUISupport.action;

import com.ti.et.elg.commonUISupport.action.TIAction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class TIButton
extends Button {
    private TIAction action;

    public void setAction(TIAction tIAction) {
        if (this.action != null) {
            this.disableProperty().unbind();
            this.setDisable(true);
            this.setOnAction(null);
        }
        this.action = tIAction;
        if (tIAction != null) {
            this.setOnAction(tIAction.getEventHandler());
            this.disableProperty().bind((ObservableValue)tIAction.disableProperty());
        }
    }

    public TIAction getAction() {
        return this.action;
    }
}

