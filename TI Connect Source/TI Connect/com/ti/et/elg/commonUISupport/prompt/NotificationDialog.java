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
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.CheckBox
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.FlowPane
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.VBox
 *  javafx.scene.text.Text
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 *  javafx.stage.Window
 */
package com.ti.et.elg.commonUISupport.prompt;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.prompt.PromptResponseButton;
import com.ti.et.elg.commonUISupport.prompt.PromptResult;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

class NotificationDialog {
    @FXML
    FlowPane message;
    @FXML
    HBox butnPanel;
    @FXML
    Text title;
    @FXML
    CheckBox checkbox;
    @FXML
    VBox vbox;
    @FXML
    HBox imageHolder;
    @FXML
    ImageView imageView;
    @FXML
    BorderPane outerPane;
    Stage dialogStage;
    Parent rootNode;

    NotificationDialog() {
    }

    public void init(String string, String string2, PromptResponseButton[] arrpromptResponseButton, PromptResult promptResult, int n, Stage stage) {
        ArrayList<Text> arrayList = new ArrayList<Text>();
        Text text = new Text(string2);
        text.setWrappingWidth(315.0);
        arrayList.add(text);
        this.init(string, arrayList, arrpromptResponseButton, promptResult, n, stage);
    }

    public void init(String string, ArrayList<Text> arrayList, final PromptResponseButton[] arrpromptResponseButton, PromptResult promptResult, int n, Stage stage) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/prompt/promptDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Parent)fXMLLoader.load();
            Scene scene = new Scene(this.rootNode);
            this.dialogStage = new Stage();
            if (stage == null) {
                this.dialogStage.initOwner((Window)CommonConstants.MAIN_STAGE);
            } else {
                this.dialogStage.initOwner((Window)stage);
            }
            if (PlatformManager.isMac()) {
                ((Stage)this.dialogStage.getOwner()).toFront();
            }
            this.dialogStage.initModality(Modality.APPLICATION_MODAL);
            this.title.setText(string);
            this.title.setId("txtDlgTitle");
            this.dialogStage.setScene(scene);
            for (Text text : arrayList) {
                text.setStyle("-fx-font-size:13px;");
            }
            PromptDialog.commonDialogSetup(this.dialogStage, promptResult, n);
            this.message.setPrefWrapLength(315.0);
            this.message.getChildren().addAll(arrayList);
            this.message.setId("txtDlgMessage");
            for (PromptResponseButton promptResponseButton : arrpromptResponseButton) {
                this.butnPanel.getChildren().add((Object)promptResponseButton);
                promptResponseButton.dialogStage = this.dialogStage;
            }
            this.checkbox.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    for (PromptResponseButton promptResponseButton : arrpromptResponseButton) {
                        promptResponseButton.useModifiedResponse(NotificationDialog.this.checkbox.isSelected());
                    }
                }
            });
        }
        catch (IOException var7_8) {
            TILogger.logError("PromptDialog", "init", var7_8);
        }
    }

    public void setImage(Image image) {
        this.imageView.setImage(image);
    }

    public void hideTitleInBody() {
        this.vbox.getChildren().remove((Object)this.title);
        this.dialogStage.setTitle(this.title.getText());
    }

    public void removeWindowDecorations() {
        this.dialogStage.initStyle(StageStyle.UTILITY);
    }

    public void setCheckboxText(String string, String string2) {
        this.checkbox.setText(string);
        this.checkbox.setId(string2);
    }

    public void hideCheckbox() {
        this.vbox.getChildren().remove((Object)this.checkbox);
    }

    public void hideImageHolder() {
        this.outerPane.setLeft(null);
    }

    public void showAndWait() {
        this.dialogStage.showAndWait();
    }

    public void show() {
        this.dialogStage.show();
    }

}

