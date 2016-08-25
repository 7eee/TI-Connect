/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.control.TableCell
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIVarHolder;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameChangedListener;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameManager;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarTableData;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class VarLocationComboBoxCell
extends TableCell<VarTableData, TIVarHolder>
implements VarNameChangedListener {
    private ResourceBundle resources = CommonUISupportResourceBundle.BUNDLE;
    @FXML
    ComboBox<String> comboLocation;
    @FXML
    Button sendToHHDeleteButton;
    private ObservableList<VarTableData> varDataList;
    private FXMLLoader fxmlLoader;
    private Node rootNode;
    private TIVarHolder item;
    private boolean isRefreshing;
    private ChangeListener<String> cbLocationChangeListener;
    private EventHandler<KeyEvent> cbLocationEventHandler;
    private EventHandler<ActionEvent> deleteBtnEventHandler;
    private EventHandler<KeyEvent> deleteBtnKeyEventHandler;

    public VarLocationComboBoxCell(ObservableList<VarTableData> observableList) {
        this.varDataList = observableList;
        try {
            this.fxmlLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/sendToHandheldDialog/fileLocationCell.fxml"));
            this.fxmlLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            this.fxmlLoader.setController((Object)this);
            this.rootNode = (Node)this.fxmlLoader.load();
        }
        catch (IOException var2_2) {
            TILogger.logError("SendToHandheldDialog", "showAndWait", var2_2);
        }
        this.isRefreshing = false;
        this.addListeners();
    }

    private void addListeners() {
        if (this.cbLocationChangeListener == null) {
            this.cbLocationChangeListener = new ChangeListener<String>(){

                public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                    if (VarLocationComboBoxCell.this.isRefreshing) {
                        return;
                    }
                    TIVar tIVar = VarLocationComboBoxCell.this.item.getTIVar();
                    if (string2 != null && string2.equals(VarLocationComboBoxCell.this.resources.getString("table.location.ram.value"))) {
                        tIVar.setVarFlagArchive(false);
                    } else {
                        tIVar.setVarFlagArchive(true);
                    }
                }
            };
        }
        this.comboLocation.valueProperty().addListener(this.cbLocationChangeListener);
        if (this.deleteBtnEventHandler == null) {
            this.deleteBtnEventHandler = new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    int n = VarLocationComboBoxCell.this.varDataList.indexOf((Object)VarLocationComboBoxCell.this.item.getParent());
                    VarTableData varTableData = (VarTableData)VarLocationComboBoxCell.this.varDataList.get(n);
                    VarTableData.removeVarNameChangedListener(varTableData);
                    VarLocationComboBoxCell.this.item.deletedProperty().set(true);
                }
            };
        }
        this.sendToHHDeleteButton.setOnAction(this.deleteBtnEventHandler);
        if (this.cbLocationEventHandler == null) {
            this.cbLocationEventHandler = new EventHandler<KeyEvent>(){
                Node node;

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.TAB) {
                        this.node = keyEvent.isShiftDown() ? VarLocationComboBoxCell.this.item.getFirstNode() : VarLocationComboBoxCell.this.item.getLastNode();
                        Platform.runLater((Runnable)new Runnable(){

                            @Override
                            public void run() {
                                3.this.node.requestFocus();
                            }
                        });
                    }
                }

            };
        }
        this.comboLocation.addEventFilter(KeyEvent.KEY_PRESSED, this.cbLocationEventHandler);
        if (this.deleteBtnKeyEventHandler == null) {
            this.deleteBtnKeyEventHandler = new EventHandler<KeyEvent>(){
                Node node;

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.TAB) {
                        int n;
                        if (keyEvent.isShiftDown()) {
                            this.node = VarLocationComboBoxCell.this.item.getMiddleNode();
                            if (this.node.isDisabled()) {
                                this.node = VarLocationComboBoxCell.this.item.getFirstNode();
                            }
                            Platform.runLater((Runnable)new Runnable(){

                                @Override
                                public void run() {
                                    4.this.node.requestFocus();
                                }
                            });
                        }
                        if ((n = VarLocationComboBoxCell.this.varDataList.indexOf((Object)VarLocationComboBoxCell.this.item.getParent())) > -1) {
                            if (n < VarLocationComboBoxCell.this.varDataList.size() - 1) {
                                ++n;
                                if (!keyEvent.isShiftDown()) {
                                    VarTableData varTableData = (VarTableData)VarLocationComboBoxCell.this.varDataList.get(n);
                                    this.node = ((TIVarHolder)varTableData.tiVarHolderProperty().get()).getFirstNode();
                                    Platform.runLater((Runnable)new Runnable(){

                                        @Override
                                        public void run() {
                                            4.this.node.requestFocus();
                                        }
                                    });
                                }
                            } else if (!keyEvent.isShiftDown()) {
                                SendToHandheldDialog.setFocusToRadioButons();
                            }
                            SendToHandheldDialog.setScrollPosition(n);
                        }
                    }
                }

            };
        }
        this.sendToHHDeleteButton.addEventFilter(KeyEvent.KEY_PRESSED, this.deleteBtnKeyEventHandler);
    }

    protected void updateItem(TIVarHolder tIVarHolder, boolean bl) {
        super.updateItem((Object)tIVarHolder, bl);
        if (tIVarHolder != null) {
            TIVar tIVar = tIVarHolder.getTIVar();
            Object[] arrobject = VarNameManager.getAllowedLocations(tIVar.getDeviceFileName(), tIVar.getVarType(), tIVar.getVarVersion());
            this.isRefreshing = true;
            this.sendToHHDeleteButton.setVisible(this.varDataList.size() > 1);
            this.comboLocation.getItems().clear();
            this.comboLocation.getItems().addAll(arrobject);
            this.comboLocation.getSelectionModel().select(this.getSelectedIndex(tIVar));
            String string = (String)this.comboLocation.getSelectionModel().getSelectedItem();
            this.comboLocation.setValue((Object)"");
            this.comboLocation.setValue((Object)string);
            this.isRefreshing = false;
            this.item = tIVarHolder;
            tIVarHolder.setMiddleNode((Node)this.comboLocation);
            tIVarHolder.setLastNode((Node)this.sendToHHDeleteButton);
            if (arrobject.length < 2) {
                this.comboLocation.setDisable(true);
            } else {
                this.comboLocation.setDisable(false);
            }
            int n = this.varDataList.indexOf((Object)tIVarHolder.getParent());
            VarTableData varTableData = (VarTableData)this.varDataList.get(n);
            VarTableData.addVarNameChangedListener(varTableData, this);
            this.setGraphic(this.rootNode);
        }
    }

    private int getSelectedIndex(TIVar tIVar) {
        String string = tIVar.isVarFlagArchive() || tIVar.isFlashObject() ? this.resources.getString("table.location.archive.value") : this.resources.getString("table.location.ram.value");
        Object[] arrobject = VarNameManager.getAllowedLocations(tIVar.getDeviceFileName(), tIVar.getVarType(), tIVar.getVarVersion());
        return Arrays.binarySearch(arrobject, string);
    }

    @Override
    public void onVarNameChanged(TIVarHolder tIVarHolder) {
        TIVar tIVar = tIVarHolder.getTIVar();
        Object[] arrobject = VarNameManager.getAllowedLocations(tIVar.getDeviceFileName(), tIVar.getVarType(), tIVar.getVarVersion());
        String string = (String)this.comboLocation.getSelectionModel().getSelectedItem();
        this.comboLocation.getItems().clear();
        this.comboLocation.getItems().addAll(arrobject);
        this.comboLocation.setValue((Object)"");
        this.comboLocation.setValue((Object)string);
        int n = this.getSelectedIndex(tIVar);
        if (n > -1) {
            this.comboLocation.getSelectionModel().select(n);
        } else {
            this.comboLocation.getSelectionModel().selectLast();
        }
        if (arrobject.length < 2) {
            this.comboLocation.setDisable(true);
        } else {
            this.comboLocation.setDisable(false);
        }
    }

}

