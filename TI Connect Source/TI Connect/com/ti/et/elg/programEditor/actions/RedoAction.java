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

public class RedoAction
extends TIAction {
    private static final RedoAction INSTANCE = new RedoAction();
    private final String TAG = RedoAction.class.getSimpleName();

    private RedoAction() {
        this.setDisabled(true);
        this.setName(RedoAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(RedoAction.this.TAG, "Executing Redo Action");
                ProgramEditorFactory.getProgramEditorContents().redo();
            }
        });
    }

    public static RedoAction getInstance() {
        return INSTANCE;
    }

}

