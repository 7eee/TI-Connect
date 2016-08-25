/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.SimpleBooleanProperty
 */
package com.ti.et.elg.deviceExplorer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class NameTIVar {
    private String name;
    private String iconFile;
    private BooleanProperty isSelected = new SimpleBooleanProperty();

    public NameTIVar(String string, String string2) {
        this.name = string;
        this.iconFile = string2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getIconFile() {
        return this.iconFile;
    }

    public void setIconFile(String string) {
        this.iconFile = string;
    }

    public BooleanProperty getIsSelectedProperty() {
        return this.isSelected;
    }

    public void setIsSelectedProperty(boolean bl) {
        this.isSelected.setValue(Boolean.valueOf(bl));
    }

    public Boolean getIsSelected() {
        return this.isSelected.getValue();
    }
}

