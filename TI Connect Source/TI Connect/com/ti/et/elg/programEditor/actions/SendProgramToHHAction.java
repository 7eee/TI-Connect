/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableSet
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.programEditor.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.deviceList.DeviceListBase;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.programEditor.actions.SaveAsProgramAction;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.elg.programEditor.transaction.SendProgramTransaction;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class SendProgramToHHAction
extends TIAction {
    private static final SendProgramToHHAction INSTANCE = new SendProgramToHHAction();
    private static final String LOG_TAG = SendProgramToHHAction.class.getSimpleName();

    private SendProgramToHHAction() {
        this.setDisabled(true);
        this.setName(SendProgramToHHAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logInfo(LOG_TAG, "Executing SendProgramToHHAction");
                if (ProgramEditorFactory.getProgramEditorContents().isThereACurrentProgram()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    TIStatus tIStatus = ProgramEditorFactory.getProgramEditorContents().saveTempProgram(stringBuilder);
                    if (tIStatus.isFailure()) {
                        TILogger.logError(LOG_TAG, "Unable to save current program to temp file before sending");
                        if (tIStatus.isFailure()) {
                            File file = new File(stringBuilder.toString());
                            file.deleteOnExit();
                            String[] arrstring = SaveAsProgramAction.reportError(tIStatus, file);
                            PromptDialog.showUserError(arrstring[0], arrstring[1]);
                        }
                    } else {
                        ArrayList<File> arrayList = new ArrayList<File>();
                        arrayList.add(new File(stringBuilder.toString()));
                        List<TIDevice> list = DeviceExplorerFactory.getDeviceList().getConnectedDevices();
                        list.removeAll(((DeviceListBase)((Object)DeviceExplorerFactory.getDeviceList())).getBusyDeviceSet());
                        DeviceExplorerFactory.getDeviceExplorer().sendFilesToTIDevices(arrayList, list, SendProgramTransaction.class);
                    }
                }
            }
        });
    }

    public static SendProgramToHHAction getInstance() {
        return INSTANCE;
    }

}

