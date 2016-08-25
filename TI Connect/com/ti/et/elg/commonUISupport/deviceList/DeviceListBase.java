/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.animation.FadeTransition
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.FloatProperty
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.property.StringProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.collections.ObservableSet
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.ContextMenu
 *  javafx.scene.control.Label
 *  javafx.scene.control.ListCell
 *  javafx.scene.control.ListView
 *  javafx.scene.control.MenuItem
 *  javafx.scene.control.MultipleSelectionModel
 *  javafx.scene.control.SelectionMode
 *  javafx.scene.control.SeparatorMenuItem
 *  javafx.scene.control.Tooltip
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.input.ContextMenuEvent
 *  javafx.scene.input.DragEvent
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.input.TransferMode
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.Pane
 *  javafx.scene.layout.VBox
 *  javafx.util.Callback
 *  javafx.util.Duration
 */
package com.ti.et.elg.commonUISupport.deviceList;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import com.ti.et.elg.commonUISupport.customComponents.CustomBackgroundProgressBar;
import com.ti.et.elg.commonUISupport.deviceList.TIDeviceHolder;
import com.ti.et.elg.commonUISupport.deviceList.TakeScreenListener;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;

public abstract class DeviceListBase {
    @FXML
    ListView<TIDeviceHolder> listHH;
    @FXML
    Label lblHHAllConnected;
    @FXML
    Label lblHHAllConnectedCount;
    @FXML
    BorderPane paneAllConnected;
    @FXML
    BorderPane deviceListPane;
    @FXML
    Button HandheldsButton;
    @FXML
    HBox allConnectedBox;
    private Button butnHHScreenCaptureAllConnected;
    private HBox hBoxHHScreenCaptureAllConnected;
    private ObservableList<TIDeviceHolder> devicesList = FXCollections.observableArrayList();
    private TIDevice selectedDevice = null;
    private ObservableSet<TIDevice> busyDeviceSet = FXCollections.observableSet((Object[])new Object[0]);
    private Node rootNode = null;
    private ResourceBundle bundle;
    private int mode;
    public Vector<TakeScreenListener> takeScreenListener = new Vector();
    private ContextMenu contextMenu = null;
    protected TIMenuItem refreshMenuItem;
    protected TIMenuItem sendOSMenuItem;
    protected TIMenuItem handheldInformationMenuItem;
    private int lastFoundDeviceIndex;

