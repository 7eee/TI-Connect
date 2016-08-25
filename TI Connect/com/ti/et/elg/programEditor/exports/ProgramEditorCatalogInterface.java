/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.programEditor.exports;

import javafx.scene.Node;

public interface ProgramEditorCatalogInterface {
    public Node getRootNode();

    public Node getDragableNode();

    public boolean isDragging();

    public void init();

    public void setDisabled(boolean var1);

    public Node getFocusableNode();
}

