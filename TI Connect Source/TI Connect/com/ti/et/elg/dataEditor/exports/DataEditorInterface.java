/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataEditor.exports;

import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;

public interface DataEditorInterface {
    public void init(WorkspaceManagerInterface var1);

    public void notifyIsActive(boolean var1);

    public void shutdown();
}

