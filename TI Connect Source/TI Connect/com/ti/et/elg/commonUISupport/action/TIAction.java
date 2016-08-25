/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.property.SimpleObjectProperty
 *  javafx.beans.property.SimpleStringProperty
 *  javafx.beans.property.StringProperty
 *  javafx.event.ActionEvent
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.action;

import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TIAction {
    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty disable = new SimpleBooleanProperty(false);
    private final BooleanProperty checkProperty = new SimpleBooleanProperty(false);
    private ObjectProperty<TIMenuItem> menuItem = null;
    private EventHandler<ActionEvent> handler;

    public String getName() {
        return this.name.getValue();
    }

    public void setName(String string) {
        this.name.setValue(string);
    }

    public boolean getDisabled() {
        return this.disable.getValue();
    }

    public void setDisabled(boolean bl) {
        this.disable.setValue(Boolean.valueOf(bl));
    }

    public BooleanProperty disableProperty() {
        return this.disable;
    }

    public void setEventHandler(EventHandler<ActionEvent> eventHandler) {
        this.handler = eventHandler;
    }

    public EventHandler<ActionEvent> getEventHandler() {
        return this.handler;
    }

    public BooleanProperty checkProperty() {
        return this.checkProperty;
    }

    public boolean getCheckProperty() {
        return this.checkProperty.getValue();
    }

    public TIMenuItem getMenuItem() {
        if (null != this.menuItem) {
            return (TIMenuItem)this.menuItem.get();
        }
        return null;
    }

    public void setMenuItem(TIMenuItem tIMenuItem) {
        if (null != tIMenuItem) {
            if (null == this.menuItem) {
                this.menuItem = new SimpleObjectProperty((Object)tIMenuItem);
            } else {
                this.menuItem.setValue((Object)tIMenuItem);
            }
        } else {
            this.menuItem = null;
        }
    }
}

