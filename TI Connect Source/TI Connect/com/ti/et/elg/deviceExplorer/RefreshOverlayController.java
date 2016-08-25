/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.animation.RotateTransition
 *  javafx.fxml.FXML
 *  javafx.scene.Node
 *  javafx.scene.image.ImageView
 *  javafx.util.Duration
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.OverlayPaneController;
import com.ti.et.elg.deviceExplorer.exports.Animatable;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class RefreshOverlayController
extends OverlayPaneController
implements Animatable {
    @FXML
    ImageView refreshImage;
    RotateTransition rotateTransition;

    @Override
    public void startAnimation() {
        this.rotateTransition = new RotateTransition(Duration.millis((double)4000.0), (Node)this.refreshImage);
        this.rotateTransition.setByAngle(1440.0);
        this.rotateTransition.setCycleCount(-1);
        if (this.rotateTransition != null) {
            this.rotateTransition.play();
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

    @Override
    public String getFxmlFileName() {
        return "/com/ti/et/elg/deviceExplorer/refreshOverlay.fxml";
    }

    @Override
    public void stopAnimation() {
        if (this.rotateTransition != null) {
            this.rotateTransition.stop();
        }
    }
}

