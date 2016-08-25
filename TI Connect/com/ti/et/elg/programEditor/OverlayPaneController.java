/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 */
package com.ti.et.elg.programEditor;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.programEditor.exports.OverlayForDeviceInterface;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public abstract class OverlayPaneController
implements OverlayForDeviceInterface {
    OverlayStackPaneBase stackPaneController;
    Node rootNode;
    long startTime;
    long delay = 1000;

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
        this.setParent(ProgramEditorFactory.getProgramEditorContents().getStackPane());
        this.startTime = new Date().getTime();
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
    public void unloadPane(final TIDevice tIDevice) {
        new Thread(){

            @Override
            public void run() {
                while (OverlayPaneController.this.startTime + OverlayPaneController.this.delay > new Date().getTime()) {
                }
                Platform.runLater((Runnable)new Runnable(){

                    @Override
                    public void run() {
                        OverlayPaneController.this.hidePane(tIDevice);
                        OverlayPaneController.this.stackPaneController.unloadPane(tIDevice);
                    }
                });
            }

        }.start();
    }

}

