/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.collections.ObservableList
 *  javafx.scene.Node
 *  javafx.scene.control.MenuBar
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.VBox
 */
package com.ti.et.elg.tiConnect;

import com.ti.et.utils.platformUtils.PlatformManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TIConnectAutomationAPI {
    private BorderPane contentPane;
    private MenuBar menuBar;
    private VBox menuBox;
    private static final TIConnectAutomationAPI TIConnectAutomationAPIInstance = new TIConnectAutomationAPI();

    private TIConnectAutomationAPI() {
    }

    public static TIConnectAutomationAPI getInstance() {
        return TIConnectAutomationAPIInstance;
    }

    public void setMainMenu(BorderPane borderPane, MenuBar menuBar, VBox vBox) {
        this.contentPane = borderPane;
        this.menuBar = menuBar;
        this.menuBox = vBox;
    }

    public void disableSystemMenuForAutomation() {
        if (PlatformManager.isMac()) {
            Platform.runLater((Runnable)new Runnable(){

                @Override
                public void run() {
                    TIConnectAutomationAPI.this.contentPane.getChildren().remove((Object)TIConnectAutomationAPI.this.menuBar);
                    TIConnectAutomationAPI.this.contentPane.setTop((Node)TIConnectAutomationAPI.this.menuBox);
                    if (!TIConnectAutomationAPI.this.menuBox.getChildren().contains((Object)TIConnectAutomationAPI.this.menuBar)) {
                        TIConnectAutomationAPI.this.menuBox.getChildren().addAll((Object[])new Node[]{TIConnectAutomationAPI.this.menuBar});
                    }
                    TIConnectAutomationAPI.this.menuBar.setUseSystemMenuBar(false);
                }
            });
        }
    }

}

