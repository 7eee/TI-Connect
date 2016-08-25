/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataEditor.factory;

import com.ti.et.elg.dataEditor.DataEditorContents;
import com.ti.et.elg.dataEditor.DataEditorController;
import com.ti.et.elg.dataEditor.DataEditorLeftList;
import com.ti.et.elg.dataEditor.DataEditorToolbar;
import com.ti.et.elg.dataEditor.exports.DataEditorContentsInterface;
import com.ti.et.elg.dataEditor.exports.DataEditorInterface;
import com.ti.et.elg.dataEditor.exports.DataEditorLeftListInterface;
import com.ti.et.elg.dataEditor.exports.DataEditorToolbarInterface;
import com.ti.et.elg.dataEditor.mock.DataEditorToolbarMock;

public class DataEditorFactory {
    private static DataEditorInterface dataEditor;
    private static DataEditorContentsInterface dataEditorContents;
    private static DataEditorToolbarInterface dataEditorToolbar;
    private static DataEditorLeftListInterface dataEditorLeftList;

    public static void configureFactory(boolean bl) {
        dataEditor = new DataEditorController();
        dataEditorContents = new DataEditorContents();
        dataEditorLeftList = new DataEditorLeftList();
        dataEditorToolbar = bl ? new DataEditorToolbarMock() : new DataEditorToolbar();
    }

    public static DataEditorInterface getDataEditor() {
        return dataEditor;
    }

    public static DataEditorContentsInterface getDataEditorContents() {
        return dataEditorContents;
    }

    public static DataEditorToolbarInterface getDataEditorToolbar() {
        return dataEditorToolbar;
    }

    public static DataEditorLeftListInterface getDataEditorLeftList() {
        return dataEditorLeftList;
    }
}

