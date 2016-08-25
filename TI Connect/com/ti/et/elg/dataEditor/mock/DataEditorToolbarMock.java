/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 *  javafx.scene.layout.BorderPane
 */
package com.ti.et.elg.dataEditor.mock;

import com.ti.et.elg.dataEditor.exports.DataEditorToolbarInterface;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class DataEditorToolbarMock
implements DataEditorToolbarInterface {
    private Node mockRootNode = new BorderPane();

    @Override
    public Node getRootNode() {
        return this.mockRootNode;
    }

    @Override
    public void init() {
    }
}

