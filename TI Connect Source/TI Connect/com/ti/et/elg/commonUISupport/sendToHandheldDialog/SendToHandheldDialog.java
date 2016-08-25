/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.sun.javafx.scene.control.behavior.TableCellBehavior
 *  javafx.application.Platform
 *  javafx.beans.binding.BooleanBinding
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.IntegerProperty
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.property.Property
 *  javafx.beans.property.ReadOnlyBooleanProperty
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.property.SimpleIntegerProperty
 *  javafx.beans.property.SimpleListProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableBooleanValue
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.FXCollections
 *  javafx.collections.ListChangeListener
 *  javafx.collections.ListChangeListener$Change
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventTarget
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.CheckBox
 *  javafx.scene.control.Label
 *  javafx.scene.control.ListCell
 *  javafx.scene.control.ListView
 *  javafx.scene.control.RadioButton
 *  javafx.scene.control.TableCell
 *  javafx.scene.control.TableColumn
 *  javafx.scene.control.TablePosition
 *  javafx.scene.control.TableView
 *  javafx.scene.control.TableView$TableViewFocusModel
 *  javafx.scene.control.TableView$TableViewSelectionModel
 *  javafx.scene.control.Toggle
 *  javafx.scene.control.ToggleGroup
 *  javafx.scene.control.cell.PropertyValueFactory
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.Pane
 *  javafx.scene.layout.VBox
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.Window
 *  javafx.stage.WindowEvent
 *  javafx.util.Callback
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.sun.javafx.scene.control.behavior.TableCellBehavior;
import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.commonUISupport.imageUtils.TIVarMultiplexer;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.DeviceCheckList;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.FileNameCell;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHHDialogConnectionListener;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIDeviceData;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIVarHolder;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarLocationComboBoxCell;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameComboBoxCell;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameManager;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarTableData;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDeviceConnectionListener;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class SendToHandheldDialog {
    @FXML
    ListView<TIDeviceData> listHH;
    @FXML
    static TableView<VarTableData> varTableView;
    @FXML
    static TIDialogButton butnCancel;
    @FXML
    static TIDialogButton butnSend;
    @FXML
    static RadioButton rbutConnectedHH;
    @FXML
    RadioButton rbutSelectHH;
    @FXML
    CheckBox chkbReplaceContent;
    @FXML
    CheckBox chkbGraphArea;
    @FXML
    Pane paneFileNameTable;
    @FXML
    Pane paneDeviceList;
    @FXML
    ToggleGroup grpHandhelds;
    @FXML
    BorderPane paneContents;
    @FXML
    VBox vBoxContents;
    @FXML
    HBox hBoxContents;
    @FXML
    HBox hBoxReplaceExistingContent;
    @FXML
    BorderPane paneGraphArea;
    @FXML
    ImageView imgGraphArea;
    @FXML
    Label lblWarningMsg;
    @FXML
    VBox vBoxDestination;
    @FXML
    VBox vBoxOSWarning;
    @FXML
    Label lblOSFileName;
    @FXML
    VBox vBoxPICVarWarning;
    private static final double ROW_HEIGHT = 36.0;
    private static final double HEADER_HEIGHT = 26.0;
    private static BooleanProperty replaceExistingContent;
    private Stage dialogStage = new Stage();
    private static ObservableList<VarTableData> varDataList;
    private static ObservableList<TIDeviceData> deviceDataList;
    private IntegerProperty numberOfDisabledDevicesSelected = new SimpleIntegerProperty();
    private IntegerProperty numberOfBusyDevices = new SimpleIntegerProperty();
    private ObservableList<TIDevice> busyDevices;
    private boolean dialogCancelled = true;
    private HandHeldTargets targets;
    private DialogMode mode;
    private List<TIVarMultiplexer> tivarmuxes;
    private List<TIDevice> devices;
    private SendToHHDialogConnectionListener connectionListener;
    private ListChangeListener<TIDevice> busyDeviceListener;
    private boolean isSendOS = false;
    private boolean isVarTableVisible = true;
    private boolean isDeviceListVisible = true;

    public SendToHandheldDialog(List<TIVarMultiplexer> list, List<TIDevice> list2, TIDevice tIDevice, HandHeldTargets handHeldTargets, DialogMode dialogMode, ObservableList<TIDevice> observableList) {
        this(list, list2, handHeldTargets, dialogMode, observableList);
        for (TIDeviceData tIDeviceData : deviceDataList) {
            if (tIDeviceData.getTIDevice() != tIDevice) continue;
            deviceDataList.remove((Object)tIDeviceData);
            break;
        }
    }

    public SendToHandheldDialog(List<TIVarMultiplexer> list, List<TIDevice> list2, HandHeldTargets handHeldTargets, DialogMode dialogMode, ObservableList<TIDevice> observableList) {
        this.targets = handHeldTargets;
        this.mode = dialogMode;
        this.tivarmuxes = list;
        this.devices = list2;
        this.busyDevices = observableList;
        this.busyDeviceListener = this.createChangeListener();
        this.initTIVarData(this.tivarmuxes);
        this.initDeviceListData(this.devices);
        this.connectionListener = new SendToHHDialogConnectionListener(deviceDataList);
        ConnectServerFactory.getCommManager().addDeviceConnectionListener(this.connectionListener);
    }

    private ListChangeListener<TIDevice> createChangeListener() {
        return new ListChangeListener<TIDevice>(){

            public void onChanged(ListChangeListener.Change<? extends TIDevice> change) {
                while (change.next()) {
                    for (TIDevice tIDevice2 : change.getAddedSubList()) {
                        SendToHandheldDialog.this.setDeviceBusy(tIDevice2, true);
                    }
                    for (TIDevice tIDevice2 : change.getRemoved()) {
                        SendToHandheldDialog.this.setDeviceBusy(tIDevice2, false);
                    }
                }
            }
        };
    }

    private void setDeviceBusy(TIDevice tIDevice, boolean bl) {
        for (TIDeviceData tIDeviceData : deviceDataList) {
            if (tIDeviceData.getTIDevice() != tIDevice) continue;
            tIDeviceData.busyProperty().set(bl);
            if (bl) {
                this.numberOfBusyDevices.set(this.numberOfBusyDevices.get() + 1);
                if (!tIDeviceData.selectedProperty().get()) break;
                this.numberOfDisabledDevicesSelected.set(this.numberOfDisabledDevicesSelected.get() + 1);
                break;
            }
            this.numberOfBusyDevices.set(this.numberOfBusyDevices.get() - 1);
            if (!tIDeviceData.selectedProperty().get()) break;
            this.numberOfDisabledDevicesSelected.set(this.numberOfDisabledDevicesSelected.get() - 1);
            break;
        }
    }

    private void initTIVarData(List<TIVarMultiplexer> list) {
        final List<TIVarMultiplexer> list2 = list;
        for (final TIVarMultiplexer tIVarMultiplexer : list2) {
            if (VarNameManager.isOSType(tIVarMultiplexer.getTIVar().getVarType())) {
                this.isSendOS = true;
            }
            final VarTableData varTableData = new VarTableData(tIVarMultiplexer);
            varTableData.deletedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (bl2.booleanValue()) {
                        list2.remove(tIVarMultiplexer);
                        varDataList.remove((Object)varTableData);
                    }
                }
            });
            varDataList.add((Object)varTableData);
        }
    }

    private void initDeviceListData(final List<TIDevice> list) {
        deviceDataList.addListener((ListChangeListener)new ListChangeListener<TIDeviceData>(){

            public void onChanged(ListChangeListener.Change<? extends TIDeviceData> change) {
                while (change.next()) {
                    if (!change.wasAdded()) continue;
                    List list2 = change.getAddedSubList();
                    for (final TIDeviceData tIDeviceData : list2) {
                        tIDeviceData.selectedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                                if (bl2.booleanValue()) {
                                    if (!list.contains(tIDeviceData.getTIDevice())) {
                                        list.add(tIDeviceData.getTIDevice());
                                    }
                                } else if (list.contains(tIDeviceData.getTIDevice())) {
                                    list.remove(tIDeviceData.getTIDevice());
                                }
                            }
                        });
                    }
                }
            }

        });
        this.busyDevices.addListener(this.busyDeviceListener);
        Collection<TIDevice> collection = ConnectServerFactory.getCommManager().getConnectedDevices();
        for (TIDevice tIDevice : collection) {
            TIDeviceData tIDeviceData = new TIDeviceData(tIDevice);
            deviceDataList.add((Object)tIDeviceData);
            if (!this.busyDevices.contains((Object)tIDevice) && tIDevice.isOSpresent() && (list.contains(tIDevice) || this.targets == HandHeldTargets.ALL_CONNECTED && list.contains(tIDevice))) {
                tIDeviceData.selectedProperty().set(true);
            } else if (list.contains(tIDeviceData.getTIDevice())) {
                list.remove(tIDeviceData.getTIDevice());
            }
            if (!this.busyDevices.contains((Object)tIDevice)) continue;
            tIDeviceData.busyProperty().set(true);
        }
    }

    private void addHandheldTargetListener() {
        this.grpHandhelds.selectedToggleProperty().addListener((ChangeListener)new ChangeListener<Toggle>(){

            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle2) {
                if (toggle2.equals((Object)SendToHandheldDialog.this.rbutSelectHH)) {
                    if (!SendToHandheldDialog.this.vBoxContents.getChildren().contains(SendToHandheldDialog.this.listHH)) {
                        SendToHandheldDialog.this.showDeviceList();
                    }
                    SendToHandheldDialog.this.connectionListener.setSelectNewDevices(false);
                } else {
                    if (SendToHandheldDialog.this.vBoxContents.getChildren().contains(SendToHandheldDialog.this.listHH)) {
                        SendToHandheldDialog.this.hideDeviceList();
                    }
                    for (TIDeviceData tIDeviceData : deviceDataList) {
                        if (SendToHandheldDialog.this.busyDevices.contains((Object)tIDeviceData.getTIDevice()) || !tIDeviceData.getTIDevice().isOSpresent()) continue;
                        tIDeviceData.selectedProperty().set(true);
                    }
                    SendToHandheldDialog.this.connectionListener.setSelectNewDevices(true);
                }
            }
        });
    }

    public boolean isDialogCancelled() {
        return this.dialogCancelled;
    }

    private void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/sendToHandheldDialog/sendToHandheldDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Parent parent = (Parent)fXMLLoader.load();
            Scene scene = new Scene(parent);
            this.addHandheldTargetListener();
            this.initTableView();
            this.initDeviceList();
            butnSend.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    SendToHandheldDialog.this.dialogCancelled = false;
                    SendToHandheldDialog.this.cleanup();
                    SendToHandheldDialog.this.dialogStage.close();
                }
            });
            butnCancel.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    SendToHandheldDialog.this.dialogCancelled = true;
                    SendToHandheldDialog.this.cleanup();
                    SendToHandheldDialog.this.dialogStage.close();
                }
            });
            scene.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        SendToHandheldDialog.this.dialogCancelled = true;
                        SendToHandheldDialog.this.cleanup();
                        SendToHandheldDialog.this.dialogStage.close();
                    }
                }
            });
            this.dialogStage.setOnCloseRequest((EventHandler)new EventHandler<WindowEvent>(){

                public void handle(WindowEvent windowEvent) {
                    SendToHandheldDialog.this.cleanup();
                }
            });
            this.chkbGraphArea.setDisable(!VarTableData.containsColorScreenImageVars());
            this.chkbGraphArea.selectedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (bl2.booleanValue()) {
                        SendToHandheldDialog.this.imgGraphArea.setImage(new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/send to hh dialog_graph area.png").toExternalForm()));
                    } else {
                        SendToHandheldDialog.this.imgGraphArea.setImage(new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/send to hh dialog_whole screen.png").toExternalForm()));
                    }
                }
            });
            this.showDuplicatedDialog();
            this.checkForBusyDevices();
            rbutConnectedHH.disableProperty().bind((ObservableValue)this.numberOfBusyDevices.greaterThan(0));
            if (this.targets == HandHeldTargets.SELECTED) {
                this.rbutSelectHH.setSelected(true);
            } else if (rbutConnectedHH.disabledProperty().get()) {
                this.rbutSelectHH.setSelected(true);
            } else {
                rbutConnectedHH.setSelected(true);
            }
            butnSend.disableProperty().bind((ObservableValue)VarTableData.numberOfDuplicatesProperty().greaterThan(0).or((ObservableBooleanValue)TIDeviceData.numberOfSelectedDevices().lessThan(1)).or((ObservableBooleanValue)this.numberOfDisabledDevicesSelected.greaterThan(0)));
            this.chkbReplaceContent.selectedProperty().bindBidirectional((Property)replaceExistingContent);
            this.prepareDialogForSendOS();
            this.prepareDialogForPicVarWarning();
            this.dialogStage.setResizable(false);
            this.dialogStage.setScene(scene);
            this.dialogStage.initModality(Modality.APPLICATION_MODAL);
            this.dialogStage.initOwner((Window)CommonConstants.MAIN_STAGE);
            this.dialogStage.getScene().getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
            parent.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        SendToHandheldDialog.this.dialogCancelled = true;
                        SendToHandheldDialog.this.cleanup();
                        SendToHandheldDialog.this.dialogStage.close();
                    } else if (keyEvent.getCode() == KeyCode.TAB) {
                        Node node;
                        if (keyEvent.isShiftDown()) {
                            if (keyEvent.getTarget().equals((Object)SendToHandheldDialog.rbutConnectedHH)) {
                                if (varDataList.size() > 0) {
                                    Platform.runLater((Runnable)new Runnable(){

                                        @Override
                                        public void run() {
                                            SendToHandheldDialog.varTableView.scrollTo(varDataList.size() - 1);
                                            SendToHandheldDialog.this.setFocusToLastElementTableView();
                                        }
                                    });
                                }
                            } else if (keyEvent.getTarget().equals((Object)SendToHandheldDialog.this.chkbReplaceContent)) {
                                if (deviceDataList.size() > 0) {
                                    if (SendToHandheldDialog.this.isDeviceListVisible) {
                                        SendToHandheldDialog.this.listHH.scrollTo(deviceDataList.size() - 1);
                                    }
                                    Platform.runLater((Runnable)new Runnable(){

                                        @Override
                                        public void run() {
                                            if (SendToHandheldDialog.this.isDeviceListVisible) {
                                                SendToHandheldDialog.this.setFocusToLastElementListView();
                                            } else {
                                                SendToHandheldDialog.varTableView.scrollTo(varDataList.size() - 1);
                                                SendToHandheldDialog.this.setFocusToLastElementTableView();
                                            }
                                        }
                                    });
                                } else {
                                    Platform.runLater((Runnable)new Runnable(){

                                        @Override
                                        public void run() {
                                            SendToHandheldDialog.this.rbutSelectHH.requestFocus();
                                        }
                                    });
                                }
                            }
                        } else if (keyEvent.getTarget().equals((Object)SendToHandheldDialog.butnCancel) && SendToHandheldDialog.butnSend.isDisabled() || keyEvent.getTarget().equals((Object)SendToHandheldDialog.butnSend)) {
                            if (varDataList.size() > 0) {
                                SendToHandheldDialog.varTableView.scrollTo(0);
                                Platform.runLater((Runnable)new Runnable(){

                                    @Override
                                    public void run() {
                                        SendToHandheldDialog.this.setFocusToFirstElementTableView();
                                    }
                                });
                            }
                        } else if (keyEvent.getTarget().equals((Object)SendToHandheldDialog.this.rbutSelectHH)) {
                            if (deviceDataList.size() > 0) {
                                SendToHandheldDialog.this.listHH.scrollTo(0);
                                Platform.runLater((Runnable)new Runnable(){

                                    @Override
                                    public void run() {
                                        SendToHandheldDialog.this.setFocusToFirstElementListView();
                                    }
                                });
                            } else {
                                Platform.runLater((Runnable)new Runnable(){

                                    @Override
                                    public void run() {
                                        SendToHandheldDialog.this.chkbReplaceContent.requestFocus();
                                    }
                                });
                            }
                        } else if (deviceDataList.size() > 0 && !SendToHandheldDialog.this.isDeviceListVisible && (node = ((TIVarHolder)((VarTableData)varDataList.get(varDataList.size() - 1)).tiVarHolderProperty().get()).getLastNode()) != null && keyEvent.getTarget().equals((Object)node)) {
                            Platform.runLater((Runnable)new Runnable(){

                                @Override
                                public void run() {
                                    SendToHandheldDialog.this.chkbReplaceContent.requestFocus();
                                }
                            });
                        }
                    }
                }

            });
            butnSend.requestFocus();
            if (PlatformManager.isWindows()) {
                this.dialogStage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
            }
        }
        catch (IOException var1_2) {
            TILogger.logError("SendToHandheldDialog", "showAndWait", var1_2);
        }
    }

    private void setFocusToFirstElementTableView() {
        if (this.isVarTableVisible && varDataList.get(0) != null) {
            Node node = ((TIVarHolder)((VarTableData)varDataList.get(0)).tiVarHolderProperty().get()).getFirstNode();
            node.requestFocus();
        }
    }

    private void setFocusToLastElementTableView() {
        if (this.isVarTableVisible && varDataList.size() > 0 && varDataList.get(varDataList.size() - 1) != null) {
            Node node = null;
            if (varDataList.size() == 1) {
                node = ((TIVarHolder)((VarTableData)varDataList.get(varDataList.size() - 1)).tiVarHolderProperty().get()).getMiddleNode();
                if (node == null || node.isDisabled()) {
                    node = ((TIVarHolder)((VarTableData)varDataList.get(varDataList.size() - 1)).tiVarHolderProperty().get()).getFirstNode();
                }
            } else {
                node = ((TIVarHolder)((VarTableData)varDataList.get(varDataList.size() - 1)).tiVarHolderProperty().get()).getLastNode();
            }
            node.requestFocus();
        }
    }

    private void setFocusToFirstElementListView() {
        if (this.isDeviceListVisible && deviceDataList.get(0) != null) {
            Node node = ((TIDeviceData)deviceDataList.get(0)).getInstanceNode();
            node.requestFocus();
        }
    }

    private void setFocusToLastElementListView() {
        if (this.isDeviceListVisible && deviceDataList.size() > 0 && deviceDataList.get(deviceDataList.size() - 1) != null) {
            Node node = ((TIDeviceData)deviceDataList.get(deviceDataList.size() - 1)).getInstanceNode();
            node.requestFocus();
        }
    }

    public static void setFocusToButtons() {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                if (SendToHandheldDialog.butnSend.isDisabled()) {
                    SendToHandheldDialog.butnCancel.requestFocus();
                } else {
                    SendToHandheldDialog.butnSend.requestFocus();
                }
            }
        });
    }

    public static void setFocusToRadioButons() {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                SendToHandheldDialog.rbutConnectedHH.requestFocus();
            }
        });
    }

    public static ObservableList<VarTableData> getVarDataList() {
        return varDataList;
    }

    public static ObservableList<TIDeviceData> getDeviceDataList() {
        return deviceDataList;
    }

    public static void setScrollPosition(int n) {
        varTableView.scrollTo(n);
    }

    private void prepareDialogForSendOS() {
        String string;
        String string2 = CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.oswarning.message");
        this.lblWarningMsg.setText(string2);
        if (this.isSendOS) {
            string = CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.os.title");
            VarTableData varTableData = (VarTableData)varDataList.get(0);
            TIVar tIVar = ((TIVarHolder)varTableData.tiVarHolderProperty().get()).getTIVar();
            this.lblOSFileName.setText(tIVar.getHostFile().getName());
            for (TIDeviceData tIDeviceData : deviceDataList) {
                if (tIDeviceData.getTIDevice().isOSpresent()) continue;
                tIDeviceData.selectedProperty().set(true);
            }
        } else {
            string = CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.title");
            this.hideOSWarning();
        }
        this.dialogStage.setTitle(string);
    }

    private void prepareDialogForPicVarWarning() {
        if (!VarTableData.containsConvertedImageVars()) {
            this.hidePicVarWarning();
        }
    }

    private void checkForBusyDevices() {
        for (TIDeviceData tIDeviceData : deviceDataList) {
            if (tIDeviceData.selectedProperty().get()) {
                if (!tIDeviceData.busyProperty().get()) continue;
                this.numberOfDisabledDevicesSelected.set(this.numberOfDisabledDevicesSelected.get() + 1);
                this.numberOfBusyDevices.set(this.numberOfBusyDevices.get() + 1);
                continue;
            }
            if (!tIDeviceData.busyProperty().get()) continue;
            this.numberOfBusyDevices.set(this.numberOfBusyDevices.get() + 1);
        }
    }

    private void showDuplicatedDialog() {
        if (VarTableData.containsDuplicatedNames()) {
            PromptDialog.showUserError(CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.duplicateNamesDialog.title"), CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.duplicateNamesDialog.message"), new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
        }
    }

    private void cleanup() {
        ConnectServerFactory.getCommManager().removeDeviceConnectionListener(this.connectionListener);
        this.busyDevices.removeListener(this.busyDeviceListener);
        VarTableData.removeAllVarNameChangedListener();
        TIDeviceData.numberOfSelectedDevices().set(0);
        VarTableData.clearListOfNames();
        varDataList.clear();
        deviceDataList.clear();
        UserPropertyManagement.setBoolean("SendToHH.ReplaceFiles.enabled", replaceExistingContent.get());
        if (varTableView != null && varTableView.getFocusModel() != null) {
            varTableView.getFocusModel().focus(null);
            try {
                reference var1_1 = TableCellBehavior.class;
                Method method = var1_1.getDeclaredMethod("setAnchor", TableView.class, TablePosition.class);
                method.setAccessible(true);
                method.invoke(null, new Object[]{varTableView, null});
            }
            catch (Exception var1_2) {
                TILogger.logError("SendToHandheldDialog", "Error removing references to TableView", var1_2);
            }
            varTableView.setSelectionModel(null);
            varTableView.getColumns().clear();
            varTableView.setItems(FXCollections.observableList((List)new SimpleListProperty()));
            varTableView = null;
        }
    }

    private void initDeviceList() {
        this.listHH.setItems(deviceDataList);
        this.listHH.setCellFactory((Callback)new Callback<ListView<TIDeviceData>, ListCell<TIDeviceData>>(){

            public ListCell<TIDeviceData> call(ListView<TIDeviceData> listView) {
                return new DeviceCheckList(SendToHandheldDialog.this.isSendOS);
            }
        });
    }

    private void initTableView() {
        varTableView.setItems(varDataList);
        TableColumn<VarTableData, TIVarHolder> tableColumn = this.createColumn(CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.filename"), 205);
        tableColumn.setCellFactory((Callback)new Callback<TableColumn<VarTableData, TIVarHolder>, TableCell<VarTableData, TIVarHolder>>(){

            public FileNameCell call(TableColumn<VarTableData, TIVarHolder> tableColumn) {
                return new FileNameCell();
            }
        });
        TableColumn<VarTableData, TIVarHolder> tableColumn2 = this.createColumn(CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.varname"), 195);
        tableColumn2.setCellFactory((Callback)new Callback<TableColumn<VarTableData, TIVarHolder>, TableCell<VarTableData, TIVarHolder>>(){

            public TableCell<VarTableData, TIVarHolder> call(TableColumn<VarTableData, TIVarHolder> tableColumn) {
                return new VarNameComboBoxCell(SendToHandheldDialog.this.dialogStage);
            }
        });
        TableColumn<VarTableData, TIVarHolder> tableColumn3 = this.createColumn(CommonUISupportResourceBundle.BUNDLE.getString("sendToHandheldDialog.location"), 175);
        tableColumn3.setCellFactory((Callback)new Callback<TableColumn<VarTableData, TIVarHolder>, TableCell<VarTableData, TIVarHolder>>(){

            public TableCell<VarTableData, TIVarHolder> call(TableColumn<VarTableData, TIVarHolder> tableColumn) {
                return new VarLocationComboBoxCell(varDataList);
            }
        });
        varTableView.getColumns().add(tableColumn);
        varTableView.getColumns().add(tableColumn2);
        varTableView.getColumns().add(tableColumn3);
        varTableView.setPrefWidth(600.0);
        if (varDataList.size() <= 5) {
            varTableView.setPrefHeight((double)varDataList.size() * 36.0 + 26.0);
        }
    }

    private TableColumn<VarTableData, TIVarHolder> createColumn(String string, int n) {
        TableColumn tableColumn = new TableColumn(string);
        tableColumn.setMinWidth((double)n);
        tableColumn.setCellValueFactory((Callback)new PropertyValueFactory("tiVarHolder"));
        return tableColumn;
    }

    public void showAndWait() {
        if (PromptDialog.arePromptsSupressed()) {
            this.dialogCancelled = false;
            this.checkForBusyDevices();
            return;
        }
        this.init();
        if (this.isSendOS) {
            this.hideVariableNameTable();
            this.hideReplaceExistingContent();
        }
        switch (this.mode) {
            case DEVICE_EXPLORER: {
                this.showDeviceExplorerMode();
                break;
            }
            case HANDHELD_TO_HANDHELD: {
                this.showHandheldToHandheldMode();
                break;
            }
            case SCREEN_CAPTURE: {
                this.showScreenCaptureMode();
                break;
            }
            case SV_EMULATOR_EXPLORER: {
                this.showSVEmulatorExplorer();
                break;
            }
            case SV_SCREEN_CAPTURE: {
                this.showSVScreenCapture();
            }
        }
    }

    public boolean isConfirmOverride() {
        if (PromptDialog.arePromptsSupressed()) {
            return true;
        }
        return this.chkbReplaceContent.isSelected();
    }

    public boolean isGraphAreaOnly() {
        return this.chkbGraphArea.isSelected();
    }

    private void showDeviceExplorerMode() {
        this.hideGraphArea();
        this.dialogStage.showAndWait();
    }

    private void showHandheldToHandheldMode() {
        this.hideGraphArea();
        this.hideVariableNameTable();
        this.dialogStage.showAndWait();
    }

    private void showScreenCaptureMode() {
        this.dialogStage.showAndWait();
    }

    private void showSVEmulatorExplorer() {
        this.hideGraphArea();
        this.showSmartViewMode();
    }

    private void showSVScreenCapture() {
        this.showSmartViewMode();
    }

    private void showSmartViewMode() {
        this.hidePicVarWarning();
        this.hideDeviceList();
        this.hideDestination();
        this.hideOSWarning();
        this.dialogStage.showAndWait();
    }

    private void hideVariableNameTable() {
        if (this.vBoxContents.getChildren().contains(varTableView)) {
            this.vBoxContents.getChildren().remove(varTableView);
            this.dialogStage.sizeToScene();
            this.isVarTableVisible = false;
        }
    }

    private void hideGraphArea() {
        if (this.hBoxContents.getChildren().contains((Object)this.paneGraphArea)) {
            this.hBoxContents.getChildren().remove((Object)this.paneGraphArea);
            this.dialogStage.sizeToScene();
        }
    }

    private void showDeviceList() {
        if (this.vBoxContents.getChildren().contains((Object)this.hBoxReplaceExistingContent)) {
            this.vBoxContents.getChildren().add(this.vBoxContents.getChildren().indexOf((Object)this.hBoxReplaceExistingContent), this.listHH);
        } else {
            this.vBoxContents.getChildren().add(this.listHH);
        }
        this.dialogStage.sizeToScene();
        this.isDeviceListVisible = true;
    }

    private void hideDeviceList() {
        this.vBoxContents.getChildren().remove(this.listHH);
        this.dialogStage.sizeToScene();
        this.isDeviceListVisible = false;
    }

    private void hideDestination() {
        this.vBoxContents.getChildren().remove((Object)this.vBoxDestination);
        this.dialogStage.sizeToScene();
    }

    private void hideOSWarning() {
        this.vBoxContents.getChildren().remove((Object)this.vBoxOSWarning);
        this.dialogStage.sizeToScene();
    }

    private void hidePicVarWarning() {
        this.vBoxContents.getChildren().remove((Object)this.vBoxPICVarWarning);
        this.dialogStage.sizeToScene();
    }

    private void hideReplaceExistingContent() {
        this.vBoxContents.getChildren().remove((Object)this.hBoxReplaceExistingContent);
        this.dialogStage.sizeToScene();
    }

    public IntegerProperty numberOfDisabledDevicesSelectedProperty() {
        return this.numberOfDisabledDevicesSelected;
    }

    public IntegerProperty getNumberOfBusyDevices() {
        return this.numberOfBusyDevices;
    }

    static {
        replaceExistingContent = new SimpleBooleanProperty();
        replaceExistingContent.set(UserPropertyManagement.getBoolean("SendToHH.ReplaceFiles.enabled", false));
        varDataList = FXCollections.observableArrayList();
        deviceDataList = FXCollections.observableArrayList();
    }

    public static enum DialogMode {
        HANDHELD_TO_HANDHELD,
        SCREEN_CAPTURE,
        DEVICE_EXPLORER,
        SV_EMULATOR_EXPLORER,
        SV_SCREEN_CAPTURE;
        

        private DialogMode() {
        }
    }

    public static enum HandHeldTargets {
        ALL_CONNECTED,
        SELECTED;
        

        private HandHeldTargets() {
        }
    }

}

