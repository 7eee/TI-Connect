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
import com.ti.et.elg.deviceExplorer.DeviceExplorerResourceBundle;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.utils.logger.TILogger;
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

public class SendOSAction
extends TIAction {
    private static final SendOSAction INSTANCE = new SendOSAction();
    private static final String userHomeDir = System.getProperty("user.home") + File.separator;
    private static final String ti83PremiumCEextension = "*.8pu";
    private static final String ti84PlusCEextension = "*.8eu";
    private static final String ti84PCSEextension = "*.8cu";
    private static final String ti84Pextension = "*.8xu";
    private static final String ti82AdvancedExtension = "*.82u";

    private SendOSAction() {
        this.setDisabled(true);
        this.setName(SendOSAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                File file;
                TILogger.logInfo("SendOSAction", "Executing SendOSAction");
                FileChooser fileChooser = new FileChooser();
                String string = DeviceExplorerResourceBundle.BUNDLE.getString("sendOSAction.fileChooser.8pu.description");
                FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.8pu"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                string = DeviceExplorerResourceBundle.BUNDLE.getString("sendOSAction.fileChooser.8eu.description");
                extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.8eu"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                string = DeviceExplorerResourceBundle.BUNDLE.getString("sendOSAction.fileChooser.8cu.description");
                extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.8cu"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                string = DeviceExplorerResourceBundle.BUNDLE.getString("sendOSAction.fileChooser.8xu.description");
                extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.8xu"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                string = DeviceExplorerResourceBundle.BUNDLE.getString("sendOSAction.fileChooser.8xuT.description");
                extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.8xu"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                string = DeviceExplorerResourceBundle.BUNDLE.getString("sendOSAction.fileChooser.82u.description");
                extensionFilter = new FileChooser.ExtensionFilter(string, new String[]{"*.82u"});
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
                String string2 = UserPropertyManagement.getUserProperty("InstallOS.lastFolder.path");
                if (null == string2) {
                    string2 = userHomeDir;
                }
                if (!(file = new File(string2)).canRead()) {
                    TILogger.logInfo("SendOSAction", "Executing AddToHandheldAction");
                    file = new File(userHomeDir);
                }
                fileChooser.setInitialDirectory(file);
                PromptDialog.setFileChooserVisible(true);
                File file2 = fileChooser.showOpenDialog((Window)CommonConstants.MAIN_STAGE);
                PromptDialog.setFileChooserVisible(false);
                if (file2 != null) {
                    UserPropertyManagement.setUserProperty("InstallOS.lastFolder.path", file2.getParent());
                    DeviceExplorerFactory.getContentTable().sendOSToSelectedDevice(file2);
                }
            }
        });
    }

    public static SendOSAction getInstance() {
        return INSTANCE;
    }

}

