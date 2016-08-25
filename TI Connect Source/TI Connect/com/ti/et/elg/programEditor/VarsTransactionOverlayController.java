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
package com.ti.et.elg.programEditor;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.exports.Animatable;
import com.ti.et.elg.deviceExplorer.exports.VarsTransactionOverlayInterface;
import com.ti.et.elg.programEditor.OverlayPaneController;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.text.MessageFormat;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class VarsTransactionOverlayController
extends OverlayPaneController
implements VarsTransactionOverlayInterface,
Animatable {
    @FXML
    Label firstLabel;
    @FXML
    Label secondLabel;
    @FXML
    ImageView animatedArrowImage;
    IDOverlappingPanelsType overlayPaneType;
    FadeTransition fadeTransition;

    public VarsTransactionOverlayController() {
    }

    public VarsTransactionOverlayController(IDOverlappingPanelsType iDOverlappingPanelsType) {
        this.overlayPaneType = iDOverlappingPanelsType;
    }

    @Override
    public void setItemsCount(int n, int n2) {
        if (this.overlayPaneType == IDOverlappingPanelsType.RECEIVING) {
            String string = CommonUISupportResourceBundle.BUNDLE.getString("receivingOverlayPanel.label");
            this.firstLabel.setText(MessageFormat.format(string, n, n2));
        }
    }

    @Override
    public void setTransaction(Transaction<?, ?> transaction) {
    }

    @Override
    public String getFxmlFileName() {
        String string = "";
        if (this.overlayPaneType == IDOverlappingPanelsType.SENDING) {
            string = "sendingFilesOverlay.fxml";
        } else if (this.overlayPaneType == IDOverlappingPanelsType.RECEIVING) {
            string = "receivingFilesOverlay.fxml";
        } else if (this.overlayPaneType == IDOverlappingPanelsType.DELETING) {
            string = "deletingFilesOverlay.fxml";
        }
        return string;
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
    public void setCurrentFileName(String string) {
        if (string != null && this.overlayPaneType == IDOverlappingPanelsType.RECEIVING) {
            this.secondLabel.setText(string);
        }
    }
}

