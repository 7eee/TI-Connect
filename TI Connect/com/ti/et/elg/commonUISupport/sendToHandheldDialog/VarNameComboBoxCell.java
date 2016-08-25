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
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.scene.Node
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.Label
 *  javafx.scene.control.ListCell
 *  javafx.scene.control.ListView
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.control.TableCell
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.text.Font
 *  javafx.stage.Stage
 *  javafx.util.Callback
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.NameOnCalculatorDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIVarHolder;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameManager;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarTableData;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

class VarNameComboBoxCell
extends TableCell<VarTableData, TIVarHolder> {
    private static final String CUSTOM_NAME = CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.customName");
    private Stage parent;
    private final ComboBox<String> cbName;
    private TIVarHolder item;
    private boolean isRefreshing;
    private ChangeListener<String> cbNameChangeListener;
    private EventHandler<KeyEvent> cbNameEventHandler;
    private ChangeListener<Boolean> duplicateChangeListener;

    public VarNameComboBoxCell(Stage stage) {
        this.parent = stage;
        this.cbName = new ComboBox();
        this.cbName.setId("comboNameOnHH");
        this.cbName.setVisibleRowCount(7);
        this.cbName.setPrefWidth(125.0);
        this.duplicateChangeListener = new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                String string = (String)VarNameComboBoxCell.this.cbName.getSelectionModel().getSelectedItem();
                if (bl2.booleanValue() && VarNameComboBoxCell.this.item.duplicatedProperty().get() && string.equals(VarNameComboBoxCell.this.item.toString())) {
                    VarNameComboBoxCell.this.cbName.getStyleClass().removeAll((Object[])new String[]{"duplicated-varname-combo"});
                    VarNameComboBoxCell.this.cbName.getStyleClass().add((Object)"duplicated-varname-combo");
                } else {
                    VarNameComboBoxCell.this.cbName.getStyleClass().removeAll((Object[])new String[]{"duplicated-varname-combo"});
                }
            }
        };
        this.isRefreshing = false;
        this.addListeners();
    }

    private void addListeners() {
        this.cbName.setCellFactory((Callback)new Callback<ListView<String>, ListCell<String>>(){

            public ListCell<String> call(ListView<String> listView) {
                ListCell<String> listCell = new ListCell<String>(){

                    public void updateItem(String string, boolean bl) {
                        super.updateItem((Object)string, bl);
                        if (string != null) {
                            Label label = new Label();
                            if (string.contains("\uf038")) {
                                label.setFont(Font.font((String)"TI Uni", (double)13.0));
                            }
                            label.setText(string);
                            this.setGraphic((Node)label);
                        }
                    }
                };
                return listCell;
            }

        });
        if (this.cbNameChangeListener == null) {
            this.cbNameChangeListener = new ChangeListener<String>(){

                public void changed(ObservableValue<? extends String> observableValue, final String string, String string2) {
                    if (VarNameComboBoxCell.this.isRefreshing) {
                        return;
                    }
                    if (CUSTOM_NAME.equals(string2)) {
                        NameOnCalculatorDialog nameOnCalculatorDialog = new NameOnCalculatorDialog(VarNameComboBoxCell.this.item.getTIVar(), VarNameComboBoxCell.this.parent);
                        nameOnCalculatorDialog.showAndWait();
                        if (!nameOnCalculatorDialog.isDialogCancelled()) {
                            int n = VarNameComboBoxCell.this.cbName.getItems().indexOf((Object)CUSTOM_NAME);
                            String string3 = nameOnCalculatorDialog.getCustomName();
                            string3 = VarNameComboBoxCell.this.transformName(string3);
                            VarNameComboBoxCell.this.cbName.getItems().add(n, (Object)string3);
                            VarNameComboBoxCell.this.setName(VarNameComboBoxCell.this.item, string3);
                        } else {
                            Platform.runLater((Runnable)new Runnable(){

                                @Override
                                public void run() {
                                    VarNameComboBoxCell.this.cbName.getSelectionModel().select((Object)string);
                                }
                            });
                        }
                    } else {
                        VarNameComboBoxCell.this.setName(VarNameComboBoxCell.this.item, string2);
                        int n = SendToHandheldDialog.getVarDataList().indexOf((Object)VarNameComboBoxCell.this.item.getParent());
                        VarTableData varTableData = (VarTableData)SendToHandheldDialog.getVarDataList().get(n);
                        VarTableData.fireVarNameChanged(varTableData);
                    }
                    VarNameComboBoxCell.this.setDuplicatedNameComboBoxStyle(VarNameComboBoxCell.this.item, VarNameComboBoxCell.this.cbName);
                }

            };
        }
        this.cbName.valueProperty().addListener(this.cbNameChangeListener);
        if (this.cbNameEventHandler == null) {
            this.cbNameEventHandler = new EventHandler<KeyEvent>(){
                Node node;
                int index;

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.TAB) {
                        if (keyEvent.isShiftDown()) {
                            this.index = SendToHandheldDialog.getVarDataList().indexOf((Object)VarNameComboBoxCell.this.item.getParent());
                            if (this.index > 0) {
                                VarTableData varTableData = (VarTableData)SendToHandheldDialog.getVarDataList().get(this.index - 1);
                                this.node = ((TIVarHolder)varTableData.tiVarHolderProperty().get()).getLastNode();
                                Platform.runLater((Runnable)new Runnable(){

                                    @Override
                                    public void run() {
                                        4.this.node.requestFocus();
                                    }
                                });
                            } else {
                                SendToHandheldDialog.setFocusToButtons();
                            }
                        } else {
                            this.node = VarNameComboBoxCell.this.item.getMiddleNode();
                            if (this.node.isDisabled()) {
                                this.node = VarNameComboBoxCell.this.item.getLastNode();
                            }
                            Platform.runLater((Runnable)new Runnable(){

                                @Override
                                public void run() {
                                    4.this.node.requestFocus();
                                }
                            });
                        }
                    }
                }

            };
        }
        this.cbName.addEventFilter(KeyEvent.KEY_PRESSED, this.cbNameEventHandler);
    }

    protected void updateItem(TIVarHolder tIVarHolder, boolean bl) {
        super.updateItem((Object)tIVarHolder, bl);
        if (tIVarHolder != null) {
            this.item = tIVarHolder;
            tIVarHolder.setFirstNode((Node)this.cbName);
            this.isRefreshing = true;
            this.cbName.getItems().clear();
            this.fillComboBox(tIVarHolder, this.cbName);
            String string = (String)this.cbName.getSelectionModel().getSelectedItem();
            this.cbName.setValue((Object)"");
            this.cbName.setValue((Object)string);
            this.isRefreshing = false;
            tIVarHolder.duplicatedProperty().addListener(this.duplicateChangeListener);
            this.setDuplicatedNameComboBoxStyle(tIVarHolder, this.cbName);
            if (VarNameManager.isCustomNameAllowed(tIVarHolder.getTIVar().getVarType())) {
                this.cbName.getItems().add((Object)CUSTOM_NAME);
            }
            this.setGraphic(this.cbName);
        }
    }

    private void setName(TIVarHolder tIVarHolder, String string) {
        VarTableData.removeNameFromList(tIVarHolder);
        tIVarHolder.getTIVar().setDeviceFileName(string);
        VarTableData.addToListOfNames(tIVarHolder);
    }

    private void setDuplicatedNameComboBoxStyle(TIVarHolder tIVarHolder, ComboBox<String> comboBox) {
        String string = (String)comboBox.getSelectionModel().getSelectedItem();
        if (tIVarHolder.duplicatedProperty().get() && string.equals(tIVarHolder.toString())) {
            comboBox.getStyleClass().removeAll((Object[])new String[]{"duplicated-varname-combo"});
            comboBox.getStyleClass().add((Object)"duplicated-varname-combo");
        } else {
            comboBox.getStyleClass().removeAll((Object[])new String[]{"duplicated-varname-combo"});
        }
    }

    private void fillComboBox(TIVarHolder tIVarHolder, ComboBox<String> comboBox) {
        Object[] arrobject = this.getPredefinedNames(tIVarHolder);
        if (arrobject.length == 0) {
            comboBox.getItems().add((Object)tIVarHolder.getTIVar().getDeviceFileName());
            comboBox.getSelectionModel().select(0);
        } else {
            int n;
            comboBox.getItems().addAll(arrobject);
            if (arrobject[0].contains("\uf038")) {
                comboBox.getStyleClass().add((Object)"varname-ti-font-combo");
            }
            if ((n = this.getSelectedIndex(tIVarHolder)) >= 0) {
                comboBox.getSelectionModel().select(n);
            } else if (VarNameManager.isCustomNameAllowed(tIVarHolder.getTIVar().getVarType())) {
                comboBox.getItems().add((Object)tIVarHolder.getTIVar().getDeviceFileName());
                comboBox.getSelectionModel().select(arrobject.length);
            } else {
                comboBox.getSelectionModel().select(0);
                this.setName(tIVarHolder, (String)comboBox.getValue());
            }
        }
    }

    private String[] getPredefinedNames(TIVarHolder tIVarHolder) {
        TIVar tIVar = tIVarHolder.getTIVar();
        int n = tIVar.getVarType();
        if (n == 3) {
            if (VarNameManager.isCustomNameValid(144, tIVar.getDeviceFileName())) {
                n = 144;
            } else if (VarNameManager.isCustomNameValid(145, tIVar.getDeviceFileName())) {
                n = 145;
            } else if (VarNameManager.isCustomNameValid(146, tIVar.getDeviceFileName())) {
                n = 146;
            } else if (VarNameManager.isCustomNameValid(147, tIVar.getDeviceFileName())) {
                n = 147;
            }
        }
        return VarNameManager.getPredefinedNames(n);
    }

    private int getSelectedIndex(TIVarHolder tIVarHolder) {
        TIVar tIVar = tIVarHolder.getTIVar();
        Object[] arrobject = this.getPredefinedNames(tIVarHolder);
        return Arrays.binarySearch(arrobject, tIVar.getDeviceFileName());
    }

    private String transformName(String string) {
        String string2 = string.replaceAll("[\u0398\u03d1\u03f4\u1dbf\u03b8]", "\\.").toUpperCase().replaceAll("\\.", "\u03b8");
        return string2;
    }

}

