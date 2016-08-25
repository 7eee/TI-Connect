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

public class UnselectAllAction
extends TIAction {
    private static final UnselectAllAction INSTANCE = new UnselectAllAction();
    private final String TAG = UnselectAllAction.class.getSimpleName();

    private UnselectAllAction() {
        this.setDisabled(true);
        this.setName(UnselectAllAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(UnselectAllAction.this.TAG, "Executing Unselect All Action");
                ProgramEditorFactory.getProgramEditorContents().unselectAll();
            }
        });
    }

    public static UnselectAllAction getInstance() {
        return INSTANCE;
    }

}

