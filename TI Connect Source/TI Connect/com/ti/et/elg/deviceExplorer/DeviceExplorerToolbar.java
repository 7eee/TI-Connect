/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ReadOnlyDoubleProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.ToolBar
 *  javafx.scene.image.ImageView
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.HBox
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIButton;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.autoDetect.mock.AutoDetectMock;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.deviceExplorer.actions.AddToHandheldAction;
import com.ti.et.elg.deviceExplorer.actions.DeleteVarsAction;
import com.ti.et.elg.deviceExplorer.actions.DeviceRefreshAction;
import com.ti.et.elg.deviceExplorer.actions.SaveVarsAction;
import com.ti.et.elg.deviceExplorer.actions.SendToHHAction;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerToolbarInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DeviceExplorerToolbar
implements DeviceExplorerToolbarInterface {
    @FXML
    HBox hboxForFirstSeparator;
    @FXML
    ImageView firstSeparator;
    @FXML
    TIButton connectDeviceBtn;
    @FXML
    TIButton disconnectDeviceBtn;
    @FXML
    TIButton butnHHExplorerAdd;
    @FXML
    TIButton butnHHExplorerRefresh;
    @FXML
    TIButton butnHHExplorerSave;
    @FXML
    HBox hboxForSendToHHs;
    @FXML
    TIButton butnHHExplorerSendToHHs;
    @FXML
    TIButton butnHHExplorerDelete;
    @FXML
    HBox toolBarSpacerHbox;
    private Node rootNode = null;
    private int offsetPadding = 11;
    DeviceExplorerInterface deviceExplorer = null;

    @Override
    public void init(DeviceExplorerInterface deviceExplorerInterface) {
        try {
            this.deviceExplorer = deviceExplorerInterface;
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/deviceExplorer/deviceExplorerToolbar.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
            this.initToolBar();
        }
        catch (IOException var2_3) {
            TILogger.logError("DeviceExplorerToolbar", "init", var2_3);
        }
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    private void initToolBar() {
        this.initMockButtons();
        this.butnHHExplorerRefresh.setAction(DeviceRefreshAction.getInstance());
        this.butnHHExplorerSave.setAction(SaveVarsAction.getInstance());
        this.butnHHExplorerDelete.setAction(DeleteVarsAction.getInstance());
        this.butnHHExplorerSendToHHs.setAction(SendToHHAction.getInstance());
        this.butnHHExplorerAdd.setAction(AddToHandheldAction.getInstance());
        if (DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_CONNECT) {
            BorderPane borderPane = (BorderPane)DeviceExplorerFactory.getDeviceList().getRootNode();
            borderPane.widthProperty().addListener((ChangeListener)new ChangeListener<Object>(){

                public void changed(ObservableValue<?> observableValue, Object object, Object object2) {
                    Double d = (Double)object2;
                    DeviceExplorerToolbar.this.toolBarSpacerHbox.setPrefWidth(d - (double)DeviceExplorerToolbar.this.offsetPadding);
                }
            });
        } else {
            this.toolBarSpacerHbox.setPrefWidth((double)(100 - this.offsetPadding));
            this.hboxForSendToHHs.getChildren().remove((Object)this.butnHHExplorerSendToHHs);
            this.hboxForFirstSeparator.getChildren().remove((Object)this.firstSeparator);
        }
    }

    private void initMockButtons() {
        if (ConnectServerFactory.getAutoDetect() instanceof AutoDetectMock) {
            this.connectDeviceBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    if (ConnectServerFactory.getAutoDetect() instanceof AutoDetectMock) {
                        ((AutoDetectMock)ConnectServerFactory.getAutoDetect()).triggerConnect(1, 1000);
                    } else {
                        PromptDialog.showUserNotification("Invalid operation", "Real autodetect in place so plug in a device");
                    }
                }
            });
            this.disconnectDeviceBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    if (ConnectServerFactory.getAutoDetect() instanceof AutoDetectMock) {
                        ((AutoDetectMock)ConnectServerFactory.getAutoDetect()).triggerDisconnect(1, 1000);
                    } else {
                        PromptDialog.showUserNotification("Invalid operation", "Real autodetect in place so unplug a device");
                    }
                }
            });
        } else {
            ((ToolBar)this.getRootNode()).getItems().remove((Object)this.connectDeviceBtn);
            ((ToolBar)this.getRootNode()).getItems().remove((Object)this.disconnectDeviceBtn);
        }
    }

}

