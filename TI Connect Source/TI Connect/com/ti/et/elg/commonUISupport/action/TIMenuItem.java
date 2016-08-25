/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.Property
 *  javafx.beans.value.ObservableValue
 *  javafx.event.ActionEvent
 *  javafx.event.EventHandler
 *  javafx.scene.control.CheckMenuItem
 *  javafx.scene.control.MenuItem
 *  javafx.scene.control.RadioMenuItem
 *  javafx.scene.control.ToggleGroup
 *  javafx.scene.input.KeyCodeCombination
 *  javafx.scene.input.KeyCombination
 */
package com.ti.et.elg.commonUISupport.action;

import com.ti.et.elg.commonUISupport.action.TIAction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class TIMenuItem {
    private TIAction action;
    private MenuItem menuItem;
    private boolean isCheckBox;
    private boolean isRadioMenu;

    public TIMenuItem(String string) {
        this(string, false);
    }

    public TIMenuItem(String string, boolean bl) {
        this.menuItem = bl ? new CheckMenuItem(string) : new MenuItem(string);
        this.isCheckBox = bl;
        this.menuItem.setDisable(true);
    }

    public TIMenuItem(String string, ToggleGroup toggleGroup) {
        this.isRadioMenu = true;
        this.menuItem = new RadioMenuItem(string);
        ((RadioMenuItem)this.menuItem).setToggleGroup(toggleGroup);
        this.menuItem.setDisable(true);
    }

    public void setAction(TIAction tIAction) {
        if (this.action != null) {
            if (this.isCheckBox) {
                this.action.checkProperty().unbindBidirectional((Property)((CheckMenuItem)this.menuItem).selectedProperty());
            }
            if (this.isRadioMenu) {
                this.action.checkProperty().unbindBidirectional((Property)((RadioMenuItem)this.menuItem).selectedProperty());
            }
            this.action.setMenuItem(null);
            this.menuItem.disableProperty().unbind();
            this.menuItem.setDisable(true);
            this.menuItem.setOnAction(null);
        }
        this.action = tIAction;
        if (tIAction != null) {
            tIAction.setMenuItem(this);
            this.menuItem.setOnAction(tIAction.getEventHandler());
            this.menuItem.disableProperty().bind((ObservableValue)tIAction.disableProperty());
            if (this.isCheckBox) {
                tIAction.checkProperty().bindBidirectional((Property)((CheckMenuItem)this.menuItem).selectedProperty());
            }
            if (this.isRadioMenu) {
                tIAction.checkProperty().bindBidirectional((Property)((RadioMenuItem)this.menuItem).selectedProperty());
            }
        }
    }

    public TIAction getAction() {
        return this.action;
    }

    public void setAccelerator(KeyCodeCombination keyCodeCombination) {
        this.menuItem.setAccelerator((KeyCombination)keyCodeCombination);
    }

    public void setId(String string) {
        this.menuItem.setId(string);
    }

    public MenuItem getMenuItem() {
        return this.menuItem;
    }

    public void setTitle(String string) {
        if (null != string && !string.isEmpty()) {
            this.menuItem.setText(string);
        }
    }
}

