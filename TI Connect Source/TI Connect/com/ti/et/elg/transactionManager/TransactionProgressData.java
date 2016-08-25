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
 */
package com.ti.et.elg.transactionManager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransactionProgressData {
    private final StringProperty currentFileName = new SimpleStringProperty("");
    private final IntegerProperty percentage = new SimpleIntegerProperty(0);
    private final IntegerProperty itemBeingDone = new SimpleIntegerProperty(0);
    private final IntegerProperty itemsTotal = new SimpleIntegerProperty(0);
    private final IntegerProperty status = new SimpleIntegerProperty(0);
    private final BooleanProperty cancel = new SimpleBooleanProperty(false);
    private boolean done = false;

    public StringProperty fileNameProperty() {
        return this.currentFileName;
    }

    public IntegerProperty percentageProperty() {
        return this.percentage;
    }

    public IntegerProperty itemBeingDoneProperty() {
        return this.itemBeingDone;
    }

    public IntegerProperty itemsTotalProperty() {
        return this.itemsTotal;
    }

    public IntegerProperty statusProperty() {
        return this.status;
    }

    public BooleanProperty cancelProperty() {
        return this.cancel;
    }

    public String getCurrentFileName() {
        return (String)this.currentFileName.get();
    }

    public void setCurrentFileName(String string) {
        this.currentFileName.set((Object)string);
    }

    public int getPercentage() {
        return this.percentage.get();
    }

    public void setPercentage(int n) {
        this.percentage.set(n);
    }

    public int getItemBeingDone() {
        return this.itemBeingDone.get();
    }

    public void setItemBeingDone(int n) {
        this.itemBeingDone.set(n);
    }

    public int getItemsTotal() {
        return this.itemsTotal.get();
    }

    public void setItemsTotal(int n) {
        this.itemsTotal.set(n);
    }

    public int getStatus() {
        return this.status.get();
    }

    public void setStatus(int n) {
        this.status.set(n);
    }

    public boolean getCancel() {
        return this.cancel.get();
    }

    public void setCancel(boolean bl) {
        this.cancel.set(bl);
    }

    public void setDone() {
        this.done = true;
        this.setCancel(true);
    }

    public boolean isDone() {
        return this.done;
    }
}

