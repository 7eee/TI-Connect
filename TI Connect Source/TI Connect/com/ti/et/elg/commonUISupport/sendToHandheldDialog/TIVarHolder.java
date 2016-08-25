/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.scene.Node
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.imageUtils.TIVarMultiplexer;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarTableData;
import com.ti.et.elg.connectServer.exports.TIVar;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;

public class TIVarHolder {
    private TIVarMultiplexer tiVarMux;
    private VarTableData parent;
    private BooleanProperty duplicatedProperty = new SimpleBooleanProperty(false);
    private BooleanProperty deletedProperty = new SimpleBooleanProperty(false);
    private Node firstNode;
    private Node middleNode;
    private Node lastNode;

    public TIVarHolder(TIVarMultiplexer tIVarMultiplexer) {
        this.tiVarMux = tIVarMultiplexer;
    }

    public BooleanProperty duplicatedProperty() {
        return this.duplicatedProperty;
    }

    public BooleanProperty deletedProperty() {
        return this.deletedProperty;
    }

    public TIVar getTIVar() {
        return this.tiVarMux.getTIVar();
    }

    public TIVarMultiplexer getTIVarMux() {
        return this.tiVarMux;
    }

    public String toString() {
        return this.tiVarMux.getTIVar().getDeviceFileName();
    }

    public Node getFirstNode() {
        return this.firstNode;
    }

    public void setFirstNode(Node node) {
        this.firstNode = node;
    }

    public Node getMiddleNode() {
        return this.middleNode;
    }

    public void setMiddleNode(Node node) {
        this.middleNode = node;
    }

    public Node getLastNode() {
        return this.lastNode;
    }

    public void setLastNode(Node node) {
        this.lastNode = node;
    }

    public VarTableData getParent() {
        return this.parent;
    }

    public void setParent(VarTableData varTableData) {
        this.parent = varTableData;
    }
}

