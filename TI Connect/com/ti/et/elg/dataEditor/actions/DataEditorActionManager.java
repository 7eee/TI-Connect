/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataEditor.actions;

import com.ti.et.elg.dataEditor.actions.OpenDataAction;
import com.ti.et.elg.dataEditor.actions.SaveDataAction;
import com.ti.et.elg.dataEditor.actions.SelectAllAction;

public class DataEditorActionManager {
    private boolean hasContent = false;

    public void setHasContent(boolean bl) {
        this.hasContent = bl;
    }

    public void updateActions() {
        if (this.hasContent) {
            SaveDataAction.getInstance().setDisabled(this.hasContent);
            SelectAllAction.getInstance().setDisabled(this.hasContent);
            OpenDataAction.getInstance().setDisabled(false);
        }
    }
}

