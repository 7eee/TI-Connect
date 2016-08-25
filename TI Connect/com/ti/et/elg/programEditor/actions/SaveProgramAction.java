/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.programEditor.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.programEditor.actions.SaveAsProgramAction;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SaveProgramAction
extends TIAction {
    private static final SaveProgramAction INSTANCE = new SaveProgramAction();
    private final String TAG = "SaveProgramAction";

    private SaveProgramAction() {
        this.setDisabled(true);
        this.setName(SaveProgramAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                if (!ProgramEditorFactory.getProgramEditorContents().isThereACurrentProgram()) {
                    TILogger.logError("SaveProgramAction", "Save program should not be enabled because there is no current program!");
                    return;
                }
                TILogger.logDetail("SaveProgramAction", "Executing Save Action");
                SaveAsProgramAction.performSave(true);
            }
        });
    }

    public static SaveProgramAction getInstance() {
        return INSTANCE;
    }

}

