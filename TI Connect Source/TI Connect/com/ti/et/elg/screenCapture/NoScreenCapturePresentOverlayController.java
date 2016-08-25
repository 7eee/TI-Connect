/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 */
package com.ti.et.elg.screenCapture;

import com.ti.et.elg.commonUISupport.overlayPanes.OverlayBaseInterface;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.screenCapture.ScreenCaptureResourceBundle;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class NoScreenCapturePresentOverlayController
implements OverlayBaseInterface {
    OverlayStackPaneBase stackPaneController;
    Node rootNode;

    @Override
    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource(this.getFxmlFileName()));
            fXMLLoader.setResources((ResourceBundle)ScreenCaptureResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError("NoScreenCapturePresentOverlayController", "init", var1_2);
        }
    }

    @Override
    public String getFxmlFileName() {
        return "/com/ti/et/elg/screenCapture/noScreenCapturePresentOverlay.fxml";
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public void setParent(OverlayStackPaneBase overlayStackPaneBase) {
        this.stackPaneController = overlayStackPaneBase;
    }

    @Override
    public void hidePane(Node node) {
        this.stackPaneController.hidePane(node);
    }
}

