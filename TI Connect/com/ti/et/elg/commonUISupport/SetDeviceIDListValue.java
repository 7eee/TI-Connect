/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport;

import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TestAutomationInterface;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SetDeviceIDListValue {
    static TestAutomationInterface testAutomationAPI = ConnectServerFactory.getTestAutomationAPI();
    static final String HEX_VALUES = "0123456789ABCDEF";

    public static String getHex(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(2 * arrby.length);
        for (byte by : arrby) {
            stringBuilder.append("0123456789ABCDEF".charAt(by >> 4 & 15)).append("0123456789ABCDEF".charAt(by & 15));
        }
        return stringBuilder.toString();
    }

    public static void setDeviceNameAndID() {
        SecureRandom secureRandom = new SecureRandom();
        long l = System.nanoTime() * 0x10000000;
        secureRandom.setSeed(l);
        byte[] arrby = new byte[5];
        secureRandom.nextBytes(arrby);
        arrby[0] = 19;
        arrby[1] = (byte)(arrby[1] & 15);
        String string = SetDeviceIDListValue.getHex(arrby);
        TILogger.logDetail(SetDeviceIDListValue.class.getSimpleName(), "ID: " + string);
        if (PromptDialog.showTwoChoicePrompt("Setting the Name and ID", "dlgDelete", "You will set the following properties for the current device:\nName: TI-83 Premium CE\nID: " + string, "Cancel", "butnCancel", "Set ID", "butnDelete") == 2) {
            byte[] arrby2 = new byte[30];
            arrby2[0] = 3;
            arrby2[1] = 61;
            arrby2[2] = 27;
            arrby2[3] = 4;
            arrby2[4] = 5;
            System.arraycopy(arrby, 0, arrby2, 5, arrby.length);
            arrby2[10] = 4;
            arrby2[11] = 45;
            byte[] arrby3 = "TI-83 Premium CE".getBytes();
            arrby2[12] = (byte)(arrby3.length + 1);
            System.arraycopy(arrby3, 0, arrby2, 13, arrby3.length);
            TILogger.logDetail(SetDeviceIDListValue.class.getSimpleName(), "Certificate to be loaded to the device: \n" + SetDeviceIDListValue.getHex(arrby2));
            int n = 0;
            ArrayList<String> arrayList = new ArrayList<String>();
            n = testAutomationAPI.getConnectedDevices(arrayList);
            if (arrayList.size() > 0) {
                String string2 = arrayList.get(0);
                TILogger.logInfo(SetDeviceIDListValue.class.getSimpleName(), "Processing DeviceID: " + string2);
                TIDevice tIDevice = testAutomationAPI.getTIDevice(string2);
                n = testAutomationAPI.setIDListValue(tIDevice, arrby2);
                if (n != 0) {
                    TILogger.logError(SetDeviceIDListValue.class.getSimpleName(), "An error ocurred trying to set the certificate err=" + n);
                    PromptDialog.showUserError("Error when Setting the IDList Value!!", "An error ocurred trying to set the certificate err=" + n);
                    return;
                }
            } else {
                TILogger.logError(SetDeviceIDListValue.class.getSimpleName(), "No device detected for this action!");
                PromptDialog.showUserError("Error when Setting the IDList Value!!", "No device detected for this action!");
                return;
            }
            TILogger.logInfo(SetDeviceIDListValue.class.getSimpleName(), "Set Complete!");
            PromptDialog.showUserNotification("Set Complete!", "Check Log file at " + TILogger.getLogFilePath() + " for results.");
        }
    }
}

