/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.stage.FileChooser
 *  javafx.stage.FileChooser$ExtensionFilter
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.programEditor.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class OpenProgramAction
extends TIAction {
    private static final OpenProgramAction INSTANCE = new OpenProgramAction();
    private final String LOG_TAG = OpenProgramAction.class.getSimpleName();
    private static final String userHomeDir = PlatformManager.getDocsFolder();

    private OpenProgramAction() {
        this.setDisabled(false);
        this.setName(OpenProgramAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                File file;
                TILogger.logDetail(OpenProgramAction.this.LOG_TAG, "Executing Save Action");
                FileChooser fileChooser = new FileChooser();
                String string = ProgramEditorResourceBundle.BUNDLE.getString("openProgram.fileChooser.8xp.description");
                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.8xp"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                String string2 = UserPropertyManagement.getUserProperty("ProgramEditor.lastOpenOrSaveFolder.path");
                if (null == string2) {
                    string2 = userHomeDir;
                }
                if (!(file = new File(string2)).canRead()) {
                    TILogger.logError(OpenProgramAction.this.LOG_TAG, "lastProgramDir invalid: " + string2);
                    file = new File(userHomeDir);
                }
                fileChooser.setInitialDirectory(file);
                PromptDialog.setFileChooserVisible(true);
                List list = fileChooser.showOpenMultipleDialog((Window)CommonConstants.MAIN_STAGE);
                PromptDialog.setFileChooserVisible(false);
                if (list != null && list.size() > 0) {
                    for (File file2 : list) {
                        UserPropertyManagement.setUserProperty("ProgramEditor.lastOpenOrSaveFolder.path", file2.getParent());
                        ProgramEditorFactory.getProgramEditorContents().openProgram(file2);
                    }
                }
            }
        });
    }

    public static OpenProgramAction getInstance() {
        return INSTANCE;
    }

}

