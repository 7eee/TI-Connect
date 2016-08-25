/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Label
 *  javafx.scene.image.Image
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.commonUISupport.aboutTIConnectDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.SendGetDeleteBuildInTest;
import com.ti.et.elg.commonUISupport.ShowErrorsPromptsBuiltInTest;
import com.ti.et.elg.commonUISupport.commonActions.VisitTIEducationAction;
import com.ti.et.elg.commonUISupport.commonActions.VisitTIPrivacyPolicyAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.connectServer.exports.Constants;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AboutTIConnectDialog {
    @FXML
    Label lblCopyRight;
    @FXML
    Label lblTIConnectVersion;
    @FXML
    TIDialogButton butnEducationTiCom;
    @FXML
    TIDialogButton butnPrivacyPolicy;
    @FXML
    Label lblTIConnect;
    private Scene dialogScene;
    private static boolean isDisplayed = false;

    private static void setDisplayed(boolean bl) {
        isDisplayed = bl;
    }

    public void showAndWait() {
        try {
            if (!isDisplayed) {
                final Stage stage = new Stage();
                AboutTIConnectDialog.setDisplayed(true);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner((Window)CommonConstants.MAIN_STAGE);
                if (PlatformManager.isWindows()) {
                    stage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
                }
                FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("aboutTIConnectDialog.fxml"));
                fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
                fXMLLoader.setController((Object)this);
                Parent parent = (Parent)fXMLLoader.load();
                this.loadInfo();
                this.butnEducationTiCom.setOnAction(VisitTIEducationAction.getInstance().getEventHandler());
                this.butnPrivacyPolicy.setOnAction(VisitTIPrivacyPolicyAction.getInstance().getEventHandler());
                this.dialogScene = new Scene(parent);
                this.dialogScene.setOnKeyReleased((EventHandler)new EventHandler<KeyEvent>(){

                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                            stage.close();
                        }
                        if (keyEvent.isShiftDown() && keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A) {
                            SendGetDeleteBuildInTest.runTest();
                        } else if (keyEvent.isShiftDown() && keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.E) {
                            ShowErrorsPromptsBuiltInTest.runTest();
                        }
                    }
                });
                this.dialogScene.getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
                stage.setScene(this.dialogScene);
                stage.setResizable(false);
                stage.showAndWait();
                AboutTIConnectDialog.setDisplayed(false);
            }
        }
        catch (IOException var1_2) {
            TILogger.logError("AboutTIConnectDialog", "showAndWait", var1_2);
        }
    }

    private void loadInfo() {
        this.lblTIConnectVersion.setText(Constants.PRODUCT_VERSION_STRING);
    }

}

