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
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class CreateProgramAction
extends TIAction {
    private static final CreateProgramAction INSTANCE = new CreateProgramAction();
    private final String TAG = CreateProgramAction.class.getSimpleName();
    private int newProgCount = 1;

    private CreateProgramAction() {
        this.setDisabled(false);
        this.setName(CreateProgramAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logDetail(CreateProgramAction.this.TAG, "Executing Create Action");
                String string = UserPropertyManagement.getUserProperty("ProgramEditor.lastOpenOrSaveFolder.path");
                string = null == string ? PlatformManager.getDocsFolder() : string + File.separator;
                String string2 = ProgramEditorResourceBundle.BUNDLE.getString("programEditor.defaultFilePrefix") + CreateProgramAction.this.newProgCount + ".8xp";
                File file = new File(string + string2);
                ProgramEditorFactory.getProgramEditorContents().createNewProgram(file);
                CreateProgramAction.this.newProgCount++;
            }
        });
    }

    public static CreateProgramAction getInstance() {
        return INSTANCE;
    }

}

