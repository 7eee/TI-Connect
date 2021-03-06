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

public class CutAction
extends TIAction {
    private static final CutAction INSTANCE = new CutAction();
    private final String TAG = CutAction.class.getSimpleName();

    private CutAction() {
        this.setDisabled(true);
        this.setName(CutAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(CutAction.this.TAG, "Executing Cut Action");
                ProgramEditorFactory.getProgramEditorContents().cut();
            }
        });
    }

    public static CutAction getInstance() {
        return INSTANCE;
    }

}

