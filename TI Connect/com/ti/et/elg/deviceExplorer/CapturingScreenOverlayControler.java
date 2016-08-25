/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.animation.FadeTransition
 *  javafx.fxml.FXML
 *  javafx.scene.Node
 *  javafx.scene.image.ImageView
 *  javafx.util.Duration
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.OverlayPaneController;
import com.ti.et.elg.deviceExplorer.exports.Animatable;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CapturingScreenOverlayControler
extends OverlayPaneController
implements Animatable {
    @FXML
    ImageView capturingScreenImage;
    FadeTransition fadeTransition;

    @Override
    public String getFxmlFileName() {
        return "/com/ti/et/elg/deviceExplorer/capturingScreenOverlay.fxml";
    }

    @Override
    public void startAnimation() {
        this.fadeTransition = null;
        this.fadeTransition = new FadeTransition(Duration.millis((double)500.0), (Node)this.capturingScreenImage);
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
    public void showPane(TIDevice tIDevice) {
        super.showPane(tIDevice);
        this.startAnimation();
    }

    @Override
    public void hidePane(TIDevice tIDevice) {
        this.stopAnimation();
        super.hidePane(tIDevice);
    }
}

