/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.deviceExplorer.exports.OverlayForDeviceInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public abstract class OverlayPaneController
implements OverlayForDeviceInterface {
    OverlayStackPaneBase stackPaneController;
    Node rootNode;

    @Override
    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource(this.getFxmlFileName()));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError("OverlayPaneController", "init", var1_2);
        }
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
    public void hidePane(TIDevice tIDevice) {
        this.stackPaneController.hidePane(tIDevice);
    }

    @Override
    public void hidePane(Node node) {
    }

    @Override
    public void loadPane(TIDevice tIDevice) {
        this.init();
        this.setParent(DeviceExplorerFactory.getContentTable().getStackPane());
        this.addPane(tIDevice);
    }

    @Override
    public void addPane(TIDevice tIDevice) {
        this.stackPaneController.addPane(tIDevice, this.rootNode);
    }

    @Override
    public void showPane(TIDevice tIDevice) {
        this.stackPaneController.setPaneFadeIn(tIDevice);
    }

    @Override
    public void unloadPane(TIDevice tIDevice) {
        this.hidePane(tIDevice);
        this.stackPaneController.unloadPane(tIDevice);
    }
}

