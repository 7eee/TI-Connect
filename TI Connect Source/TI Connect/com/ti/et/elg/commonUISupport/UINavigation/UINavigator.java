/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.commonUISupport.UINavigation;

import javafx.scene.Node;

public interface UINavigator {
    public boolean addNodeToNavCycle(Node var1);

    public boolean removeNodeFromNavCycle(Node var1);

    public void startAtPos(int var1);

    public int getListSize();

    public int getCurrentPos();

    public void enable();

    public void disable();

    public void setEnabled(boolean var1);

    public void fireFocusChange(Node var1);
}

