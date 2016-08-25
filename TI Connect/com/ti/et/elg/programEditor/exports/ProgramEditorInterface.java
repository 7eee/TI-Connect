/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.programEditor.exports;

import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;

public interface ProgramEditorInterface {
    public void init(WorkspaceManagerInterface var1);

    public void notifyIsActive(boolean var1);

    public boolean shutdown();

    public void updateFocusableNodes();
}

