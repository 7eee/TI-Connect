/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.commonUISupport.overlayPanes;

import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import javafx.scene.Node;

public interface OverlayBaseInterface {
    public void init();

    public String getFxmlFileName();

    public Node getRootNode();

    public void setParent(OverlayStackPaneBase var1);

    public void hidePane(Node var1);
}

