/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.sun.webpane.sg.prism.WCGraphicsPrismContext
 *  javafx.application.HostServices
 *  javafx.application.Platform
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.property.ReadOnlyStringProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.concurrent.Worker
 *  javafx.concurrent.Worker$State
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.CheckBox
 *  javafx.scene.control.ProgressIndicator
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.VBox
 *  javafx.scene.web.WebEngine
 *  javafx.scene.web.WebView
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 *  javafx.stage.WindowEvent
 */
package com.ti.et.elg.notifications.dialogs;

import com.sun.webpane.sg.prism.WCGraphicsPrismContext;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.notifications.AutomaticNotificationResourceBundle;
import com.ti.et.elg.notifications.gui.AutomaticNotificationCheckBox;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class AutomaticNotificationDialog {
    @FXML
    BorderPane paneAutomaticNotification;
    @FXML
    TIDialogButton butnLearnMore;
    @FXML
    TIDialogButton butnClose;
    @FXML
    HBox leftHBox;
    private CheckBox chkAuto;
    private Stage dialogStage;
    private Scene dialogScene;
    private String contentURL;
    private String learnMoreURL;
    private String content;
    private boolean isHtmlContent;
    private static final int MIN_DIALOG_WIDTH = 400;
    private static final int MIN_DIALOG_HEIGHT = 300;
    private static final Logger logger = Logger.getLogger(WCGraphicsPrismContext.class.getName());
    protected static final Logger LOG = Logger.getLogger(AutomaticNotificationDialog.class.getName());

    public AutomaticNotificationDialog(String string, String string2) {
        this.contentURL = string;
        this.learnMoreURL = string2;
        this.init("/com/ti/et/elg/notifications/dialogs/automaticNotificationDialog.fxml", "automaticNotificationDialog.title");
        this.addWebViewBox();
        this.addCheckBox();
        this.addLearnMoreAction();
        this.addCloseAction();
    }

    public AutomaticNotificationDialog(DialogIds dialogIds) {
        String string = null;
        String string2 = null;
        this.isHtmlContent = true;
        switch (dialogIds) {
            case CONNECTION_ERROR: {
                this.content = AutomaticNotificationResourceBundle.BUNDLE.getString("connectionErrorDialog.content.text");
                string2 = "/com/ti/et/elg/notifications/dialogs/connectionErrorDialog.fxml";
                string = "connectionErrorDialog.title";
                this.init(string2, string);
                this.addWebViewBox();
                this.addCloseAction();
                break;
            }
            case NO_NOTIFICATION: {
                this.content = AutomaticNotificationResourceBundle.BUNDLE.getString("noNotificationDialog.content.text");
                string2 = "/com/ti/et/elg/notifications/dialogs/noNotificationDialog.fxml";
                string = "noNotificationDialog.title";
                this.init(string2, string);
                this.addWebViewBox();
                this.addCheckBox();
                this.addCloseAction();
            }
        }
        this.dialogStage.setResizable(false);
    }

    private void init(String string, String string2) {
        Parent parent = null;
        this.dialogStage = new Stage();
        FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource(string));
        fXMLLoader.setResources((ResourceBundle)AutomaticNotificationResourceBundle.BUNDLE);
        fXMLLoader.setController((Object)this);
        try {
            parent = (Parent)fXMLLoader.load();
        }
        catch (IOException var5_5) {
            TILogger.logError("AutomaticNotificationDialog", "show", var5_5);
            return;
        }
        this.dialogScene = new Scene(parent);
        this.dialogStage.setTitle(AutomaticNotificationResourceBundle.BUNDLE.getString(string2));
        this.dialogScene.getStylesheets().add((Object)this.getClass().getResource("auto_notif_style.css").toExternalForm());
        this.dialogScene.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    AutomaticNotificationDialog.this.dialogStage.close();
                }
            }
        });
        this.dialogStage.initStyle(StageStyle.DECORATED);
        this.dialogStage.initModality(Modality.APPLICATION_MODAL);
        this.dialogStage.setMinHeight(300.0);
        this.dialogStage.setMinWidth(400.0);
        this.dialogStage.setResizable(true);
        this.dialogStage.setScene(this.dialogScene);
        this.dialogStage.setOnCloseRequest((EventHandler)new EventHandler<WindowEvent>(){

            public void handle(WindowEvent windowEvent) {
                AutomaticNotificationDialog.this.dialogStage.close();
            }
        });
    }

    public void show() {
        logger.setLevel(Level.OFF);
        if (!this.dialogStage.isShowing()) {
            this.dialogStage.show();
        }
        this.dialogStage.toFront();
    }

    private void addWebViewBox() {
        final WebView webView = new WebView();
        webView.setId("webviewNotification");
        webView.setFocusTraversable(true);
        webView.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case TAB: {
                        if (AutomaticNotificationDialog.this.chkAuto == null) break;
                        AutomaticNotificationDialog.this.chkAuto.requestFocus();
                        break;
                    }
                }
            }
        });
        final WebEngine webEngine = webView.getEngine();
        final VBox vBox = new VBox();
        vBox.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        vBox.setAlignment(Pos.CENTER);
        final ProgressIndicator progressIndicator = new ProgressIndicator(0.314);
        progressIndicator.setProgress(-1.0);
        if (this.isHtmlContent) {
            webEngine.loadContent(this.content);
        } else {
            webEngine.load(this.contentURL);
        }
        vBox.getChildren().addAll((Object[])new Node[]{webView, progressIndicator});
        webEngine.locationProperty().addListener((ChangeListener)new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> observableValue, final String string, String string2) {
                if (!string2.contains(AutomaticNotificationDialog.this.contentURL)) {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            webEngine.load(string);
                        }
                    });
                }
            }

        });
        webEngine.getLoadWorker().stateProperty().addListener((ChangeListener)new ChangeListener<Worker.State>(){

            public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State state2) {
                if (state2 == Worker.State.SUCCEEDED) {
                    vBox.getChildren().remove((Object)progressIndicator);
                    webView.requestFocus();
                    AutomaticNotificationDialog.LOG.info("Content was loaded succesfully");
                } else if (state2 == Worker.State.FAILED) {
                    AutomaticNotificationDialog.LOG.info("Web content loading: " + (Object)state2);
                    AutomaticNotificationDialog.this.dialogStage.close();
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            new AutomaticNotificationDialog(DialogIds.CONNECTION_ERROR).show();
                        }
                    });
                }
            }

        });
        this.paneAutomaticNotification.setCenter((Node)vBox);
    }

    private void addCheckBox() {
        this.chkAuto = AutomaticNotificationCheckBox.getInstance().getAutoSettingCheckBox();
        this.leftHBox.getChildren().add((Object)this.chkAuto);
    }

    private void addCloseAction() {
        this.butnClose.setText(AutomaticNotificationResourceBundle.BUNDLE.getString("automaticNotificationDialog.close.text"));
        this.butnClose.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                AutomaticNotificationDialog.this.dialogStage.close();
            }
        });
    }

    private void addLearnMoreAction() {
        this.butnLearnMore.setText(AutomaticNotificationResourceBundle.BUNDLE.getString("automaticNotificationDialog.learn.text"));
        this.butnLearnMore.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                CommonConstants.hostServices.showDocument(AutomaticNotificationDialog.this.learnMoreURL);
                AutomaticNotificationDialog.this.dialogStage.close();
            }
        });
    }

    public static enum DialogIds {
        CONNECTION_ERROR,
        NO_NOTIFICATION;
        

        private DialogIds() {
        }
    }

}

