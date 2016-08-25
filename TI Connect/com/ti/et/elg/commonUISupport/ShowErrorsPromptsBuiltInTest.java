/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.image.Image
 *  javafx.stage.Stage
 */
package com.ti.et.elg.commonUISupport;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.TIDeviceMock;
import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ShowErrorsPromptsBuiltInTest {
    public static ResourceBundle ticBundle = null;
    public static ResourceBundle svBundle = null;

    public static void runTest() {
        TILogger.logInfo("ShowErrorsPromptsBuiltInTest", "ShowErrorsPromptsBuiltInTest starting");
        if (ticBundle != null) {
            PromptDialog.showUserNotification("Show Errors Prompts Built In Test", "First we will show error dialogs related to:\nSending Vars\nReceiving Vars\nDeleting Vars\nSaving Screens\nOpening Vars\nSaving Programs");
        } else {
            PromptDialog.showUserNotification("Show Errors Prompts Built In Test", "First we will show error dialogs related to:\nSending Vars\nReceiving Vars\nDeleting Vars\nSaving Screens");
        }
        TIDeviceMock tIDeviceMock = new TIDeviceMock();
        String string = "DUMMY_FILE_LOCATION";
        String[] arrstring = new String[2];
        HashMap<TransactionType, int[]> hashMap = new HashMap<TransactionType, int[]>();
        TransactionType[] arrtransactionType = new TransactionType[]{TransactionType.RECEIVING, TransactionType.SENDING, TransactionType.DELETING, TransactionType.SAVING_SCREEN_CAPTURES, TransactionType.DATA_EDITING, TransactionType.SAVING_PROGRAMS};
        TransactionType[] arrtransactionType2 = new TransactionType[]{TransactionType.RECEIVING, TransactionType.SENDING, TransactionType.DELETING, TransactionType.SAVING_SCREEN_CAPTURES};
        TransactionType[] arrtransactionType3 = ticBundle != null ? arrtransactionType : arrtransactionType2;
        int[] arrn = new int[]{-6, 7, 31, 54, 65, -1, 44, 76, -5};
        int[] arrn2 = new int[]{7, -1, -9};
        int[] arrn3 = new int[]{7, 76};
        int[] arrn4 = new int[]{-10, -9, -8, -7, -1};
        int[] arrn5 = new int[]{-1, 101, 111, 113, 63, 112};
        int[] arrn6 = new int[]{63, -1, -9, -10, 57};
        hashMap.put(TransactionType.RECEIVING, arrn);
        hashMap.put(TransactionType.SENDING, arrn2);
        hashMap.put(TransactionType.DELETING, arrn3);
        hashMap.put(TransactionType.SAVING_SCREEN_CAPTURES, arrn4);
        if (ticBundle != null) {
            hashMap.put(TransactionType.DATA_EDITING, arrn5);
            hashMap.put(TransactionType.SAVING_PROGRAMS, arrn6);
        }
        for (TransactionType transactionType : arrtransactionType3) {
            int[] arrn7;
            for (int n : arrn7 = (int[])hashMap.get((Object)transactionType)) {
                arrstring[1] = null;
                arrstring[0] = null;
                arrstring = ErrorTranslator.errorCodeToMessage(transactionType, new TIStatus(n), tIDeviceMock, string);
                PromptDialog.showUserError(arrstring[0], arrstring[1]);
            }
        }
        PromptDialog.showUserNotification("Show Errors Prompts Built In Test", "Now we will show prompts and notifications");
        PromptDialog.showUserError(CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.duplicateNamesDialog.title"), CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.duplicateNamesDialog.message"), new Image(SendToHandheldDialog.class.getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
        TIResourceBundle tIResourceBundle = CommonUISupportResourceBundle.BUNDLE;
        PromptDialog.showUserNotification(tIResourceBundle.getString("error"), tIResourceBundle.getString("oneOrMoreFilesWasInvalid"));
        PromptDialog.showTwoChoicePrompt(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Title"), "dlgDelete", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Message"), CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.DeleteButton"), "butnDelete");
        if (ticBundle != null) {
            String[] arrstring2 = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("No"), "butnNo", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedProgram.SaveButton"), "butnSave"};
            PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedProgram.Title"), MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedProgram.Message"), string), "dlgSaveUnsavedProgram", arrstring2, new Image(ShowErrorsPromptsBuiltInTest.class.getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
        }
        PromptDialog.showSimpleReplaceFilePromptAndWait(string);
        PromptDialog.showTwoChoicePrompt(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Title"), "dlgDelete", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Message"), CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.DeleteButton"), "butnDelete", CommonConstants.MAIN_STAGE);
        PromptDialog.showReplaceFilePromptAndWait(string, CommonConstants.MAIN_STAGE);
        String[] arrstring3 = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("No"), "butnNo", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedScreenCaptures.SaveAllButton"), "butnSaveAll"};
        PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedScreenCaptures.Title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedScreenCaptures.Message"), "dlgSaveUnsaved", arrstring3, new Image(ShowErrorsPromptsBuiltInTest.class.getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()), CommonConstants.MAIN_STAGE);
        arrstring3 = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.btnCancel"), "btnCancel", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.btnDiscard"), "btnDiscard"};
        PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.message"), "discardClipboardContents", arrstring3, new Image(ShowErrorsPromptsBuiltInTest.class.getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
        PromptDialog.showUserError(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithOSTransactions.Title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithOSTransactions.Message"));
        arrstring3 = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "closeWT.cancelBtn", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithActiveTransactions.CloseButton"), "closeWT.closeBtn"};
        PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithActiveTransactions.Title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithActiveTransactions.Message"), "closeWithTransactions", arrstring3, new Image(ShowErrorsPromptsBuiltInTest.class.getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
        if (ticBundle != null) {
            PromptDialog.showUserError(ticBundle.getString("mac32bitError.title"), ticBundle.getString("mac32bitError.message"));
            PromptDialog.showUserNotification(ticBundle.getString("macConnectCommManagerWarning.title"), ticBundle.getString("macConnectCommManagerWarning.message"));
        } else {
            arrstring3[0] = svBundle.getString("topLevelMenuItem.actions.resetEmulatorPrompt.CancelBtn");
            arrstring3[1] = "butnCancel";
            arrstring3[2] = svBundle.getString("topLevelMenuItem.actions.resetEmulatorPrompt.RestoreBtn");
            arrstring3[3] = "butnRestore";
            PromptDialog.showMutipleChoiceDialog(svBundle.getString("topLevelMenuItem.actions.resetEmulatorPrompt.Title"), svBundle.getString("topLevelMenuItem.actions.resetEmulatorPrompt.Message"), "resestEmulatorDlg", arrstring3, new Image(ShowErrorsPromptsBuiltInTest.class.getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
            PromptDialog.showTwoChoicePrompt(svBundle.getString("KPHPrompt.ClearKeys.Title"), "KPHClearKeys", svBundle.getString("KPHPrompt.ClearKeys.Message"), svBundle.getString("KPHPrompt.ClearKeys.CancelBtn"), "btnCancel", svBundle.getString("KPHPrompt.ClearKeys.ClearBtn"), "btnClear");
        }
        PromptDialog.showUserNotification("Show Errors Prompts Built In Test", "We are done!");
        TILogger.logInfo("ShowErrorsPromptsBuiltInTest", "ShowErrorsPromptsBuiltInTest DONE");
    }
}

