/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.dataEditor.exports;

import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import java.io.File;
import java.util.List;
import javafx.scene.Node;

public interface DataEditorContentsInterface {
    public void init();

    public Node getRootNode();

    public Node getFocusableNode();

    public void setParent(OverlayStackPaneBase var1);

    public OverlayStackPaneBase getParent();

    public void showPane(Node var1);

    public void hidePane(Node var1);

    public void openFromFile(File var1);

    public void openFiles(List<File> var1);

    public void saveContents(File var1);
}

