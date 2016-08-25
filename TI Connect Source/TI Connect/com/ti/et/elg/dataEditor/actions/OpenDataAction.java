/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.stage.FileChooser
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.dataEditor.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.dataEditor.factory.DataEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import java.io.File;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class OpenDataAction
extends TIAction {
    private static final OpenDataAction INSTANCE = new OpenDataAction();

    private OpenDataAction() {
        this.setDisabled(false);
        this.setName(OpenDataAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                File file;
                TILogger.logDetail(OpenDataAction.this.getName(), "About to open a file for Data Editing");
                String string = UserPropertyManagement.getUserProperty("AddFiles.lastFolder.path");
                if (null == string) {
                    string = PlatformManager.getDocsFolder();
                }
                if (null == (file = new File(string)) || !file.exists()) {
                    string = PlatformManager.getDocsFolder();
                    file = new File(string);
                }
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(file);
                List list = fileChooser.showOpenMultipleDialog((Window)CommonConstants.MAIN_STAGE);
                if (null != list && !list.isEmpty()) {
                    DataEditorFactory.getDataEditorContents().openFiles(list);
                    UserPropertyManagement.setUserProperty("AddFiles.lastFolder.path", ((File)list.get(0)).getParentFile().getAbsolutePath());
                }
            }
        });
    }

    public static OpenDataAction getInstance() {
        return INSTANCE;
    }

}

