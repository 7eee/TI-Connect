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

public class CloseProgramAction
extends TIAction {
    private static final CloseProgramAction INSTANCE = new CloseProgramAction();
    private final String TAG = CloseProgramAction.class.getSimpleName();

    private CloseProgramAction() {
        this.setDisabled(true);
        this.setName(CloseProgramAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(CloseProgramAction.this.TAG, "Executing Close Action");
                ProgramEditorFactory.getProgramEditorContents().closeProgram();
            }
        });
    }

    public static CloseProgramAction getInstance() {
        return INSTANCE;
    }

}

