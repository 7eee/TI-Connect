/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 *  javafx.scene.layout.BorderPane
 */
package com.ti.et.elg.dataEditor;

import com.ti.et.elg.dataEditor.exports.DataEditorLeftListInterface;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class DataEditorLeftList
implements DataEditorLeftListInterface {
    private BorderPane root = new BorderPane();

    @Override
    public Node getRootNode() {
        return this.root;
    }

    @Override
    public void init() {
    }
}

