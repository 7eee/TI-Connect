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

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SaveAsProgramAction
extends TIAction {
    private static final SaveAsProgramAction INSTANCE = new SaveAsProgramAction();
    private static boolean faceless = false;
    private static final String LOG_TAG = SaveAsProgramAction.class.getSimpleName();
    private static final String userHomeDir = PlatformManager.getDocsFolder();
    private static final String PROGRAM_EXTENSION = ".8xp";

    private SaveAsProgramAction() {
        this.setDisabled(true);
        this.setName(SaveAsProgramAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                if (!ProgramEditorFactory.getProgramEditorContents().isThereACurrentProgram()) {
                    TILogger.logError(LOG_TAG, "Save As Program should not be enabled because there is no current program!");
                    return;
                }
                TILogger.logDetail(LOG_TAG, "Executing Save As Program Action");
                SaveAsProgramAction.performSave(false);
            }
        });
    }

    public static boolean performSave(boolean bl) {
        String[] arrstring;
        Object object;
        String string = ProgramEditorFactory.getProgramEditorContents().getFilePath();
        File file = new File(string);
        boolean bl2 = false;
        int n = 2;
        if (bl && !ProgramEditorFactory.getProgramEditorContents().unsavedNewFile()) {
            TILogger.logDetail(LOG_TAG, "File path can just be used as is");
        } else if (!faceless) {
            File file2;
            object = new FileChooser();
            object.setInitialFileName(file.getName());
            arrstring = ProgramEditorResourceBundle.BUNDLE.getString("openProgram.fileChooser.8xp.description");
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter((String)arrstring, new String[]{"*.8xp"});
            object.getExtensionFilters().add((Object)extensionFilter);
            String string2 = UserPropertyManagement.getUserProperty("ProgramEditor.lastOpenOrSaveFolder.path");
            if (null == string2) {
                string2 = userHomeDir;
            }
            if (!(file2 = new File(string2)).canRead()) {
                TILogger.logError(LOG_TAG, "lastProgramDir invalid: " + string2);
                file2 = new File(userHomeDir);
            }
            object.setInitialDirectory(file2);
            PromptDialog.setFileChooserVisible(true);
            file = object.showSaveDialog((Window)CommonConstants.MAIN_STAGE);
            PromptDialog.setFileChooserVisible(false);
            if (PlatformManager.isWindows() && file != null && !file.getAbsolutePath().toUpperCase().endsWith(".8xp".toUpperCase()) && (file = new File(file.getAbsolutePath() + ".8xp")).exists()) {
                n = PromptDialog.showSimpleReplaceFilePromptAndWait(file.getName());
            }
        } else {
            file = new File("Testing.8xp");
        }
        if (file != null && n == 2) {
            UserPropertyManagement.setUserProperty("ProgramEditor.lastOpenOrSaveFolder.path", file.getParent() != null ? file.getParent() : "");
            object = ProgramEditorFactory.getProgramEditorContents().saveProgram(file, true);
            if (object.isFailure()) {
                arrstring = SaveAsProgramAction.reportError((TIStatus)object, file);
                PromptDialog.showUserError(arrstring[0], arrstring[1]);
            } else {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static String[] reportError(TIStatus tIStatus, File file) {
        String string = file.getName();
        switch (tIStatus.getStatusCode()) {
            case -10: {
                string = file.getParent();
                break;
            }
            case -9: 
            case -1: {
                string = file.getAbsolutePath();
                break;
            }
        }
        return ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_PROGRAMS, tIStatus, null, string);
    }

    public static SaveAsProgramAction getInstance() {
        return INSTANCE;
    }

    public static void goFaceless() {
        faceless = true;
    }

}

