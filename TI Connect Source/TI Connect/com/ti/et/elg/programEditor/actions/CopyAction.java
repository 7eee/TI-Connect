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

public class CopyAction
extends TIAction {
    private static final CopyAction INSTANCE = new CopyAction();
    private final String TAG = CopyAction.class.getSimpleName();

    private CopyAction() {
        this.setDisabled(true);
        this.setName(CopyAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(CopyAction.this.TAG, "Executing Copy Action");
                ProgramEditorFactory.getProgramEditorContents().copy();
            }
        });
    }

    public static CopyAction getInstance() {
        return INSTANCE;
    }

}