    protected void init(ResourceBundle resourceBundle) {
        try {
            this.bundle = resourceBundle;
            FXMLLoader fXMLLoader = new FXMLLoader(DeviceListBase.class.getResource("/com/ti/et/elg/commonUISupport/deviceList/deviceList.fxml"));
            fXMLLoader.setResources(resourceBundle);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
            this.listHH.setItems(this.devicesList);
            this.listHH.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            this.initHBoxHHScreenCaptureAllConnected();
            this.listHH.setCellFactory((Callback)new Callback<ListView<TIDeviceHolder>, ListCell<TIDeviceHolder>>(){

                public ListCell<TIDeviceHolder> call(ListView<TIDeviceHolder> listView) {
                    final DeviceListCell deviceListCell = new DeviceListCell(DeviceListBase.this.bundle);
                    deviceListCell.setOnDragDropped((EventHandler)new EventHandler<DragEvent>(){

                        public void handle(DragEvent dragEvent) {
                            DeviceListBase.this.handleDropOnDevice(((TIDeviceHolder)deviceListCell.getItem()).getTIDevice(), dragEvent);
                        }
                    });
                    deviceListCell.setOnDragOver((EventHandler)new EventHandler<DragEvent>(){

                        public void handle(DragEvent dragEvent) {
                            if (deviceListCell.getItem() != null) {
                                DeviceListBase.this.handleDragOverDevice(((TIDeviceHolder)deviceListCell.getItem()).getTIDevice(), dragEvent);
                                if (dragEvent.getAcceptedTransferMode() == TransferMode.COPY) {
                                    ((TIDeviceHolder)deviceListCell.getItem()).setDragOverAccepted(true);
                                }
                            }
                        }
                    });
                    deviceListCell.setOnDragDone((EventHandler)new EventHandler<DragEvent>(){

                        public void handle(DragEvent dragEvent) {
                            if (deviceListCell.getItem() != null) {
                                ((TIDeviceHolder)deviceListCell.getItem()).setDragOverAccepted(false);
                            }
                        }
                    });
                    deviceListCell.setOnDragExited((EventHandler)new EventHandler<DragEvent>(){

                        public void handle(DragEvent dragEvent) {
                            if (deviceListCell.getItem() != null) {
                                ((TIDeviceHolder)deviceListCell.getItem()).setDragOverAccepted(false);
                            }
                        }
                    });
                    return deviceListCell;
                }

            });
            this.listHH.getSelectionModel().selectedItemProperty().addListener((ChangeListener)new ChangeListener<TIDeviceHolder>(){

                public void changed(ObservableValue<? extends TIDeviceHolder> observableValue, TIDeviceHolder tIDeviceHolder, TIDeviceHolder tIDeviceHolder2) {
                    DeviceListBase.this.selectedDevice = tIDeviceHolder2 == null ? null : tIDeviceHolder2.getTIDevice();
                    DeviceListBase.this.notifyDeviceSelectionChanged(DeviceListBase.this.selectedDevice);
                }
            });
            this.paneAllConnected.setOnDragDropped((EventHandler)new EventHandler<DragEvent>(){

                public void handle(DragEvent dragEvent) {
                    DeviceListBase.this.handleDropOnPaneAllConnected(dragEvent);
                }
            });
            this.paneAllConnected.setOnDragOver((EventHandler)new EventHandler<DragEvent>(){

                public void handle(DragEvent dragEvent) {
                    DeviceListBase.this.handleDragOverPaneAllConnected(dragEvent);
                    if (dragEvent.getAcceptedTransferMode() == TransferMode.COPY) {
                        if (DeviceListBase.this.allConnectedBox.getStyleClass().contains((Object)"dragOverNone")) {
                            DeviceListBase.this.allConnectedBox.getStyleClass().remove((Object)"dragOverNone");
                        }
                        DeviceListBase.this.allConnectedBox.getStyleClass().add((Object)"dragOverAllConnected");
                    }
                }
            });
            this.paneAllConnected.setOnDragDone((EventHandler)new EventHandler<DragEvent>(){

                public void handle(DragEvent dragEvent) {
                    if (DeviceListBase.this.allConnectedBox.getStyleClass().contains((Object)"dragOverAllConnected")) {
                        DeviceListBase.this.allConnectedBox.getStyleClass().remove((Object)"dragOverAllConnected");
                    }
                    DeviceListBase.this.allConnectedBox.getStyleClass().add((Object)"dragOverNone");
                }
            });
            this.paneAllConnected.setOnDragExited((EventHandler)new EventHandler<DragEvent>(){

                public void handle(DragEvent dragEvent) {
                    if (DeviceListBase.this.allConnectedBox.getStyleClass().contains((Object)"dragOverAllConnected")) {
                        DeviceListBase.this.allConnectedBox.getStyleClass().remove((Object)"dragOverAllConnected");
                    }
                    DeviceListBase.this.allConnectedBox.getStyleClass().add((Object)"dragOverNone");
                }
            });
            this.createContextMenu();
            this.rootNode.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>(){

                public void handle(ContextMenuEvent contextMenuEvent) {
                    DeviceListBase.this.contextMenu.show(DeviceListBase.this.rootNode, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                }
            });
            if (PlatformManager.isMac()) {
                this.rootNode.addEventFilter(MouseEvent.MOUSE_CLICKED, (EventHandler)new EventHandler<MouseEvent>(){

                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.isControlDown()) {
                            DeviceListBase.this.contextMenu.show(DeviceListBase.this.rootNode, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        }
                    }
                });
            }
            this.deviceListPane.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        DeviceListBase.this.handleEnterOnDeviceListItem(DeviceListBase.this.selectedDevice);
                    } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        DeviceListBase.this.handleCancelOnDeviceListItem(DeviceListBase.this.selectedDevice);
                    }
                }
            });
        }
        catch (IOException var2_3) {
            TILogger.logError("DeviceListBase", "init", var2_3);
        }
    }

    protected void createContextMenu() {
        if (this.contextMenu == null) {
            this.contextMenu = new ContextMenu();
            this.contextMenu.setId("ctxMenuDeviceList");
            this.contextMenu.impl_showRelativeToWindowProperty().set(true);
            this.refreshMenuItem = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.refresh"));
            this.refreshMenuItem.setId("popupmenuRefresh");
            this.sendOSMenuItem = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.sendOS"));
            this.sendOSMenuItem.setId("popupmenuSendOS");
            this.handheldInformationMenuItem = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.handheldInformation"));
            this.handheldInformationMenuItem.setId("popupmenuCalculatorInf");
            this.contextMenu.getItems().addAll((Object[])new MenuItem[]{this.refreshMenuItem.getMenuItem(), new SeparatorMenuItem(), this.handheldInformationMenuItem.getMenuItem(), new SeparatorMenuItem(), this.sendOSMenuItem.getMenuItem()});
        }
    }

    protected abstract void handleEnterOnDeviceListItem(TIDevice var1);

    protected abstract void handleCancelOnDeviceListItem(TIDevice var1);

    protected abstract void handleDropOnDevice(TIDevice var1, DragEvent var2);

    protected abstract void handleDragOverDevice(TIDevice var1, DragEvent var2);

    protected abstract void handleDragOverPaneAllConnected(DragEvent var1);

    protected abstract void handleDropOnPaneAllConnected(DragEvent var1);

    protected abstract void notifyDeviceSelectionChanged(TIDevice var1);

    protected abstract void notifyConnectedHandhelds(int var1);

    protected abstract void notifyDeviceWithoutOSConnected(TIDevice var1);

    protected abstract void notifyDisconnectedHandhelds(int var1);

    protected abstract void notifyDeviceWithoutOSDisonnected(TIDevice var1);

    protected void notifyScreenCapture(TIDevice tIDevice) {
        for (TakeScreenListener takeScreenListener : this.takeScreenListener) {
            takeScreenListener.takeScreenCapture(tIDevice);
        }
    }

    public void notifyScreenCapture() {
        List<TIDevice> list = this.getConnectedDevices();
        list.removeAll(this.getBusyDeviceSet());
        for (TakeScreenListener takeScreenListener : this.takeScreenListener) {
            takeScreenListener.takeScreenCapture(list);
        }
    }

    protected void busyDevice(TIDevice tIDevice, boolean bl) {
        if (bl) {
            this.busyDeviceSet.add((Object)tIDevice);
        } else {
            this.busyDeviceSet.remove((Object)tIDevice);
        }
        if (this.busyDeviceSet.isEmpty()) {
            this.paneAllConnected.disableProperty().set(false);
        } else {
            this.paneAllConnected.disableProperty().set(true);
        }
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public Node getFocusableNode() {
        return this.listHH;
    }

    public void addTakeScreenListener(TakeScreenListener takeScreenListener) {
        this.takeScreenListener.add(takeScreenListener);
    }

    private void refreshConnectedHandheldCount() {
        if (this.devicesList.isEmpty()) {
            this.lblHHAllConnectedCount.setText("");
            this.butnHHScreenCaptureAllConnected.disableProperty().set(true);
        } else {
            this.lblHHAllConnectedCount.setText("(" + this.devicesList.size() + ")");
            this.butnHHScreenCaptureAllConnected.disableProperty().set(false);
        }
    }

    private void clearDeviceListFadeInAnimations() {
        for (TIDeviceHolder tIDeviceHolder : this.devicesList) {
            tIDeviceHolder.setFadeInAnimationPending(false);
        }
    }

    private void clearDeviceFadeAnimation(TIDeviceHolder tIDeviceHolder) {
        tIDeviceHolder.setFadeAnimationPending(false);
    }

    public void addTIDevice(TIDevice tIDevice) {
        TIDeviceHolder tIDeviceHolder = new TIDeviceHolder(tIDevice);
        if (!this.suppressAnimations()) {
            this.clearDeviceListFadeInAnimations();
            for (TIDeviceHolder tIDeviceHolder2 : this.devicesList) {
                if (!tIDeviceHolder2.getTIDevice().getConnectionID().equalsIgnoreCase(tIDevice.getConnectionID())) continue;
                this.clearDeviceFadeAnimation(tIDeviceHolder2);
            }
        }
        this.devicesList.add((Object)tIDeviceHolder);
        this.refreshConnectedHandheldCount();
        this.notifyConnectedHandhelds(this.devicesList.size());
        if (this.devicesList.size() == 1) {
            this.listHH.getSelectionModel().select((Object)tIDeviceHolder);
        }
        if (!tIDevice.isOSpresent()) {
            this.notifyDeviceWithoutOSConnected(tIDevice);
        }
    }

    public void removeTIDevice(final TIDevice tIDevice) {
        final TIDeviceHolder tIDeviceHolder = this.findDeviceHolder(tIDevice);
        if (tIDeviceHolder != null) {
            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    DeviceListBase.this.clearDeviceListFadeInAnimations();
                    boolean bl = false;
                    if (DeviceListBase.this.devicesList.size() > 1 && DeviceListBase.this.selectedDevice != null && tIDevice == DeviceListBase.this.selectedDevice) {
                        bl = true;
                    }
                    DeviceListBase.this.devicesList.remove((Object)tIDeviceHolder);
                    if (bl) {
                        if (DeviceListBase.this.lastFoundDeviceIndex == 0) {
                            DeviceListBase.this.listHH.getSelectionModel().select(DeviceListBase.this.lastFoundDeviceIndex);
                        } else {
                            DeviceListBase.this.lastFoundDeviceIndex--;
                            DeviceListBase.this.listHH.getSelectionModel().select(DeviceListBase.this.lastFoundDeviceIndex);
                        }
                    } else if (DeviceListBase.this.devicesList.size() > 0 && DeviceListBase.this.selectedDevice != null && ((TIDeviceHolder)DeviceListBase.this.devicesList.get(0)).getTIDevice() == DeviceListBase.this.selectedDevice) {
                        DeviceListBase.this.listHH.getSelectionModel().select(0);
                    }
                    if (!tIDevice.isOSpresent()) {
                        DeviceListBase.this.notifyDeviceWithoutOSDisonnected(tIDevice);
                    }
                    DeviceListBase.this.notifyDisconnectedHandhelds(DeviceListBase.this.devicesList.size());
                    DeviceListBase.this.refreshConnectedHandheldCount();
                }
            };
            if (!this.suppressAnimations()) {
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds((double)1.0), tIDeviceHolder.getRootNode());
                if (tIDeviceHolder.isFadeAnimationPending()) {
                    fadeTransition.setFromValue(1.0);
                } else {
                    fadeTransition.setFromValue(0.0);
                }
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
                fadeTransition.setOnFinished((EventHandler)eventHandler);
            } else {
                eventHandler.handle(null);
            }
        }
    }

    protected boolean suppressAnimations() {
        return false;
    }

    public List<TIDevice> getConnectedDevices() {
        ArrayList<TIDevice> arrayList = new ArrayList<TIDevice>();
        for (TIDeviceHolder tIDeviceHolder : this.devicesList) {
            arrayList.add(tIDeviceHolder.getTIDevice());
        }
        return arrayList;
    }

    public TIDeviceHolder findDeviceHolder(TIDevice tIDevice) {
        int n = 0;
        for (TIDeviceHolder tIDeviceHolder : this.devicesList) {
            if (tIDeviceHolder.getTIDevice() == tIDevice) {
                this.lastFoundDeviceIndex = n;
                return tIDeviceHolder;
            }
            ++n;
        }
        this.lastFoundDeviceIndex = -1;
        return null;
    }

    public TIDevice getSelectedDevice() {
        return this.selectedDevice;
    }

    public ObservableSet<TIDevice> getBusyDeviceSet() {
        return this.busyDeviceSet;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int n) {
        this.mode = n;
        if (n == 1) {
            this.paneAllConnected.setRight((Node)this.hBoxHHScreenCaptureAllConnected);
            this.deviceListPane.getStyleClass().add((Object)"handheldListScreenCapture");
        } else {
            this.listHH.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            this.paneAllConnected.setRight(null);
            this.deviceListPane.getStyleClass().removeAll((Object[])new String[]{"handheldListScreenCapture"});
        }
        if (this.listHH.getItems().size() > 0) {
            for (TIDeviceHolder tIDeviceHolder : this.listHH.getItems()) {
                boolean bl = n == 1;
                tIDeviceHolder.setScreenCaptureEnabled(bl && tIDeviceHolder.getTIDevice().isOSpresent() && !tIDeviceHolder.isSendOSTransferInProgress());
                if (!tIDeviceHolder.cancelButtonEnabledProperty().getValue().booleanValue()) continue;
                tIDeviceHolder.setCancelButtonEnabled(false);
                tIDeviceHolder.setCancelButtonEnabled(true);
            }
        }
    }

    private void initHBoxHHScreenCaptureAllConnected() {
        this.hBoxHHScreenCaptureAllConnected = new HBox();
        this.butnHHScreenCaptureAllConnected = new Button();
        this.butnHHScreenCaptureAllConnected.disableProperty().set(true);
        this.butnHHScreenCaptureAllConnected.getStyleClass().add((Object)"cameraAllScreenCaptureButton");
        Tooltip tooltip = new Tooltip(CommonUISupportResourceBundle.BUNDLE.getString("butnHHScreenCaptureAllConnected.tooltip"));
        this.setScreenCaptureStyle(this.butnHHScreenCaptureAllConnected, this.hBoxHHScreenCaptureAllConnected, tooltip);
        this.butnHHScreenCaptureAllConnected.setId("butnHHScreenCaptureAllConnected");
        this.butnHHScreenCaptureAllConnected.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                DeviceListBase.this.notifyScreenCapture();
            }
        });
    }

    private void setScreenCaptureStyle(Button button, HBox hBox, Tooltip tooltip) {
        ImageView imageView = new ImageView(new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/hh bar separator line.png").toExternalForm()));
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setTooltip(tooltip);
        hBox.getChildren().addAll((Object[])new Node[]{imageView, button});
        HBox.setMargin((Node)button, (Insets)new Insets(0.0, 0.0, 1.0, 0.0));
        HBox.setMargin((Node)imageView, (Insets)new Insets(5.0, 0.0, 5.0, 0.0));
        hBox.setAlignment(Pos.CENTER);
    }

    class DeviceListCell
    extends ListCell<TIDeviceHolder> {
        private CustomBackgroundProgressBar progressBar;
        @FXML
        Label lblHHName;
        @FXML
        Label lblHHId;
        @FXML
        Label lblHHOS;
        @FXML
        BorderPane deviceListRowContentPane;
        @FXML
        Pane progressBarPanel;
        @FXML
        Button handheldButton;
        @FXML
        Pane paneHandheldButton;
        @FXML
        VBox handheldBox;
        private HBox hBoxHHScreenCapture;
        private Button butnHHScreenCapture;
        private Button cancelTransactionBtn;
        private Label errorIcon;
        private ResourceBundle bundle;
        private String OSVersion;
        private String defaultLabelStyle;
        private Node rootNode;
        private FadeTransition fadeTransition;

        public DeviceListCell(ResourceBundle resourceBundle) {
            this.bundle = resourceBundle;
        }

        protected void updateItem(final TIDeviceHolder tIDeviceHolder, boolean bl) {
            super.updateItem((Object)tIDeviceHolder, bl);
            if (tIDeviceHolder != null) {
                this.setText("");
                try {
                    Object object;
                    if (this.rootNode == null) {
                        object = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/deviceList/deviceListRow.fxml"));
                        object.setResources(this.bundle);
                        object.setController((Object)this);
                        this.rootNode = (Node)object.load();
                    }
                    this.progressBar = new CustomBackgroundProgressBar(this.progressBarPanel);
                    this.handheldButton.getStyleClass().add((Object)"handheldbutton");
                    this.initHBoxHHScreenCaptureConnected(tIDeviceHolder);
                    if (DeviceListBase.this.mode == 1) {
                        if (tIDeviceHolder.getTIDevice().isOSpresent()) {
                            tIDeviceHolder.setScreenCaptureEnabled(!tIDeviceHolder.isSendOSTransferInProgress());
                            if (!tIDeviceHolder.isSendOSTransferInProgress()) {
                                this.deviceListRowContentPane.setRight((Node)this.hBoxHHScreenCapture);
                            }
                        } else {
                            tIDeviceHolder.setScreenCaptureEnabled(false);
                        }
                    }
                    if (tIDeviceHolder.getTIDevice().isOSpresent()) {
                        this.lblHHName.setId("lblHHName" + tIDeviceHolder.getTIDevice().getConnectionIDforName());
                        this.lblHHId.setId("lblHHId" + tIDeviceHolder.getTIDevice().getConnectionIDforName());
                        if (this.butnHHScreenCapture != null) {
                            this.butnHHScreenCapture.setId("butnHHScreenCapture" + tIDeviceHolder.getTIDevice().getConnectionIDforName());
                        }
                        this.lblHHOS.setId("lblHHOS" + tIDeviceHolder.getTIDevice().getConnectionIDforName());
                        this.lblHHName.setText(tIDeviceHolder.getDeviceName());
                        if (tIDeviceHolder.getTIDevice().getConnectionIDforName().length() != 0) {
                            this.lblHHId.setText(" - " + tIDeviceHolder.getTIDevice().getConnectionIDforName());
                        } else {
                            this.lblHHId.setText("");
                        }
                        this.OSVersion = "OS " + tIDeviceHolder.getTIDevice().getOsVersionUpperByte() + "." + tIDeviceHolder.getTIDevice().getOsVersionLowerByte();
                    } else {
                        this.handheldButton.getStyleClass().add((Object)"noOShandheldbutton");
                        this.lblHHName.setText(tIDeviceHolder.getDeviceName());
                        this.lblHHId.setText("");
                        this.OSVersion = "";
                    }
                    this.defaultLabelStyle = this.lblHHOS.getStyle();
                    this.setOSVersion();
                    object = tIDeviceHolder.transactionTypeProperty().getValue();
                    if (object != null) {
                        this.setTransactionType((String)object);
                        if (tIDeviceHolder.isSendOSTransferInProgress()) {
                            tIDeviceHolder.setScreenCaptureEnabled(false);
                        }
                        if (tIDeviceHolder.cancelButtonEnabledProperty().getValue().booleanValue()) {
                            this.deviceListRowContentPane.setRight((Node)this.getCancelButton());
                        }
                    }
                    ((TIDeviceHolder)this.getItem()).transactionProgressProperty().addListener((ChangeListener)new ChangeListener<Number>(){

                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                            DeviceListCell.this.setTransactionProgress(number2.floatValue());
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).progressBarEnabledProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                            DeviceListCell.this.setTransactionComplete();
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).transactionTypeProperty().addListener((ChangeListener)new ChangeListener<String>(){

                        public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                            DeviceListCell.this.setTransactionType(string2);
                            if (tIDeviceHolder.isSendOSTransferInProgress()) {
                                tIDeviceHolder.setScreenCaptureEnabled(false);
                            }
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).idAvailableProperty().addListener((ChangeListener)new ChangeListener<String>(){

                        public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                            DeviceListCell.this.setLabelInHHID(string2);
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).screenCaptureEnabledProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                            if (bl2.booleanValue()) {
                                DeviceListCell.this.deviceListRowContentPane.setRight((Node)DeviceListCell.this.hBoxHHScreenCapture);
                            } else {
                                DeviceListCell.this.deviceListRowContentPane.setRight(null);
                            }
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).dragOverAcceptedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                            if (bl2.booleanValue()) {
                                if (DeviceListCell.this.handheldBox.getStyleClass().contains((Object)"dragOverNone")) {
                                    DeviceListCell.this.handheldBox.getStyleClass().remove((Object)"dragOverNone");
                                    DeviceListCell.this.paneHandheldButton.getStyleClass().remove((Object)"dragOverNone");
                                }
                                DeviceListCell.this.handheldBox.getStyleClass().add((Object)"dragOverHandheldBox");
                                DeviceListCell.this.paneHandheldButton.getStyleClass().add((Object)"dragOverPaneHandheldButton");
                            } else {
                                if (DeviceListCell.this.handheldBox.getStyleClass().contains((Object)"dragOverHandheldBox")) {
                                    DeviceListCell.this.handheldBox.getStyleClass().remove((Object)"dragOverHandheldBox");
                                    DeviceListCell.this.paneHandheldButton.getStyleClass().remove((Object)"dragOverPaneHandheldButton");
                                }
                                DeviceListCell.this.handheldBox.getStyleClass().add((Object)"dragOverNone");
                                DeviceListCell.this.paneHandheldButton.getStyleClass().add((Object)"dragOverNone");
                            }
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).distressDeviceProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                            if (!bl2.booleanValue() && DeviceListCell.this.handheldButton.getStyleClass().contains((Object)"noOShandheldbutton")) {
                                DeviceListCell.this.handheldButton.getStyleClass().remove((Object)"noOShandheldbutton");
                            }
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).cancelButtonEnabledProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                            if (bl2.booleanValue()) {
                                DeviceListCell.this.deviceListRowContentPane.setRight((Node)DeviceListCell.this.getCancelButton());
                            } else {
                                DeviceListCell.this.deviceListRowContentPane.setRight(null);
                            }
                        }
                    });
                    ((TIDeviceHolder)this.getItem()).isErrorReportedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                            if (bl2.booleanValue()) {
                                DeviceListCell.this.deviceListRowContentPane.setRight((Node)DeviceListCell.this.getErrorIcon());
                                DeviceListCell.this.progressBar.setProgressBarColor("#B2B4B7");
                                DeviceListCell.this.progressBar.setPercentage(DeviceListCell.this.progressBar.getPercentage());
                                DeviceListCell.this.setLabelInTransaction(CommonUISupportResourceBundle.BUNDLE.getString("label.osversion.transferError"), true);
                            } else {
                                DeviceListCell.this.deviceListRowContentPane.setRight(null);
                                DeviceListCell.this.resetUI();
                            }
                        }
                    });
                    this.setGraphic(this.rootNode);
                    tIDeviceHolder.setRootNode(this.rootNode);
                    if (tIDeviceHolder.isFadeInAnimationPending()) {
                        this.fadeTransition = new FadeTransition(Duration.millis((double)500.0), this.rootNode);
                        this.fadeTransition.setFromValue(1.0);
                        this.fadeTransition.setToValue(0.2);
                        this.fadeTransition.setCycleCount(4);
                        this.fadeTransition.setAutoReverse(true);
                        this.fadeTransition.play();
                    }
                }
                catch (IOException var3_4) {
                    TILogger.logError("DeviceListBase", "updateItem", var3_4);
                    this.setGraphic(null);
                }
            }
        }

        private void initHBoxHHScreenCaptureConnected(final TIDeviceHolder tIDeviceHolder) {
            this.hBoxHHScreenCapture = new HBox();
            this.butnHHScreenCapture = new Button();
            this.butnHHScreenCapture.getStyleClass().add((Object)"cameraButton");
            Tooltip tooltip = new Tooltip(CommonUISupportResourceBundle.BUNDLE.getString("butnHHScreenCapture.tooltip"));
            DeviceListBase.this.setScreenCaptureStyle(this.butnHHScreenCapture, this.hBoxHHScreenCapture, tooltip);
            this.butnHHScreenCapture.setId("butnHHScreenCapture");
            this.butnHHScreenCapture.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    DeviceListBase.this.notifyScreenCapture(tIDeviceHolder.getTIDevice());
                }
            });
        }

        private void resetUI() {
            if (this.getItem() != null) {
                if (((TIDeviceHolder)this.getItem()).cancelButtonEnabledProperty().getValue().booleanValue()) {
                    ((TIDeviceHolder)this.getItem()).setCancelButtonEnabled(false);
                    ((TIDeviceHolder)this.getItem()).setCancelButtonEnabled(true);
                }
                this.setTransactionType(((TIDeviceHolder)this.getItem()).transactionTypeProperty().getValue());
            }
        }

        private void createErrorButton() {
            this.errorIcon = new Label();
            this.errorIcon.getStyleClass().add((Object)"calc-error");
        }

        private HBox getErrorIcon() {
            this.createErrorButton();
            HBox hBox = new HBox();
            hBox.getChildren().addAll((Object[])new Node[]{this.errorIcon});
            HBox.setMargin((Node)this.errorIcon, (Insets)new Insets(0.0, 10.0, 0.0, 10.0));
            hBox.setAlignment(Pos.CENTER);
            return hBox;
        }

        private void createCancelButton() {
            this.cancelTransactionBtn = new Button();
            this.cancelTransactionBtn.getStyleClass().add((Object)"stopxfer-button");
            this.cancelTransactionBtn.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    DeviceListCell.this.cancelTransaction();
                }
            });
        }

        private HBox getCancelButton() {
            this.createCancelButton();
            HBox hBox = new HBox();
            hBox.getChildren().addAll((Object[])new Node[]{this.cancelTransactionBtn});
            HBox.setMargin((Node)this.cancelTransactionBtn, (Insets)new Insets(1.0, -6.0, 1.0, 1.0));
            hBox.setAlignment(Pos.CENTER);
            return hBox;
        }

        private void cancelTransaction() {
            ((TIDeviceHolder)this.getItem()).setTransactionCanceled(true);
        }

        private void setOSVersion() {
            this.setLabelInTransaction(this.OSVersion, false);
        }

        private void setLabelInTransaction(String string, boolean bl) {
            if (bl) {
                this.lblHHOS.setStyle(this.defaultLabelStyle + "-fx-text-fill: #ff0000;-fx-font-weight: bold;");
            } else {
                this.lblHHOS.setStyle(this.defaultLabelStyle);
            }
            this.lblHHOS.setText(string);
        }

        private void setLabelInHHID(String string) {
            this.lblHHId.setText(" - " + string);
            this.lblHHName.setId("lblHHName" + string);
            this.lblHHId.setId("lblHHId" + string);
            if (this.butnHHScreenCapture != null) {
                this.butnHHScreenCapture.setId("butnHHScreenCapture" + string);
            }
            this.lblHHOS.setId("lblHHOS" + string);
        }

        private void setTransactionProgress(float f) {
            this.progressBar.setPercentage(f);
        }

        private void setTransactionComplete() {
            this.progressBar.done();
            if (this.getItem() != null && ((TIDeviceHolder)this.getItem()).progressBarEnabledProperty().getValue().booleanValue()) {
                ((TIDeviceHolder)this.getItem()).setProgressBarEnabled(false);
                ((TIDeviceHolder)this.getItem()).setTransactionType(TransactionType.NONE);
            }
        }

        private void setTransactionType(String string) {
            if (string == null) {
                this.setOSVersion();
            } else {
                switch (TransactionType.valueOf(string)) {
                    case SENDING: {
                        this.progressBar.setProgressBarColor("#0092C8");
                        this.setLabelInTransaction(CommonUISupportResourceBundle.BUNDLE.getString("label.osversion.sending"), false);
                        break;
                    }
                    case RECEIVING: {
                        this.progressBar.setProgressBarColor("#8DC63F");
                        this.setLabelInTransaction(CommonUISupportResourceBundle.BUNDLE.getString("label.osversion.receiving"), false);
                        break;
                    }
                    case DELETING: {
                        this.progressBar.setProgressBarColor("#8DC63F");
                        this.setLabelInTransaction(CommonUISupportResourceBundle.BUNDLE.getString("label.osversion.deleting"), false);
                        break;
                    }
                    case SEND_OS: {
                        this.progressBar.setProgressBarColor("#0092C8");
                        this.setLabelInTransaction(CommonUISupportResourceBundle.BUNDLE.getString("label.osversion.sendingOS"), false);
                        break;
                    }
                    case CAPTURING_SCREEN: {
                        this.progressBar.setProgressBarColor("#0092C8");
                        this.setLabelInTransaction(CommonUISupportResourceBundle.BUNDLE.getString("label.osversion.capturingScreen"), false);
                        break;
                    }
                    case NONE: {
                        this.setOSVersion();
                        break;
                    }
                    default: {
                        this.setOSVersion();
                    }
                }
            }
        }

    }

}

