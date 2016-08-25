/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.image.Image
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.text.Text
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.commonUISupport.analyticsOptInDialog;

import com.ti.et.elg.AnalyticsManager.AnalyticsManager;
import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.connectServer.exports.Constants;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AnalyticsOptInDialog {
    @FXML
    protected Text messageText;
    @FXML
    protected TIDialogButton noThanksBtn;
    @FXML
    protected TIDialogButton yesWillHelpBtn;
    private boolean isDisplayed = false;
    private Stage dialogStage = new Stage();
    private static final String LOG_TAG = AnalyticsOptInDialog.class.getSimpleName();

    private void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/analyticsOptInDialog/analyticsOptInDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Parent parent = (Parent)fXMLLoader.load();
            parent.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        AnalyticsOptInDialog.this.setAnalyticsUserProperty(true);
                        AnalyticsOptInDialog.this.dialogStage.close();
                    }
                }
            });
            Scene scene = new Scene(parent);
            scene.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        AnalyticsOptInDialog.this.setAnalyticsUserProperty(true);
                        AnalyticsOptInDialog.this.dialogStage.close();
                    }
                }
            });
            this.dialogStage.setTitle(CommonUISupportResourceBundle.BUNDLE.getString("analyticsOptInDialog.dialogTitle"));
            this.dialogStage.initModality(Modality.APPLICATION_MODAL);
            this.dialogStage.initOwner((Window)(CommonConstants.SPLASH_STAGE.isShowing() ? CommonConstants.SPLASH_STAGE : CommonConstants.MAIN_STAGE));
            this.dialogStage.setResizable(false);
            this.dialogStage.setScene(scene);
            this.dialogStage.getScene().getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
            if (PlatformManager.isWindows()) {
                this.dialogStage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
            }
            this.messageText.setWrappingWidth(365.0);
            this.messageText.setText(CommonUISupportResourceBundle.BUNDLE.getString("analyticsOptInDialog.messageText"));
            this.noThanksBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    AnalyticsOptInDialog.this.setAnalyticsUserProperty(false);
                    AnalyticsOptInDialog.this.dialogStage.close();
                }
            });
            this.yesWillHelpBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    AnalyticsOptInDialog.this.setAnalyticsUserProperty(true);
                    AnalyticsOptInDialog.this.dialogStage.close();
                }
            });
            this.yesWillHelpBtn.requestFocus();
        }
        catch (IOException var1_2) {
            TILogger.logError(LOG_TAG, "showAndWait", var1_2);
        }
    }

    public void showAndWait() {
        if (!this.isDisplayed) {
            this.setDisplayed(true);
            this.init();
            this.dialogStage.showAndWait();
            this.setDisplayed(false);
        }
    }

    public boolean isDisplayed() {
        return this.isDisplayed;
    }

    public void setDisplayed(boolean bl) {
        this.isDisplayed = bl;
    }

    private void setAnalyticsUserProperty(boolean bl) {
        UserPropertyManagement.setBoolean("Analytics.enabled", bl);
        if (bl) {
            AnalyticsManager.enableAnalytics(CommonConstants.PRODUCT_NAME_WITH_TRADEMARK, Constants.PRODUCT_VERSION_STRING);
        } else {
            AnalyticsManager.disableAnalytics();
        }
    }

}

