/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.programEditor.exports;

import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import java.io.File;
import javafx.scene.Node;

public interface ProgramEditorContentsInterface {
    public Node getRootNode();

    public void init();

    public TIStatus saveProgram(File var1, boolean var2);

    public TIStatus saveTempProgram(StringBuilder var1);

    public void openProgram(File var1);

    public void openProgram(TIVar var1);

    public TIStatus createNewProgram(File var1);

    public void closeProgram();

    public void closeAllPrograms();

    public void insertText(String var1);

    public boolean isThereACurrentProgram();

    public void undo();

    public void redo();

    public void cut();

    public void copy();

    public void paste();

    public void selectAll();

    public void unselectAll();

    public void delete();

    public boolean isUndoable();

    public boolean isRedoable();

    public boolean hasSelection();

    public boolean isPasteable();

    public boolean isDirty();

    public String getFilePath();

    public boolean unsavedNewFile();

    public boolean hasUnsavedItems();

    public Node[] getFocusableNodes();

    public void updateFocusableNodes();

    public OverlayStackPaneBase getStackPane();
}

