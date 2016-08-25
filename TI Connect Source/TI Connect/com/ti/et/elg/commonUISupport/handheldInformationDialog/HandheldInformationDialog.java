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
 *  javafx.scene.image.ImageView
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.layout.HBox
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.commonUISupport.handheldInformationDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class HandheldInformationDialog {
    @FXML
    Label lblHandheldType;
    @FXML
    Label lblProductID;
    @FXML
    Label lblProductIDLastDigits;
    @FXML
    Label lblOSVersion;
    @FXML
    Label lblRam;
    @FXML
    Label lblArchive;
    @FXML
    Label lblLanguage;
    @FXML
    Label lblRomVersion;
    @FXML
    Label lblHardwareVersion;
    @FXML
    TIDialogButton butnClose;
    @FXML
    ImageView calculatorImage;
    @FXML
    HBox pIDBox;
    private TIDevice device;
    private Scene dialogScene;
    private static final String IMAGE_PATH_TI83PLUS = "handheldInfoDialog.ti83Plus.image";
    private static final String IMAGE_PATH_TI84PLUS = "handheldInfoDialog.ti84Plus.image";
    private static final String IMAGE_PATH_TI82ADVANCED = "handheldInfoDialog.ti82Advanced.image";
    private static final String IMAGE_PATH_TI84PLUS_SILVER = "handheldInfoDialog.ti84PlusSilver.image";
    private static final String IMAGE_PATH_TI84PLUS_C_SILVER = "handheldInfoDialog.ti84PlusCSilver.image";
    private static final String IMAGE_PATH_TI83PREMIUM_CE = "handheldInfoDialog.ti83PremiumCE.image";
    private static final String IMAGE_PATH_TI84PLS_CE = "handheldInfoDialog.ti84PlusCE.image";
    private static final String IMAGE_PATH_TI84P_T = "handheldInfoDialog.ti84PlusT.image";
    private Image img;

    public HandheldInformationDialog(TIDevice tIDevice) {
        this.device = tIDevice;
    }

    public void showAndWait(boolean bl) {
        try {
            final Stage stage = new Stage();
            stage.setTitle(CommonUISupportResourceBundle.BUNDLE.getString("handheldInfoDialog.title"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner((Window)CommonConstants.MAIN_STAGE);
            if (PlatformManager.isWindows()) {
                stage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
            }
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/handheldInformationDialog/handheldInformationDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Parent parent = (Parent)fXMLLoader.load();
            this.loadInfo(bl);
            this.butnClose.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });
            this.dialogScene = new Scene(parent);
            this.dialogScene.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        stage.close();
                    }
                }
            });
            this.dialogScene.getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
            if (!bl) {
                this.pIDBox.setVisible(false);
                this.pIDBox.setManaged(false);
            }
            stage.setScene(this.dialogScene);
            stage.setResizable(false);
            stage.showAndWait();
        }
        catch (IOException var2_3) {
            TILogger.logError("HandheldInformationDialog", "showAndWait", var2_3);
        }
    }

    private void loadInfo(boolean bl) {
        String string;
        this.img = null;
        switch (this.device.getHardwareVersion()) {
            case 1: 
            case 2: {
                string = "handheldInfoDialog.ti83Plus.image";
                break;
            }
            case 3: {
                string = this.device.getProductID() == 11 ? "handheldInfoDialog.ti82Advanced.image" : "handheldInfoDialog.ti84Plus.image";
                break;
            }
            case 4: 
            case 5: {
                string = this.device.getProductID() == 27 ? "handheldInfoDialog.ti84PlusT.image" : "handheldInfoDialog.ti84PlusSilver.image";
                break;
            }
            case 6: {
                string = "handheldInfoDialog.ti84PlusCSilver.image";
                break;
            }
            default: {
                string = this.device.getExactMathCapability() ? "handheldInfoDialog.ti83PremiumCE.image" : "handheldInfoDialog.ti84PlusCE.image";
            }
        }
        string = CommonUISupportResourceBundle.BUNDLE.getString(string);
        this.img = new Image(this.getClass().getResource(string).toExternalForm());
        if (null != this.img) {
            this.calculatorImage.setImage(this.img);
        }
        this.lblHandheldType.setText(this.device.getDeviceName());
        String string2 = this.device.getConnectionID();
        String string3 = this.device.getConnectionIDforName();
        string2 = string2.substring(0, string2.lastIndexOf(string3));
        this.lblProductID.setText(string2);
        this.lblProductIDLastDigits.setText(string3);
        String string4 = "" + this.device.getOsVersionUpperByte() + "." + this.device.getOsVersionLowerByte() + "." + this.device.getOsPatchNum() + "." + this.device.getOsBuildNum();
        this.lblOSVersion.setText(string4);
        this.lblRam.setText(this.getMemoryMessage(this.device.getTotalRAM() / 1000, this.device.getFreeRAM() / 1000, true));
        this.lblArchive.setText(this.getMemoryMessage(this.device.getTotalFLASH() / 1000, this.device.getFreeFLASH() / 1000, bl));
        this.lblLanguage.setText(this.device.getPrimaryLanguageString());
        String string5 = "" + this.device.getBootCodeUpperByte() + "." + this.device.getBootCodeLowerByte() + "." + this.device.getBootPatchNum() + "." + this.device.getBootBuildNum();
        this.lblRomVersion.setText(string5);
        this.lblHardwareVersion.setText(String.valueOf(this.device.getHardwareVersion()));
    }

    private String getMemoryMessage(double d, double d2, boolean bl) {
        String string;
        if (bl) {
            string = CommonUISupportResourceBundle.BUNDLE.getString("handheldInfoDialog.memory");
            string = MessageFormat.format(string, d, d2);
        } else {
            string = CommonUISupportResourceBundle.BUNDLE.getString("handheldInfoDialog.memory.SV");
            string = MessageFormat.format(string, d);
        }
        return string;
    }

}

