/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.sun.javafx.stage.StageHelper
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.image.Image
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.text.Text
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.WindowEvent
 */
package com.ti.et.elg.commonUISupport.prompt;

import com.sun.javafx.stage.StageHelper;
import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.NotificationDialog;
import com.ti.et.elg.commonUISupport.prompt.PromptResponseButton;
import com.ti.et.elg.commonUISupport.prompt.PromptResult;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PromptDialog {
    static final String TAG = "PromptDialog";
    private static boolean suppressPrompts = false;
    private static Image dialogIcon;
    private static BooleanProperty showPromptsWhenClosing;
    private static boolean fileChooserVisible;

    public static boolean isModalDialogActive() {
        ObservableList observableList = StageHelper.getStages();
        for (Stage stage : observableList) {
            Modality modality = stage.getModality();
            if (modality == Modality.NONE) continue;
            return true;
        }
        if (fileChooserVisible) {
            return true;
        }
        return false;
    }

    public static void setFileChooserVisible(boolean bl) {
        TILogger.logDetail("PromptDialog", "Setting file chooser visible = " + bl);
        fileChooserVisible = bl;
    }

    public static void setSuppressPrompts(boolean bl) {
        suppressPrompts = bl;
    }

    public static boolean arePromptsSupressed() {
        return suppressPrompts;
    }

    public static int showReplaceFilePromptAndWait(String string) {
        return PromptDialog.showReplaceFilePromptAndWait(string, null);
    }

    public static int showReplaceFilePromptAndWait(String string, Stage stage) {
        if (suppressPrompts) {
            return 3;
        }
        PromptResult promptResult = new PromptResult(-1);
        NotificationDialog notificationDialog = new NotificationDialog();
        PromptResponseButton promptResponseButton = new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Replace"), 1, promptResult, null);
        promptResponseButton.setModifiedResponse(3);
        promptResponseButton.setId("butnReplace");
        PromptResponseButton promptResponseButton2 = new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Skip"), 0, promptResult, null);
        promptResponseButton2.setModifiedResponse(2);
        promptResponseButton2.setId("butnSkip");
        PromptResponseButton promptResponseButton3 = new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), 4, promptResult, null);
        promptResponseButton3.setModifiedResponse(4);
        promptResponseButton3.setId("butnCancel");
        String string2 = string;
        try {
            string2 = URLDecoder.decode(string, "UTF-8");
        }
        catch (Exception var8_8) {
            TILogger.logError("PromptDialog", "showReplaceFilePromptAndWait", var8_8);
        }
        notificationDialog.init(CommonUISupportResourceBundle.BUNDLE.getString("TITLE_FILE_ALREADY_EXISTS_ON_HOST"), MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("PROMPT_FILE_ALREADY_EXISTS_ON_HOST"), string2), new PromptResponseButton[]{promptResponseButton3, promptResponseButton2, promptResponseButton}, promptResult, 4, stage);
        notificationDialog.rootNode.setId("dlgReplace");
        notificationDialog.hideTitleInBody();
        notificationDialog.hideImageHolder();
        notificationDialog.setCheckboxText(CommonUISupportResourceBundle.BUNDLE.getString("CHECKBOX_FILE_ALREADY_EXISTS_ON_DEVICE"), "chkbApplyToAllHH");
        notificationDialog.showAndWait();
        return promptResult.getResultNoWait();
    }

    public static int showSimpleReplaceFilePromptAndWait(String string) {
        String string2 = CommonUISupportResourceBundle.BUNDLE.getString("TITLE_FILE_ALREADY_EXISTS_ON_HOST");
        String string3 = "dlgReplace";
        String string4 = string;
        try {
            string4 = URLDecoder.decode(string, "UTF-8");
        }
        catch (Exception var4_4) {
            TILogger.logError("PromptDialog", "showReplaceFilePromptAndWait", var4_4);
        }
        String string5 = MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("PROMPT_FILE_ALREADY_EXISTS_ON_HOST"), string4);
        String string6 = CommonUISupportResourceBundle.BUNDLE.getString("Cancel");
        String string7 = "butnCancel";
        String string8 = CommonUISupportResourceBundle.BUNDLE.getString("Replace");
        String string9 = "butnReplace";
        return PromptDialog.showTwoChoicePrompt(string2, string3, string5, string6, string7, string8, string9);
    }

    public static void showReplaceFilePrompt(String string, PromptResult promptResult) {
        PromptDialog.showReplaceFilePrompt(string, promptResult, null);
    }

    public static void showReplaceFilePrompt(String string, PromptResult promptResult, Stage stage) {
        if (suppressPrompts) {
            promptResult.setResult(3);
            return;
        }
        NotificationDialog notificationDialog = new NotificationDialog();
        PromptResponseButton promptResponseButton = new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Replace"), 1, promptResult, null);
        promptResponseButton.setModifiedResponse(3);
        promptResponseButton.setId("butnReplace");
        PromptResponseButton promptResponseButton2 = new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Skip"), 0, promptResult, null);
        promptResponseButton2.setModifiedResponse(2);
        promptResponseButton2.setId("butnSkip");
        PromptResponseButton promptResponseButton3 = new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), 4, promptResult, null);
        promptResponseButton3.setModifiedResponse(4);
        promptResponseButton3.setId("butnCancel");
        String string2 = string;
        try {
            string2 = URLDecoder.decode(string, "UTF-8");
        }
        catch (Exception var8_8) {
            TILogger.logError("PromptDialog", "showReplaceFilePrompt", var8_8);
        }
        notificationDialog.init(CommonUISupportResourceBundle.BUNDLE.getString("TITLE_FILE_ALREADY_EXISTS_ON_HOST"), MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("PROMPT_FILE_ALREADY_EXISTS_ON_HOST"), string2), new PromptResponseButton[]{promptResponseButton3, promptResponseButton2, promptResponseButton}, promptResult, 4, stage);
        notificationDialog.rootNode.setId("dlgReplace");
        notificationDialog.hideTitleInBody();
        notificationDialog.hideImageHolder();
        notificationDialog.setCheckboxText(CommonUISupportResourceBundle.BUNDLE.getString("CHECKBOX_FILE_ALREADY_EXISTS_ON_DEVICE"), "chkbApplyToAllHH");
        notificationDialog.show();
    }

    public static int showTwoChoicePrompt(String string, String string2, String string3, String string4, String string5, String string6, String string7) {
        return PromptDialog.showTwoChoicePrompt(string, string2, string3, string4, string5, string6, string7, null);
    }

    public static int showTwoChoicePrompt(String string, String string2, String string3, String string4, String string5, String string6, String string7, Stage stage) {
        if (suppressPrompts) {
            return 2;
        }
        NotificationDialog notificationDialog = new NotificationDialog();
        PromptResult promptResult = new PromptResult(-1);
        PromptResponseButton promptResponseButton = new PromptResponseButton(string4, 1, promptResult, null);
        PromptResponseButton promptResponseButton2 = new PromptResponseButton(string6, 2, promptResult, null);
        promptResponseButton.setId(string5);
        promptResponseButton2.setId(string7);
        notificationDialog.init(string, string3, new PromptResponseButton[]{promptResponseButton, promptResponseButton2}, null, 0, stage);
        notificationDialog.rootNode.setId(string2);
        notificationDialog.hideTitleInBody();
        notificationDialog.hideImageHolder();
        notificationDialog.hideCheckbox();
        notificationDialog.showAndWait();
        return promptResult.getResultNoWait();
    }

    public static int showMutipleChoiceDialog(String string, String string2, String string3, String[] arrstring, Image image) {
        return PromptDialog.showMutipleChoiceDialog(string, string2, string3, arrstring, image, null);
    }

    public static int showMutipleChoiceDialog(String string, ArrayList<Text> arrayList, String string2, String[] arrstring, Image image) {
        return PromptDialog.showMutipleChoiceDialog(string, arrayList, string2, arrstring, image, null);
    }

    public static int showMutipleChoiceDialog(String string, String string2, String string3, String[] arrstring, Image image, Stage stage) {
        ArrayList<Text> arrayList = new ArrayList<Text>();
        Text text = new Text(string2);
        text.setWrappingWidth(315.0);
        arrayList.add(text);
        return PromptDialog.showMutipleChoiceDialog(string, arrayList, string3, arrstring, image, stage);
    }

    public static int showMutipleChoiceDialog(String string, ArrayList<Text> arrayList, String string2, String[] arrstring, Image image, Stage stage) {
        if (suppressPrompts) {
            return 2;
        }
        int n = 0;
        if (arrstring.length > 2) {
            n = arrstring.length % 2 == 1 ? (arrstring.length - 1) / 2 : arrstring.length / 2;
        } else {
            return -1;
        }
        PromptResult promptResult = new PromptResult(-1);
        PromptResponseButton[] arrpromptResponseButton = new PromptResponseButton[n];
        for (int i = 0; i < n; ++i) {
            String string3 = arrstring[2 * i];
            String string4 = arrstring[2 * i + 1];
            if (null != string3 && string3.isEmpty()) {
                string3 = "CHOICE" + i;
            }
            if (null != string4 && string4.isEmpty()) {
                string3 = "button" + i;
            }
            arrpromptResponseButton[i] = new PromptResponseButton(string3, i, promptResult, null);
            arrpromptResponseButton[i].setId(string4);
        }
        NotificationDialog notificationDialog = new NotificationDialog();
        notificationDialog.init(string, arrayList, arrpromptResponseButton, promptResult, 0, stage);
        notificationDialog.hideCheckbox();
        if (null != image) {
            notificationDialog.setImage(image);
        } else {
            notificationDialog.hideImageHolder();
        }
        notificationDialog.showAndWait();
        return promptResult.getResultNoWait();
    }

    public static void showUserNotification(String string, String string2) {
        PromptDialog.showUserNotification(string, string2, null);
    }

    public static void showUserNotification(String string, String string2, Stage stage) {
        if (suppressPrompts) {
            return;
        }
        NotificationDialog notificationDialog = new NotificationDialog();
        notificationDialog.init(string, string2, new PromptResponseButton[]{new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Ok"), 1, new PromptResult(1), null)}, null, 0, stage);
        notificationDialog.hideTitleInBody();
        notificationDialog.hideImageHolder();
        notificationDialog.hideCheckbox();
        notificationDialog.showAndWait();
    }

    public static void showUserError(String string, String string2) {
        Stage stage = null;
        PromptDialog.showUserError(string, string2, stage);
    }

    public static void showUserError(String string, String string2, Stage stage) {
        if (suppressPrompts) {
            return;
        }
        NotificationDialog notificationDialog = new NotificationDialog();
        PromptResponseButton[] arrpromptResponseButton = new PromptResponseButton[]{new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Ok"), 1, new PromptResult(1), null)};
        arrpromptResponseButton[0].setId("butnOK");
        notificationDialog.init(string, string2, arrpromptResponseButton, null, 0, stage);
        notificationDialog.removeWindowDecorations();
        notificationDialog.hideCheckbox();
        notificationDialog.showAndWait();
    }

    public static void showUserError(String string, String string2, Image image) {
        PromptDialog.showUserError(string, string2, image, null);
    }

    public static void showUserError(String string, String string2, Image image, Stage stage) {
        if (suppressPrompts) {
            return;
        }
        NotificationDialog notificationDialog = new NotificationDialog();
        notificationDialog.init(string, string2, new PromptResponseButton[]{new PromptResponseButton(CommonUISupportResourceBundle.BUNDLE.getString("Ok"), 1, new PromptResult(1), null)}, null, 0, stage);
        notificationDialog.removeWindowDecorations();
        notificationDialog.setImage(image);
        notificationDialog.hideCheckbox();
        notificationDialog.showAndWait();
    }

    public static void commonDialogSetup(final Stage stage, final PromptResult promptResult, final int n) {
        stage.getScene().getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
        if (PlatformManager.isWindows() && dialogIcon != null) {
            stage.getIcons().add((Object)dialogIcon);
        }
        stage.setResizable(false);
        if (promptResult == null) {
            stage.getScene().setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        stage.close();
                    }
                }
            });
        } else {
            stage.getScene().setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        stage.close();
                        promptResult.setResult(n);
                    }
                }
            });
            stage.setOnCloseRequest((EventHandler)new EventHandler<WindowEvent>(){

                public void handle(WindowEvent windowEvent) {
                    promptResult.setResult(n);
                }
            });
        }
    }

    public static void setDialogIcon(Image image) {
        dialogIcon = image;
    }

    public static void setAppClosingProperty(BooleanProperty booleanProperty) {
        showPromptsWhenClosing.bind((ObservableValue)booleanProperty);
        suppressPrompts = !booleanProperty.get();
    }

    static {
        showPromptsWhenClosing = new SimpleBooleanProperty(false);
        fileChooserVisible = false;
    }

}

