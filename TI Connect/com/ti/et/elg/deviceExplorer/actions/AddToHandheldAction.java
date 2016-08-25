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
package com.ti.et.elg.deviceExplorer.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AddToHandheldAction
extends TIAction {
    private static final AddToHandheldAction INSTANCE = new AddToHandheldAction();
    private static final String userHomeDir = System.getProperty("user.home") + File.separator;

    private AddToHandheldAction() {
        this.setDisabled(true);
        this.setName(AddToHandheldAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logInfo("AddToHandheldAction", "Executing AddToHandheldAction");
                String string = UserPropertyManagement.getUserProperty("AddFiles.lastFolder.path");
                if (null == string) {
                    string = userHomeDir;
                }
                List<TIDevice> list = DeviceExplorerFactory.getDeviceList().getConnectedDevices();
                FileChooser fileChooser = new FileChooser();
                File file = new File(string);
                if (!file.canRead()) {
                    TILogger.logInfo("AddToHandheldAction", "Can not use last directory");
                    file = new File(userHomeDir);
                }
                fileChooser.setInitialDirectory(file);
                PromptDialog.setFileChooserVisible(true);
                List list2 = fileChooser.showOpenMultipleDialog((Window)CommonConstants.MAIN_STAGE);
                PromptDialog.setFileChooserVisible(false);
                if (list2 != null && !list2.isEmpty() && !list.isEmpty()) {
                    ArrayList<File> arrayList = new ArrayList<File>(list2);
                    DeviceExplorerFactory.getDeviceExplorer().sendFilesToTIDevices(arrayList, list, null);
                    UserPropertyManagement.setUserProperty("AddFiles.lastFolder.path", arrayList.get(0).getParent());
                }
            }
        });
    }

    public static AddToHandheldAction getInstance() {
        return INSTANCE;
    }

}

