/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.image.Image
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.text.Text
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.commonUISupport.changeAppLanguageDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ChangeAppLanguageDialog {
    @FXML
    protected Text messageText;
    @FXML
    protected TIDialogButton quitNowBtn;
    @FXML
    protected TIDialogButton quitLaterBtn;
    @FXML
    protected TIDialogButton cancelBtn;
    @FXML
    protected ComboBox<String> languageSelector;
    private boolean isDisplayed = false;
    private Stage dialogStage = new Stage();
    private final String EN_US_STR = CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Language.EnglishUS");
    private final String ES_STR = CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Language.Spanish");
    private final String FR_STR = CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Language.French");
    private final String SV_STR = CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Language.Swedish");
    private final String PT_STR = CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Language.Portuguese");
    private final String DE_STR = CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Language.German");
    private final String NL_STR = CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Language.Dutch");
    private boolean appShouldExit = false;
    private HashMap<String, Locale> langMap = new HashMap();
    private ObservableList<String> allLanguages = FXCollections.observableArrayList();
    private static final String LOG_TAG = ChangeAppLanguageDialog.class.getSimpleName();

    private void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/changeAppLanguageDialog/changeAppLanguageDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Parent parent = (Parent)fXMLLoader.load();
            parent.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        ChangeAppLanguageDialog.this.dialogStage.close();
                    }
                }
            });
            Scene scene = new Scene(parent);
            scene.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        ChangeAppLanguageDialog.this.dialogStage.close();
                    }
                }
            });
            this.dialogStage.setTitle(CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Title"));
            this.dialogStage.initOwner((Window)CommonConstants.MAIN_STAGE);
            this.dialogStage.setResizable(false);
            this.dialogStage.setScene(scene);
            this.dialogStage.initModality(Modality.APPLICATION_MODAL);
            this.dialogStage.getScene().getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
            if (PlatformManager.isWindows()) {
                this.dialogStage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
            }
            this.messageText.setWrappingWidth(433.0);
            this.messageText.setText(CommonUISupportResourceBundle.BUNDLE.getString("changeAppLanDialog.Text"));
            this.languageSelector.setEditable(false);
            this.quitNowBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    String string = (String)ChangeAppLanguageDialog.this.languageSelector.getSelectionModel().getSelectedItem();
                    Locale locale = (Locale)ChangeAppLanguageDialog.this.langMap.get(string);
                    if (locale != null && !locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                        UserPropertyManagement.setUserProperty("Application.OverrideLanguage", locale.getLanguage());
                        UserPropertyManagement.setUserProperty("Application.OverrideCountry", locale.getCountry());
                        ChangeAppLanguageDialog.this.setAppExitFlag(true);
                    }
                    ChangeAppLanguageDialog.this.dialogStage.close();
                }
            });
            this.quitLaterBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    String string = (String)ChangeAppLanguageDialog.this.languageSelector.getSelectionModel().getSelectedItem();
                    Locale locale = (Locale)ChangeAppLanguageDialog.this.langMap.get(string);
                    if (locale != null && !locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                        UserPropertyManagement.setUserProperty("Application.OverrideLanguage", locale.getLanguage());
                        UserPropertyManagement.setUserProperty("Application.OverrideCountry", locale.getCountry());
                    }
                    ChangeAppLanguageDialog.this.setAppExitFlag(false);
                    ChangeAppLanguageDialog.this.dialogStage.close();
                }
            });
            this.cancelBtn.requestFocus();
            this.cancelBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    ChangeAppLanguageDialog.this.setAppExitFlag(false);
                    ChangeAppLanguageDialog.this.dialogStage.close();
                }
            });
        }
        catch (IOException var1_2) {
            TILogger.logError(LOG_TAG, "showAndWait", var1_2);
        }
    }

    private void loadLanguages() {
        this.allLanguages.add((Object)this.DE_STR);
        this.langMap.put(this.DE_STR, Locale.GERMAN);
        this.allLanguages.add((Object)this.EN_US_STR);
        this.langMap.put(this.EN_US_STR, Locale.ENGLISH);
        this.allLanguages.add((Object)this.ES_STR);
        this.langMap.put(this.ES_STR, new Locale("es"));
        this.allLanguages.add((Object)this.FR_STR);
        this.langMap.put(this.FR_STR, Locale.FRENCH);
        this.allLanguages.add((Object)this.NL_STR);
        this.langMap.put(this.NL_STR, new Locale("nl"));
        this.allLanguages.add((Object)this.PT_STR);
        this.langMap.put(this.PT_STR, new Locale("pt"));
        this.allLanguages.add((Object)this.SV_STR);
        this.langMap.put(this.SV_STR, new Locale("sv"));
        this.languageSelector.getItems().clear();
        this.languageSelector.getItems().addAll(this.allLanguages);
        String string = UserPropertyManagement.getString("Application.OverrideLanguage", null);
        this.languageSelector.getSelectionModel().select((Object)this.languageCodeToLanguageString(string != null ? string : Locale.getDefault().getLanguage()));
    }

    private String languageCodeToLanguageString(String string) {
        switch (string) {
            case "de": {
                return this.DE_STR;
            }
            case "sv": {
                return this.SV_STR;
            }
            case "fr": {
                return this.FR_STR;
            }
            case "pt": {
                return this.PT_STR;
            }
            case "nl": {
                return this.NL_STR;
            }
            case "es": {
                return this.ES_STR;
            }
        }
        return this.EN_US_STR;
    }

    public boolean showAndWait() {
        if (!this.isDisplayed) {
            this.setDisplayed(true);
            this.init();
            this.loadLanguages();
            this.dialogStage.showAndWait();
            this.setDisplayed(false);
            return this.shouldAppExit();
        }
        return false;
    }

    private void setDisplayed(boolean bl) {
        this.isDisplayed = bl;
    }

    private boolean shouldAppExit() {
        return this.appShouldExit;
    }

    private void setAppExitFlag(boolean bl) {
        this.appShouldExit = bl;
    }

}

