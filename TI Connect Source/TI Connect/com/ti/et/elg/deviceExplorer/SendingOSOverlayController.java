/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.animation.FadeTransition
 *  javafx.fxml.FXML
 *  javafx.scene.Node
 *  javafx.scene.control.Label
 *  javafx.scene.image.ImageView
 *  javafx.util.Duration
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.OverlayPaneController;
import com.ti.et.elg.deviceExplorer.exports.Animatable;
import com.ti.et.elg.deviceExplorer.exports.OSOverlayControllerInterface;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.text.MessageFormat;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SendingOSOverlayController
extends OverlayPaneController
implements OSOverlayControllerInterface,
Animatable {
    @FXML
    Label sendingOSLabel;
    @FXML
    Label sendingOSText;
    @FXML
    ImageView animatedArrowImage;
    FadeTransition fadeTransition;

    @Override
    public void setOSVersion(int n, int n2) {
        String string = CommonUISupportResourceBundle.BUNDLE.getString("sendingOSOverlayPanel.text");
        this.sendingOSText.setText(MessageFormat.format(string, n, n2));
    }

    @Override
    public String getFxmlFileName() {
        return "/com/ti/et/elg/deviceExplorer/sendingOSOverlay.fxml";
    }

    @Override
    public void showPane(TIDevice tIDevice) {
        super.showPane(tIDevice);
        this.startAnimation();
    }

    @Override
    public void hidePane(TIDevice tIDevice) {
        this.stopAnimation();
        super.hidePane(tIDevice);
    }

    @Override
    public void startAnimation() {
        this.fadeTransition = new FadeTransition(Duration.millis((double)500.0), (Node)this.animatedArrowImage);
        this.fadeTransition.setFromValue(1.0);
        this.fadeTransition.setToValue(0.25);
        this.fadeTransition.setCycleCount(-1);
        this.fadeTransition.setAutoReverse(true);
        if (this.fadeTransition != null) {
            this.fadeTransition.play();
        }
    }

    @Override
    public void stopAnimation() {
        if (this.fadeTransition != null) {
            this.fadeTransition.stop();
        }
    }

    @Override
    public void setValidationLabel() {
        String string = CommonUISupportResourceBundle.BUNDLE.getString("sendingOSOverlayPanel.label.validation");
        this.sendingOSLabel.setText(string);
    }
}

