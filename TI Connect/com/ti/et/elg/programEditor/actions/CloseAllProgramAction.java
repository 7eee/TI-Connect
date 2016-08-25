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
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CloseAllProgramAction
extends TIAction {
    private static final CloseAllProgramAction INSTANCE = new CloseAllProgramAction();
    private final String TAG = CloseAllProgramAction.class.getSimpleName();

    private CloseAllProgramAction() {
        this.setDisabled(true);
        this.setName(CloseAllProgramAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(CloseAllProgramAction.this.TAG, "Executing Close All Action");
                ProgramEditorFactory.getProgramEditorContents().closeAllPrograms();
            }
        });
    }

    public static CloseAllProgramAction getInstance() {
        return INSTANCE;
    }

}

