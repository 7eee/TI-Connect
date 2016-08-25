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
package com.ti.et.elg.deviceExplorer.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.DeviceExplorerResourceBundle;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DataImportAction
extends TIAction {
    private static final DataImportAction INSTANCE = new DataImportAction();
    private static final String CSV_FILE_EXTENSION = "*.csv";

    private DataImportAction() {
        this.setDisabled(true);
        this.setName(DataImportAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                File file;
                TILogger.logInfo("DataImportAction", "Executing DataImportAction");
                FileChooser fileChooser = new FileChooser();
                String string = DeviceExplorerResourceBundle.BUNDLE.getString("dataImportAction.fileChooser.csv.description");
                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.csv"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                String string2 = UserPropertyManagement.getUserProperty("DataImport.lastFolder.path");
                if (null != string2) {
                    file = new File(string2);
                    if (file != null && file.exists() && file.canRead()) {
                        fileChooser.setInitialDirectory(file);
                    } else {
                        fileChooser.setInitialDirectory(new File(PlatformManager.getDocsFolder()));
                    }
                } else {
                    fileChooser.setInitialDirectory(new File(PlatformManager.getDocsFolder()));
                }
                PromptDialog.setFileChooserVisible(true);
                file = fileChooser.showOpenDialog((Window)CommonConstants.MAIN_STAGE);
                PromptDialog.setFileChooserVisible(false);
                if (file != null) {
                    UserPropertyManagement.setUserProperty("DataImport.lastFolder.path", file.getParent());
                    ArrayList<File> arrayList = new ArrayList<File>();
                    arrayList.add(file);
                    List<TIDevice> list = DeviceExplorerFactory.getDeviceList().getConnectedDevices();
                    DeviceExplorerFactory.getDeviceExplorer().sendFilesToTIDevices(arrayList, list, null);
                }
            }
        });
    }

    public static DataImportAction getInstance() {
        return INSTANCE;
    }

}

