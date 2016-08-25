/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.animation.FadeTransition
 *  javafx.application.Platform
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Label
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 *  javafx.util.Duration
 */
package com.ti.et.elg.tiConnect;

import com.ti.et.elg.tiConnect.TIConnectResourceBundle;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CloseDialog {
    @FXML
    Label label;
    private Stage closeDialog;
    private Scene dialogScene;
    private FadeTransition fadeTransition;

    public CloseDialog() {
        this.init();
    }

    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("closeDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)TIConnectResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Node node = (Node)fXMLLoader.load();
            this.dialogScene = new Scene((Parent)node);
            this.closeDialog = new Stage();
            this.closeDialog.setScene(this.dialogScene);
            this.closeDialog.initStyle(StageStyle.UNDECORATED);
        }
        catch (IOException var1_2) {
            TILogger.logError("CloseDialog", "init", var1_2);
        }
    }

    public void show() {
        this.closeDialog.show();
        this.startAnimation();
    }

    public void close() {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                if (CloseDialog.this.closeDialog.isShowing()) {
                    CloseDialog.this.stopAnimation();
                    CloseDialog.this.closeDialog.close();
                }
            }
        });
    }

    public void startAnimation() {
        this.fadeTransition = new FadeTransition(Duration.millis((double)500.0), (Node)this.label);
        this.fadeTransition.setFromValue(1.0);
        this.fadeTransition.setToValue(0.25);
        this.fadeTransition.setCycleCount(-1);
        this.fadeTransition.setAutoReverse(true);
        if (this.fadeTransition != null) {
            this.fadeTransition.play();
        }
    }

    public void stopAnimation() {
        if (this.fadeTransition != null) {
            this.fadeTransition.stop();
        }
    }

}

