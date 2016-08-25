/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.programEditor.actions;

import com.ti.et.elg.programEditor.actions.CloseAllProgramAction;
import com.ti.et.elg.programEditor.actions.CloseProgramAction;
import com.ti.et.elg.programEditor.actions.CopyAction;
import com.ti.et.elg.programEditor.actions.CutAction;
import com.ti.et.elg.programEditor.actions.DeleteAction;
import com.ti.et.elg.programEditor.actions.PasteAction;
import com.ti.et.elg.programEditor.actions.RedoAction;
import com.ti.et.elg.programEditor.actions.SaveAsProgramAction;
import com.ti.et.elg.programEditor.actions.SaveProgramAction;
import com.ti.et.elg.programEditor.actions.SelectAllAction;
import com.ti.et.elg.programEditor.actions.SendProgramToHHAction;
import com.ti.et.elg.programEditor.actions.UndoAction;
import com.ti.et.elg.programEditor.actions.UnselectAllAction;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;

public class ProgramEditorActionManager {
    private static final ProgramEditorActionManager INSTANCE = new ProgramEditorActionManager();
    private boolean isThereACurrentProgram = false;

    private ProgramEditorActionManager() {
    }

    public static ProgramEditorActionManager getInstance() {
        return INSTANCE;
    }

    public void setCurrentProgram(boolean bl) {
        this.isThereACurrentProgram = bl;
        this.updateActionState();
    }

    public void programStateChanged() {
        this.updateActionState();
    }

    private void updateActionState() {
        SaveProgramAction.getInstance().setDisabled(!this.isThereACurrentProgram || !ProgramEditorFactory.getProgramEditorContents().isDirty());
        SaveAsProgramAction.getInstance().setDisabled(!this.isThereACurrentProgram);
        SendProgramToHHAction.getInstance().setDisabled(!this.isThereACurrentProgram);
        UndoAction.getInstance().setDisabled(!ProgramEditorFactory.getProgramEditorContents().isUndoable());
        RedoAction.getInstance().setDisabled(!ProgramEditorFactory.getProgramEditorContents().isRedoable());
        CutAction.getInstance().setDisabled(!ProgramEditorFactory.getProgramEditorContents().hasSelection());
        CopyAction.getInstance().setDisabled(!ProgramEditorFactory.getProgramEditorContents().hasSelection());
        PasteAction.getInstance().setDisabled(!ProgramEditorFactory.getProgramEditorContents().isPasteable());
        SelectAllAction.getInstance().setDisabled(!this.isThereACurrentProgram);
        UnselectAllAction.getInstance().setDisabled(!ProgramEditorFactory.getProgramEditorContents().hasSelection());
        CloseProgramAction.getInstance().setDisabled(!this.isThereACurrentProgram);
        CloseAllProgramAction.getInstance().setDisabled(!this.isThereACurrentProgram);
        DeleteAction.getInstance().setDisabled(!ProgramEditorFactory.getProgramEditorContents().hasSelection());
    }
}

