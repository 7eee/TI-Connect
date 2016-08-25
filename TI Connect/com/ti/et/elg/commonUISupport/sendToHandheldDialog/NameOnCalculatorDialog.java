/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.binding.BooleanBinding
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.value.ObservableValue
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
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.commonUISupport.customComponents.TIPatternTextField;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameManager;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
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

public class NameOnCalculatorDialog {
    @FXML
    Label lblMessage;
    @FXML
    TIPatternTextField fldTokenName;
    @FXML
    TIDialogButton butnCancel;
    @FXML
    TIDialogButton butnDone;
    private Stage parent;
    private boolean dialogCancelled = true;
    private String customName;
    TIVar tiVar;

    public NameOnCalculatorDialog(TIVar tIVar, Stage stage) {
        this.tiVar = tIVar;
        this.parent = stage;
    }

    public void showAndWait() {
        try {
            Stage stage = new Stage();
            stage.setTitle(CommonUISupportResourceBundle.BUNDLE.getString("nameOnCalculatorDialog.title"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner((Window)this.parent);
            if (PlatformManager.isWindows()) {
                stage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
            }
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/sendToHandheldDialog/nameOnCalculatorDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Parent parent = (Parent)fXMLLoader.load();
            int n = this.tiVar.getVarType();
            this.fldTokenName.setCustomNamePattern(VarNameManager.getRegularExpression(n));
            this.butnDone.disableProperty().bind((ObservableValue)this.fldTokenName.validContentProperty().not());
            Scene scene = new Scene(parent);
            this.setEventHandlers(stage, scene);
            String string = CommonUISupportResourceBundle.BUNDLE.getString("tivar.type." + this.tiVar.getNameFromVarType(n));
            String string2 = MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("nameOnCalculatorDialog.text"), string, VarNameManager.getNameSizeLimit(n));
            this.lblMessage.setText(string2);
            scene.getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        }
        catch (IOException var1_2) {
            TILogger.logError("NameOnCalculatorDialog", "init", var1_2);
        }
    }

    private void setEventHandlers(final Stage stage, Scene scene) {
        this.butnCancel.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                NameOnCalculatorDialog.this.dialogCancelled = true;
                stage.close();
            }
        });
        this.butnDone.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                NameOnCalculatorDialog.this.dialogCancelled = false;
                NameOnCalculatorDialog.this.customName = NameOnCalculatorDialog.this.fldTokenName.getText();
                stage.close();
            }
        });
        scene.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    NameOnCalculatorDialog.this.dialogCancelled = true;
                    stage.close();
                }
            }
        });
    }

    public boolean isDialogCancelled() {
        return this.dialogCancelled;
    }

    public String getCustomName() {
        return this.customName;
    }

}

