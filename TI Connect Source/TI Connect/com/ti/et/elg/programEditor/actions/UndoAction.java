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

public class UndoAction
extends TIAction {
    private static final UndoAction INSTANCE = new UndoAction();
    private final String TAG = UndoAction.class.getSimpleName();

    private UndoAction() {
        this.setDisabled(true);
        this.setName(UndoAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(UndoAction.this.TAG, "Executing Undo Action");
                ProgramEditorFactory.getProgramEditorContents().undo();
            }
        });
    }

    public static UndoAction getInstance() {
        return INSTANCE;
    }

}

