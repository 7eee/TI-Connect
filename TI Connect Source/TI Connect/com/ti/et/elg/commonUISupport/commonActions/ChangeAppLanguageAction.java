/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.commonActions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.changeAppLanguageDialog.ChangeAppLanguageDialog;
import com.ti.et.elg.commonUISupport.commonActions.ExitAction;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class ChangeAppLanguageAction
extends TIAction {
    private static final ChangeAppLanguageAction INSTANCE = new ChangeAppLanguageAction();

    public static ChangeAppLanguageAction getInstance() {
        return INSTANCE;
    }

    private ChangeAppLanguageAction() {
        this.setName(ChangeAppLanguageAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                ChangeAppLanguageDialog changeAppLanguageDialog = new ChangeAppLanguageDialog();
                if (changeAppLanguageDialog.showAndWait()) {
                    ExitAction.getInstance().getEventHandler().handle((Event)new ActionEvent());
                }
            }
        });
    }

}

