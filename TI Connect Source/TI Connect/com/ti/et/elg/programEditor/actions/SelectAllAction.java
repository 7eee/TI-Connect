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

public class SelectAllAction
extends TIAction {
    private static final SelectAllAction INSTANCE = new SelectAllAction();
    private final String TAG = SelectAllAction.class.getSimpleName();

    private SelectAllAction() {
        this.setDisabled(true);
        this.setName(SelectAllAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(SelectAllAction.this.TAG, "Executing Select All Action");
                ProgramEditorFactory.getProgramEditorContents().selectAll();
            }
        });
    }

    public static SelectAllAction getInstance() {
        return INSTANCE;
    }

}

