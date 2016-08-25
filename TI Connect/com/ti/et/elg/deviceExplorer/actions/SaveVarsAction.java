/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.stage.DirectoryChooser
 *  javafx.stage.FileChooser
 *  javafx.stage.FileChooser$ExtensionFilter
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.deviceExplorer.actions;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SaveVarsAction
extends TIAction {
    private static final SaveVarsAction INSTANCE = new SaveVarsAction();
    private static final String userHomeDir = System.getProperty("user.home") + File.separator;
    private ResourceBundle resources = CommonUISupportResourceBundle.BUNDLE;

    private SaveVarsAction() {
        this.setDisabled(true);
        this.setName(SaveVarsAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                File file;
                String string = UserPropertyManagement.getUserProperty("Save.lastFolder.path");
                if (null == string) {
                    string = userHomeDir;
                }
                if (!(file = new File(string)).canRead()) {
                    TILogger.logInfo("SendOSAction", "Executing AddToHandheldAction");
                    file = new File(userHomeDir);
                }
                if (DeviceExplorerFactory.getContentTable().getCurrentlySelectedVars().length == 1) {
                    SaveVarsAction.this.saveSingleFile(file);
                } else {
                    SaveVarsAction.this.saveMultipleFiles(file);
                }
            }
        });
    }

    public void saveSingleFile(File file) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file);
        TIVar tIVar = DeviceExplorerFactory.getContentTable().getCurrentlySelectedVars()[0];
        String string = tIVar.getHostFileNameFromDeviceFileName();
        String string2 = DeviceExplorerFactory.getContentTable().getCurrentlySelectedVars()[0].getProperFileExtension();
        if (PlatformManager.isWindows() && string.length() > string2.length() && string.endsWith(string2)) {
            string = string.substring(0, string.length() - string2.length());
        }
        fileChooser.setInitialFileName(string);
        String string3 = DeviceExplorerFactory.getContentTable().getCurrentlySelectedVars()[0].getNameFromVarType(DeviceExplorerFactory.getContentTable().getCurrentlySelectedVars()[0].getVarType());
        String string4 = this.resources.getString("tivar.type." + string3);
        fileChooser.getExtensionFilters().addAll((Object[])new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter(string4, new String[]{"*" + string2})});
        PromptDialog.setFileChooserVisible(true);
        File file2 = fileChooser.showSaveDialog((Window)CommonConstants.MAIN_STAGE);
        PromptDialog.setFileChooserVisible(false);
        if (file2 != null) {
            if (!file2.getAbsolutePath().toUpperCase().endsWith(string2.toUpperCase())) {
                file2 = new File(file2.getAbsolutePath() + string2);
            }
            DeviceExplorerFactory.getContentTable().getSelectedFileFromTIDevice(file2);
            UserPropertyManagement.setUserProperty("Save.lastFolder.path", file2.getParent());
        }
    }

    public void saveMultipleFiles(File file) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(file);
        File file2 = directoryChooser.showDialog((Window)CommonConstants.MAIN_STAGE);
        if (file2 != null) {
            DeviceExplorerFactory.getContentTable().getSelectedFilesFromTIDevice(file2);
            UserPropertyManagement.setUserProperty("Save.lastFolder.path", file2.getAbsolutePath());
        }
    }

    public static SaveVarsAction getInstance() {
        return INSTANCE;
    }

}

