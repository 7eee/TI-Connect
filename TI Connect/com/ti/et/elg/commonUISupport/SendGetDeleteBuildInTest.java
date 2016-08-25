/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.stage.DirectoryChooser
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.commonUISupport;

import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.exports.TestAutomationInterface;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SendGetDeleteBuildInTest {
    static TestAutomationInterface testAutomationAPI = ConnectServerFactory.getTestAutomationAPI();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void runTest() {
        block24 : {
            int n = 0;
            try {
                PromptDialog.showUserNotification("Send Get Delete Built In Test.", "You will pick a directory of files and for each we will send/get/delete.\nWhen done, logs will be written at " + TILogger.getLogFilePath());
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File file = directoryChooser.showDialog((Window)CommonConstants.MAIN_STAGE);
                if (file != null) {
                    TILogger.logInfo("SendGetDeleteBIT", "Processing directory: " + file.getAbsolutePath());
                    ArrayList<String> arrayList = new ArrayList<String>();
                    n = testAutomationAPI.getConnectedDevices(arrayList);
                    if (arrayList.size() > 0) {
                        String string = arrayList.get(0);
                        TILogger.logInfo("SendGetDeleteBIT", "Processing DeviceID: " + string);
                        TIDevice tIDevice = testAutomationAPI.getTIDevice(string);
                        n = testAutomationAPI.getDeviceInfo(tIDevice);
                        TILogger.logInfo("SendGetDeleteBIT", "GetDeviceInfo RC: " + n);
                        if (n != 0) {
                            return;
                        }
                        ArrayList<TIVar> arrayList2 = new ArrayList<TIVar>();
                        File[] arrfile = file.listFiles();
                        for (int i = 0; i < arrfile.length; ++i) {
                            File file2 = arrfile[i];
                            if (file2.getName().equals(".DS_Store")) continue;
                            TIVar tIVar = testAutomationAPI.createTIVar(file2.getAbsolutePath());
                            TILogger.logInfo("SendGetDeleteBIT", "TIVar[" + tIVar.getDeviceFileName() + "] from file [" + file2.getName() + "] created successfully");
                            n = testAutomationAPI.sendVariable(string, tIVar);
                            TILogger.logInfo("SendGetDeleteBIT", "Sent the Var [" + file2.getName() + "] with RC: " + n);
                            if (n != 0) {
                                return;
                            }
                            n = testAutomationAPI.getDeviceDynamicInfo(tIDevice);
                            TILogger.logInfo("SendGetDeleteBIT", "GetDeviceDynamicInfo RC: " + n);
                            if (n != 0) {
                                return;
                            }
                            arrayList2 = new ArrayList();
                            n = testAutomationAPI.getDirectory(string, arrayList2);
                            TILogger.logInfo("SendGetDeleteBIT", "getDirectory with RC: " + n);
                            if (n != 0) {
                                return;
                            }
                            TIVar tIVar2 = tIVar.clone();
                            tIVar2.setData(null);
                            tIVar2.setHostFile(null);
                            n = testAutomationAPI.getVariable(string, tIVar2);
                            TILogger.logInfo("SendGetDeleteBIT", "Got the Var [" + file2.getName() + "] with RC: " + n);
                            if (n != 0) {
                                return;
                            }
                            if (!tIVar.isFlashObject()) {
                                if (Arrays.equals(tIVar.getData(), tIVar2.getData())) {
                                    TILogger.logInfo("SendGetDeleteBIT", "Data matches what was sent");
                                } else {
                                    TILogger.logError("SendGetDeleteBIT", "Data DOES NOT MATCH what was sent");
                                    return;
                                }
                            }
                            n = testAutomationAPI.deleteVariable(string, tIVar);
                            TILogger.logInfo("SendGetDeleteBIT", "Deleted the Var [" + file2.getName() + "] with RC: " + n);
                            if (n != 0) {
                                return;
                            }
                            n = testAutomationAPI.getDeviceDynamicInfo(tIDevice);
                            TILogger.logInfo("SendGetDeleteBIT", "GetDeviceDynamicInfo RC: " + n);
                            if (n == 0) continue;
                            return;
                        }
                        TILogger.logInfo("SendGetDeleteBIT", "Done with DeviceID: " + string);
                    } else {
                        TILogger.logError("SendGetDeleteBIT", "No Devices Connected!");
                    }
                    break block24;
                }
                TILogger.logError("SendGetDeleteBIT", "No Directory Connected!");
            }
            finally {
                PromptDialog.showUserNotification("Test Complete.  Final RC: " + n, "Check Log file at " + TILogger.getLogFilePath() + " for results.");
            }
        }
    }
}

