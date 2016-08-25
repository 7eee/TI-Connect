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

public class PasteAction
extends TIAction {
    private static final PasteAction INSTANCE = new PasteAction();
    private final String TAG = PasteAction.class.getSimpleName();

    private PasteAction() {
        this.setDisabled(true);
        this.setName(PasteAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(PasteAction.this.TAG, "Executing Paste Action");
                ProgramEditorFactory.getProgramEditorContents().paste();
            }
        });
    }

    public static PasteAction getInstance() {
        return INSTANCE;
    }

}

