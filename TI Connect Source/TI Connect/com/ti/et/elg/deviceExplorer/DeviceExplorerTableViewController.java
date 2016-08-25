/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.FXCollections
 *  javafx.collections.ListChangeListener
 *  javafx.collections.ListChangeListener$Change
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Node
 *  javafx.scene.control.ContextMenu
 *  javafx.scene.control.Label
 *  javafx.scene.control.MenuItem
 *  javafx.scene.control.SelectionMode
 *  javafx.scene.control.SeparatorMenuItem
 *  javafx.scene.control.TableCell
 *  javafx.scene.control.TableColumn
 *  javafx.scene.control.TableColumn$SortType
 *  javafx.scene.control.TableView
 *  javafx.scene.control.TableView$TableViewSelectionModel
 *  javafx.scene.control.cell.PropertyValueFactory
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.input.ClipboardContent
 *  javafx.scene.input.ContextMenuEvent
 *  javafx.scene.input.DragEvent
 *  javafx.scene.input.Dragboard
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.input.TransferMode
 *  javafx.scene.layout.HBox
 *  javafx.scene.text.Font
 *  javafx.scene.text.FontWeight
 *  javafx.scene.text.Text
 *  javafx.util.Callback
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.DeviceExplorerResourceBundle;
import com.ti.et.elg.deviceExplorer.NameTIVar;
import com.ti.et.elg.deviceExplorer.SizeTIVar;
import com.ti.et.elg.deviceExplorer.TableViewData;
import com.ti.et.elg.deviceExplorer.TransactionProgressTableViewController;
import com.ti.et.elg.deviceExplorer.actions.DeleteVarsAction;
import com.ti.et.elg.deviceExplorer.actions.DeviceExplorerActionManager;
import com.ti.et.elg.deviceExplorer.exports.DeviceContentsInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.exports.DeviceSelectionListener;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.deviceExplorer.transaction.DeleteVarsTransaction;
import com.ti.et.elg.deviceExplorer.transaction.GetVarsTransaction;
import com.ti.et.elg.deviceExplorer.transaction.MultiStepGetVarTransaction;
import com.ti.et.elg.deviceExplorer.transaction.OpenFromDeviceTransaction;
import com.ti.et.elg.deviceExplorer.transaction.TIDeviceDirectoryRefreshTransaction;
import com.ti.et.elg.nativeDnD.DnD;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import com.ti.et.elg.transactionManager.TransactionProgressData;
import com.ti.et.elg.transactionManager.TransactionQueueListener;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class DeviceExplorerTableViewController
implements DeviceContentsInterface,
DeviceSelectionListener {
    private static final String NAME_PROPERTY = "name";
    private static final String TYPE_PROPERTY = "type";
    private static final String SIZE_PROPERTY = "size";
    private static final String LOCATION_PROPERTY = "location";
    @FXML
    TableView<TableViewData> tableContent;
    @FXML
    Label lblDeviceInfo;
    @FXML
    Label lblArchiveInfo;
    OverlayStackPaneBase stackPaneController;
    private Node rootNode = null;
    private TransactionProgressTableViewController transactionProgressTableViewController = null;
    private EventHandler<Event> sendScreenCaptureEventHandler = null;
    private TIDevice selectedDevice = null;
    private ObservableList<TableViewData> data = FXCollections.observableArrayList();
    private TIVar[] currentlyDraggingVars = null;
    private ObservableList<Integer> selectedIndices = null;
    int[] lastSelectIndices = null;
    int[] lastSelectIndicesTable = null;
    ObservableList<TableViewData> selectedRows = null;
    int rowSelected;
    boolean isClean;
    private MultiStepGetVarTransaction multiStepGetVarTransaction = null;
    private TIMenuItem popupmenuRefresh;
    private TIMenuItem popupmenuDelete;
    private TIMenuItem popupmenuSelectAll;
    private TIMenuItem popupmenuSendToHandhelds;
    private TIMenuItem popupmenuSendToComputer;
    private ContextMenu contextMenu;
    private boolean alreadyInOnDragDetected = false;
    private boolean isCellBeingDragged;
    private boolean sortCleared = false;
    private ChangeListener<Object> cl;

    @Override
    public void init(DeviceExplorerInterface deviceExplorerInterface) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/deviceExplorer/deviceExplorerDeviceContents.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
            this.initTableView();
            this.transactionProgressTableViewController = new TransactionProgressTableViewController(TransactionManager.getInstance());
            this.transactionProgressTableViewController.initTransactionProgressTableView(null);
            this.createContextMenu();
            this.clearLblDeviceAndArchive();
            this.rootNode.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>(){

                public void handle(ContextMenuEvent contextMenuEvent) {
                    DeviceExplorerTableViewController.this.contextMenu.show(DeviceExplorerTableViewController.this.rootNode, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                }
            });
            if (PlatformManager.isMac()) {
                this.rootNode.addEventFilter(MouseEvent.MOUSE_CLICKED, (EventHandler)new EventHandler<MouseEvent>(){

                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.isControlDown()) {
                            DeviceExplorerTableViewController.this.contextMenu.show(DeviceExplorerTableViewController.this.rootNode, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        }
                    }
                });
                this.rootNode.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                            DeleteVarsAction.getInstance().getEventHandler().handle(null);
                        }
                    }
                });
            }
        }
        catch (IOException var2_3) {
            TILogger.logError("DeviceExplorerTableViewController", "init", var2_3);
        }
    }

    private void createContextMenu() {
        if (this.contextMenu == null) {
            this.contextMenu = new ContextMenu();
            this.contextMenu.setId("ctxMenuContentTable");
            this.contextMenu.consumeAutoHidingEventsProperty().set(false);
            this.contextMenu.impl_showRelativeToWindowProperty().set(true);
            this.popupmenuRefresh = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.refresh"));
            this.popupmenuRefresh.setId("popupmenuRefresh");
            this.popupmenuDelete = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.delete"));
            this.popupmenuDelete.setId("popupmenuDelete");
            this.popupmenuSelectAll = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.selectAll"));
            this.popupmenuSelectAll.setId("popupmenuSelectAll");
            if (DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_CONNECT) {
                this.popupmenuSendToHandhelds = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.sendToHandheld"));
                this.popupmenuSendToHandhelds.setId("popupmenuSendToCalculators");
                this.contextMenu.getItems().add((Object)this.popupmenuSendToHandhelds.getMenuItem());
            }
            this.popupmenuSendToComputer = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.sendToComputer"));
            this.popupmenuSendToComputer.setId("popupmenuSendToComputer");
            this.contextMenu.getItems().addAll((Object[])new MenuItem[]{this.popupmenuSendToComputer.getMenuItem(), new SeparatorMenuItem(), this.popupmenuRefresh.getMenuItem(), new SeparatorMenuItem(), this.popupmenuDelete.getMenuItem(), this.popupmenuSelectAll.getMenuItem()});
        }
    }

    protected static void setNameFont(Label label, boolean bl) {
        FontWeight fontWeight;
        String string = label.getText();
        FontWeight fontWeight2 = fontWeight = bl ? FontWeight.BOLD : FontWeight.NORMAL;
        if (string.contains("\uf038")) {
            label.setFont(Font.font((String)"TI Uni", (FontWeight)fontWeight, (double)13.0));
        } else {
            label.setFont(Font.font((String)"OpenSans-Regular", (FontWeight)fontWeight, (double)13.0));
        }
    }

    protected TableColumn<TableViewData, NameTIVar> setupNameColumn() {
        TIResourceBundle tIResourceBundle = CommonUISupportResourceBundle.BUNDLE;
        TableColumn tableColumn = new TableColumn(tIResourceBundle.getString("table.header.name"));
        tableColumn.setMinWidth(110.0);
        tableColumn.setPrefWidth(150.0);
        tableColumn.setCellValueFactory((Callback)new PropertyValueFactory("name"));
        tableColumn.setComparator((Comparator)new Comparator<NameTIVar>(){

            @Override
            public int compare(NameTIVar nameTIVar, NameTIVar nameTIVar2) {
                String string = nameTIVar.getName();
                String string2 = nameTIVar2.getName();
                return Collator.getInstance().compare(string, string2);
            }
        });
        tableColumn.setCellFactory((Callback)new Callback<TableColumn<TableViewData, NameTIVar>, TableCell<TableViewData, NameTIVar>>(){

            public TableCell<TableViewData, NameTIVar> call(TableColumn<TableViewData, NameTIVar> tableColumn) {
                TableCell<TableViewData, NameTIVar> tableCell = new TableCell<TableViewData, NameTIVar>(){

                    public void updateItem(final NameTIVar nameTIVar, boolean bl) {
                        super.updateItem((Object)nameTIVar, bl);
                        if (nameTIVar != null) {
                            HBox hBox = new HBox();
                            hBox.setPadding(new Insets(0.0, 0.0, 0.0, 14.0));
                            hBox.setSpacing(10.0);
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            final Label label = new Label(nameTIVar.getName());
                            DeviceExplorerTableViewController.setNameFont(label, nameTIVar.getIsSelected());
                            final ImageView imageView = new ImageView();
                            imageView.setImage(new Image(this.getClass().getResource(nameTIVar.getIconFile()).toExternalForm()));
                            hBox.getChildren().addAll((Object[])new Node[]{imageView, label});
                            this.setGraphic((Node)hBox);
                            ((NameTIVar)this.getItem()).getIsSelectedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                                    imageView.setImage(new Image(this.getClass().getResource(nameTIVar.getIconFile()).toExternalForm()));
                                    DeviceExplorerTableViewController.setNameFont(label, bl2);
                                }
                            });
                        }
                    }

                };
                return tableCell;
            }

        });
        tableColumn.setId("name");
        return tableColumn;
    }

    protected TableColumn<TableViewData, String> setupTypeColumn() {
        TIResourceBundle tIResourceBundle = CommonUISupportResourceBundle.BUNDLE;
        TableColumn tableColumn = new TableColumn(tIResourceBundle.getString("table.header.type"));
        tableColumn.setMinWidth(125.0);
        tableColumn.setPrefWidth(175.0);
        tableColumn.setCellValueFactory((Callback)new PropertyValueFactory("type"));
        return tableColumn;
    }

    protected TableColumn<TableViewData, SizeTIVar> setupSizeColumn() {
        TIResourceBundle tIResourceBundle = CommonUISupportResourceBundle.BUNDLE;
        TableColumn tableColumn = new TableColumn(tIResourceBundle.getString("table.header.size"));
        tableColumn.setMinWidth(60.0);
        tableColumn.setPrefWidth(100.0);
        tableColumn.setCellValueFactory((Callback)new PropertyValueFactory("size"));
        tableColumn.setComparator((Comparator)new Comparator<SizeTIVar>(){

            @Override
            public int compare(SizeTIVar sizeTIVar, SizeTIVar sizeTIVar2) {
                return sizeTIVar.getSize().compareTo(sizeTIVar2.getSize());
            }
        });
        tableColumn.setCellFactory((Callback)new Callback<TableColumn<TableViewData, SizeTIVar>, TableCell<TableViewData, SizeTIVar>>(){

            public TableCell<TableViewData, SizeTIVar> call(TableColumn<TableViewData, SizeTIVar> tableColumn) {
                TableCell<TableViewData, SizeTIVar> tableCell = new TableCell<TableViewData, SizeTIVar>(){

                    public void updateItem(SizeTIVar sizeTIVar, boolean bl) {
                        super.updateItem((Object)sizeTIVar, bl);
                        if (sizeTIVar != null) {
                            this.setText(sizeTIVar.getSizeFormat());
                        }
                    }
                };
                return tableCell;
            }

        });
        return tableColumn;
    }

    protected TableColumn<TableViewData, String> setupLocationColumn() {
        TIResourceBundle tIResourceBundle = CommonUISupportResourceBundle.BUNDLE;
        TableColumn tableColumn = new TableColumn(tIResourceBundle.getString("table.header.location"));
        tableColumn.setMinWidth(120.0);
        tableColumn.setPrefWidth(175.0);
        tableColumn.setCellValueFactory((Callback)new PropertyValueFactory("location"));
        return tableColumn;
    }

    protected void setupDnDHandlers() {
        DnD.getInstance().initialize(DeviceExplorerFactory.getContentTable());
        this.tableContent.setOnDragDropped((EventHandler)new EventHandler<DragEvent>(){

            public void handle(final DragEvent dragEvent) {
                Dragboard dragboard = dragEvent.getDragboard();
                boolean bl = false;
                if (dragboard.hasFiles()) {
                    TIDevice tIDevice = DeviceExplorerTableViewController.this.selectedDevice;
                    List list = dragboard.getFiles();
                    if (tIDevice != null && list != null && !list.isEmpty()) {
                        ArrayList<TIDevice> arrayList = new ArrayList<TIDevice>();
                        arrayList.add(tIDevice);
                        DeviceExplorerFactory.getDeviceExplorer().commonSendFilesToTIDevicesDropHandler(list, arrayList);
                        bl = true;
                    }
                } else if (dragboard.hasImage() || dragboard.hasHtml()) {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            DeviceExplorerTableViewController.this.sendScreenCaptureEventHandler.handle((Event)dragEvent);
                        }
                    });
                }
                dragEvent.setDropCompleted(bl);
                dragEvent.consume();
            }

        });
        this.tableContent.setOnDragOver((EventHandler)new EventHandler<DragEvent>(){

            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource() != DeviceExplorerTableViewController.this.tableContent && (dragEvent.getDragboard().hasFiles() || dragEvent.getDragboard().hasImage() || dragEvent.getDragboard().hasHtml()) && DeviceExplorerTableViewController.this.selectedDevice != null && !PromptDialog.isModalDialogActive()) {
                    dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.COPY});
                }
                dragEvent.consume();
            }
        });
        this.tableContent.setOnDragDetected((EventHandler)new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent) {
                if (!DeviceExplorerTableViewController.this.alreadyInOnDragDetected && DeviceExplorerTableViewController.this.isCellBeingDragged) {
                    DeviceExplorerTableViewController.this.alreadyInOnDragDetected = true;
                    List list = DeviceExplorerTableViewController.this.getSelectedItemsFromTableView();
                    if (list != null && list.size() > 0) {
                        TIVar[] arrtIVar = new TIVar[list.size()];
                        int n = 0;
                        for (TableViewData tableViewData2 : list) {
                            TIVar tIVar;
                            arrtIVar[n] = tIVar = tableViewData2.getTiVar();
                            ++n;
                        }
                        DeviceExplorerTableViewController.this.currentlyDraggingVars = arrtIVar;
                        if (!PlatformManager.isWindows()) {
                            TableViewData tableViewData2;
                            Dragboard dragboard = DeviceExplorerTableViewController.this.tableContent.startDragAndDrop(new TransferMode[]{TransferMode.COPY});
                            tableViewData2 = new ClipboardContent();
                            tableViewData2.putString("TIVars");
                            dragboard.setContent((Map)((Object)tableViewData2));
                        }
                        DnD.getInstance().beginDrag(arrtIVar);
                        if (PlatformManager.isWindows()) {
                            DeviceExplorerTableViewController.this.currentlyDraggingVars = null;
                            if (DeviceExplorerTableViewController.this.multiStepGetVarTransaction != null) {
                                DeviceExplorerTableViewController.this.multiStepGetVarTransaction.makeDone();
                                DeviceExplorerTableViewController.this.multiStepGetVarTransaction = null;
                            }
                        }
                    }
                    DeviceExplorerTableViewController.this.alreadyInOnDragDetected = false;
                }
                DeviceExplorerTableViewController.this.isCellBeingDragged = false;
                mouseEvent.consume();
            }
        });
    }

    protected void setupSortListener() {
        this.cl = new ChangeListener<Object>(){

            public void changed(ObservableValue<?> observableValue, Object object, Object object2) {
                DeviceExplorerTableViewController.this.updateSortProperty();
            }
        };
        this.tableContent.getSortOrder().addListener(new ListChangeListener<TableColumn<TableViewData, ?>>(){

            public void onChanged(ListChangeListener.Change<? extends TableColumn<TableViewData, ?>> change) {
                change.next();
                for (TableColumn tableColumn2 : change.getRemoved()) {
                    tableColumn2.sortTypeProperty().removeListener(DeviceExplorerTableViewController.this.cl);
                }
                for (TableColumn tableColumn2 : change.getAddedSubList()) {
                    tableColumn2.sortTypeProperty().addListener(DeviceExplorerTableViewController.this.cl);
                }
                if (!DeviceExplorerTableViewController.this.sortCleared) {
                    DeviceExplorerTableViewController.this.updateSortProperty();
                } else {
                    DeviceExplorerTableViewController.this.sortCleared = false;
                }
            }
        });
    }

    protected void setupSelectionHandlers() {
        this.tableContent.setOnMouseClicked((EventHandler)new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent) {
                if (!DeviceExplorerTableViewController.this.isClean) {
                    DeviceExplorerTableViewController.this.tableContent.getSelectionModel().clearAndSelect(DeviceExplorerTableViewController.this.rowSelected);
                }
                DeviceExplorerTableViewController.this.isClean = true;
                DeviceExplorerTableViewController.this.isCellBeingDragged = false;
                if (mouseEvent.getClickCount() == 2 && DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_CONNECT) {
                    TILogger.logDetail(DeviceExplorerTableViewController.class.getSimpleName(), "Double clicked");
                    if (DeviceExplorerTableViewController.this.selectedRows != null && DeviceExplorerTableViewController.this.selectedRows.size() == 1) {
                        TableViewData tableViewData = (TableViewData)DeviceExplorerTableViewController.this.selectedRows.get(0);
                        TIVar tIVar = tableViewData.getTiVar();
                        OpenFromDeviceTransaction openFromDeviceTransaction = new OpenFromDeviceTransaction(DeviceExplorerTableViewController.this.selectedDevice, tIVar, TransactionManager.getInstance());
                        TransactionManager.getInstance().addTransaction(DeviceExplorerTableViewController.this.selectedDevice, openFromDeviceTransaction, null);
                    }
                }
            }
        });
        this.tableContent.setOnMousePressed((EventHandler)new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent) {
                boolean bl = true;
                DeviceExplorerTableViewController.this.isCellBeingDragged = true;
                DeviceExplorerTableViewController.this.rowSelected = DeviceExplorerTableViewController.this.tableContent.getSelectionModel().getSelectedIndex();
                if (!PlatformManager.isWindows() && mouseEvent.isControlDown() && mouseEvent.isPrimaryButtonDown()) {
                    if (DeviceExplorerTableViewController.this.lastSelectIndices != null) {
                        if (DeviceExplorerTableViewController.this.lastSelectIndices.length > DeviceExplorerTableViewController.this.selectedIndices.size()) {
                            for (int n : DeviceExplorerTableViewController.this.lastSelectIndices) {
                                if (DeviceExplorerTableViewController.this.tableContent.getSelectionModel().isSelected(n)) continue;
                                DeviceExplorerTableViewController.this.tableContent.getSelectionModel().select(n);
                                break;
                            }
                        } else {
                            DeviceExplorerTableViewController.this.tableContent.getSelectionModel().clearAndSelect(DeviceExplorerTableViewController.this.rowSelected);
                        }
                    } else {
                        DeviceExplorerTableViewController.this.tableContent.getSelectionModel().clearAndSelect(DeviceExplorerTableViewController.this.rowSelected);
                    }
                } else if (DeviceExplorerTableViewController.this.lastSelectIndices != null && DeviceExplorerTableViewController.this.lastSelectIndices.length > 1) {
                    for (int n2 : DeviceExplorerTableViewController.this.lastSelectIndices) {
                        if (DeviceExplorerTableViewController.this.rowSelected != n2) continue;
                        bl = false;
                        break;
                    }
                    if (!bl) {
                        DeviceExplorerTableViewController.this.isClean = false;
                        for (int n2 : DeviceExplorerTableViewController.this.lastSelectIndices) {
                            if (DeviceExplorerTableViewController.this.rowSelected == n2) continue;
                            DeviceExplorerTableViewController.this.tableContent.getSelectionModel().select(n2);
                        }
                    }
                }
                DeviceExplorerTableViewController.this.lastSelectIndices = null;
            }
        });
        this.tableContent.addEventFilter(MouseEvent.MOUSE_PRESSED, (EventHandler)new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent) {
                DeviceExplorerTableViewController.this.lastSelectIndices = null;
                DeviceExplorerTableViewController.this.isClean = true;
                if (DeviceExplorerTableViewController.this.tableContent.getSelectionModel().getSelectedItems().size() > 0 && !mouseEvent.isControlDown() && !mouseEvent.isShiftDown() && !mouseEvent.isMetaDown() && mouseEvent.isPrimaryButtonDown() || !PlatformManager.isWindows() && mouseEvent.isControlDown() && mouseEvent.isPrimaryButtonDown() && DeviceExplorerTableViewController.this.tableContent.getSelectionModel().getSelectedItems().size() > 1) {
                    int[] arrn = new int[DeviceExplorerTableViewController.this.tableContent.getSelectionModel().getSelectedItems().size()];
                    int n = 0;
                    Iterator iterator = DeviceExplorerTableViewController.this.selectedIndices.iterator();
                    while (iterator.hasNext()) {
                        int n2;
                        arrn[n] = n2 = ((Integer)iterator.next()).intValue();
                        ++n;
                    }
                    DeviceExplorerTableViewController.this.lastSelectIndices = arrn;
                }
            }
        });
        this.tableContent.getSelectionModel().getSelectedItems().addListener((ListChangeListener)new ListChangeListener<TableViewData>(){

            public void onChanged(ListChangeListener.Change<? extends TableViewData> change) {
                DeviceExplorerActionManager.getInstance().varsSelected(!DeviceExplorerTableViewController.this.tableContent.getSelectionModel().getSelectedItems().isEmpty());
                if (!DeviceExplorerTableViewController.this.tableContent.getSelectionModel().getSelectedItems().isEmpty()) {
                    if (DeviceExplorerTableViewController.this.lastSelectIndicesTable != null) {
                        for (int n : DeviceExplorerTableViewController.this.lastSelectIndicesTable) {
                            if (DeviceExplorerTableViewController.this.tableContent.getSelectionModel().isSelected(n) || DeviceExplorerTableViewController.this.tableContent.getItems().size() <= n) continue;
                            ((TableViewData)DeviceExplorerTableViewController.this.tableContent.getItems().get(n)).changeIconFile(false);
                        }
                    }
                    if (DeviceExplorerTableViewController.this.selectedRows != null && DeviceExplorerTableViewController.this.selectedRows.size() > 0) {
                        int[] arrn = new int[DeviceExplorerTableViewController.this.selectedRows.size()];
                        int n = 0;
                        for (TableViewData tableViewData : DeviceExplorerTableViewController.this.selectedRows) {
                            tableViewData.changeIconFile(true);
                            arrn[n] = (Integer)DeviceExplorerTableViewController.this.selectedIndices.get(n);
                            ++n;
                        }
                        DeviceExplorerTableViewController.this.lastSelectIndicesTable = arrn;
                    } else {
                        DeviceExplorerTableViewController.this.lastSelectIndicesTable = null;
                    }
                } else if (DeviceExplorerTableViewController.this.lastSelectIndicesTable != null) {
                    for (int n : DeviceExplorerTableViewController.this.lastSelectIndicesTable) {
                        if (DeviceExplorerTableViewController.this.tableContent.getSelectionModel().isSelected(n) || DeviceExplorerTableViewController.this.tableContent.getItems().size() <= n) continue;
                        ((TableViewData)DeviceExplorerTableViewController.this.tableContent.getItems().get(n)).changeIconFile(false);
                    }
                }
            }
        });
    }

    protected void setupTransactionQueueListeners() {
        TransactionManager.getInstance().addTransactionQueueListener(new TransactionQueueListener(){

            @Override
            public void transactionQueueEmpty(TIDevice tIDevice) {
                if (tIDevice == DeviceExplorerTableViewController.this.selectedDevice && DeviceExplorerTableViewController.this.selectedDevice != null) {
                    DeviceExplorerTableViewController.this.tableContent.disableProperty().setValue(Boolean.valueOf(false));
                }
            }

            @Override
            public void transactionQueueNonEmpty(TIDevice tIDevice) {
                if (tIDevice == DeviceExplorerTableViewController.this.selectedDevice && DeviceExplorerTableViewController.this.selectedDevice != null) {
                    DeviceExplorerTableViewController.this.tableContent.disableProperty().setValue(Boolean.valueOf(true));
                }
            }
        });
    }

    public void initTableView() {
        final TableColumn<TableViewData, NameTIVar> tableColumn = this.setupNameColumn();
        final TableColumn<TableViewData, String> tableColumn2 = this.setupTypeColumn();
        final TableColumn<TableViewData, SizeTIVar> tableColumn3 = this.setupSizeColumn();
        final TableColumn<TableViewData, String> tableColumn4 = this.setupLocationColumn();
        this.setupDnDHandlers();
        this.setupSortListener();
        this.setupSelectionHandlers();
        this.setupTransactionQueueListeners();
        Text text = new Text(CommonUISupportResourceBundle.BUNDLE.getString("contentTable.emptymessage"));
        text.setStyle("-fx-text-alignment: center");
        this.tableContent.setPlaceholder((Node)text);
        this.tableContent.setItems(this.data);
        this.tableContent.getColumns().add(tableColumn);
        this.tableContent.getColumns().add(tableColumn2);
        this.tableContent.getColumns().add(tableColumn3);
        this.tableContent.getColumns().add(tableColumn4);
        this.tableContent.setPrefHeight(400.0);
        this.tableContent.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.selectedIndices = this.tableContent.getSelectionModel().getSelectedIndices();
        this.selectedRows = this.tableContent.getSelectionModel().getSelectedItems();
        DeviceExplorerFactory.getDeviceList().addDeviceSelectionListener(this);
        this.tableContent.getColumns().addListener((ListChangeListener)new ListChangeListener<Object>(){

            public void onChanged(ListChangeListener.Change<?> change) {
                change.next();
                if (change.wasReplaced()) {
                    DeviceExplorerTableViewController.this.tableContent.getColumns().clear();
                    DeviceExplorerTableViewController.this.tableContent.getColumns().add((Object)tableColumn);
                    DeviceExplorerTableViewController.this.tableContent.getColumns().add((Object)tableColumn2);
                    DeviceExplorerTableViewController.this.tableContent.getColumns().add((Object)tableColumn3);
                    DeviceExplorerTableViewController.this.tableContent.getColumns().add((Object)tableColumn4);
                }
            }
        });
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public Node getFocusableNode() {
        return this.tableContent;
    }

    @Override
    public void sendOSToSelectedDevice(File file) {
        if (file.isFile() && file.canRead()) {
            ArrayList<File> arrayList = new ArrayList<File>();
            arrayList.add(file);
            ArrayList<TIDevice> arrayList2 = new ArrayList<TIDevice>();
            arrayList2.add(this.selectedDevice);
            DeviceExplorerFactory.getDeviceExplorer().sendFilesToTIDevices(arrayList, arrayList2, null);
        }
    }

    @Override
    public void getSelectedFilesFromTIDevice(File file) {
        List<TableViewData> list = this.getSelectedItemsFromTableView();
        GetVarsTransaction getVarsTransaction = null;
        if (list != null && list.size() > 0 && file.isDirectory() && file.exists()) {
            getVarsTransaction = new GetVarsTransaction(list, this.selectedDevice, file, TransactionManager.getInstance());
            TransactionManager.getInstance().addTransaction(this.selectedDevice, getVarsTransaction, null);
        }
    }

    private List<TableViewData> getSelectedItemsFromTableView() {
        ObservableList observableList = this.tableContent.getSelectionModel().getSelectedItems();
        HashSet hashSet = new HashSet(observableList);
        return new ArrayList<TableViewData>(hashSet);
    }

    @Override
    public void getSelectedFileFromTIDevice(File file) {
        List<TableViewData> list = this.getSelectedItemsFromTableView();
        if (list == null || list.size() != 1) {
            return;
        }
        File file2 = new File(file.getParent());
        GetVarsTransaction getVarsTransaction = null;
        if (file2.isDirectory() && file2.exists()) {
            list.get(0).getTiVar().setHostFile(file);
            getVarsTransaction = new GetVarsTransaction(list, this.selectedDevice, file2, TransactionManager.getInstance());
            if (PlatformManager.isMac()) {
                getVarsTransaction.getListener().setConfirmOverride(3);
            }
            TransactionManager.getInstance().addTransaction(this.selectedDevice, getVarsTransaction, null);
        }
    }

    @Override
    public void deleteSelectedFilesFromTIDevice() {
        List<TableViewData> list = this.getSelectedItemsFromTableView();
        TIDevice tIDevice = this.selectedDevice;
        if (tIDevice != null && list != null && list.size() > 0 && PromptDialog.showTwoChoicePrompt(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Title"), "dlgDelete", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Message"), CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.DeleteButton"), "butnDelete") == 2) {
            DeleteVarsTransaction deleteVarsTransaction = new DeleteVarsTransaction(list, tIDevice, TransactionManager.getInstance());
            TransactionManager.getInstance().addTransaction(this.selectedDevice, deleteVarsTransaction, null);
        }
    }

    @Override
    public TIVar[] getCurrentlyDraggingVars() {
        return this.currentlyDraggingVars;
    }

    @Override
    public void writeSelectedDeviceFilesToURL(String string) {
        File file = new File(string);
        this.getSelectedFilesFromTIDevice(file);
    }

    @Override
    public byte[] getDataForFileName(String string) {
        if (this.currentlyDraggingVars != null && this.currentlyDraggingVars.length > 0) {
            TIVar tIVar;
            TIVar tIVar2 = null;
            Object object = this.currentlyDraggingVars;
            int n = object.length;
            for (int i = 0; i < n && !(tIVar2 = (tIVar = object[i])).getDeviceFileName().equals(string) && !tIVar2.getHostFileNameFromDeviceFileName().equals(string); ++i) {
                tIVar2 = null;
            }
            if (tIVar2 != null) {
                object = tIVar2.clone();
                if (this.multiStepGetVarTransaction == null) {
                    this.multiStepGetVarTransaction = new MultiStepGetVarTransaction(this.selectedDevice, this.currentlyDraggingVars.length);
                    this.multiStepGetVarTransaction.makeActive();
                    TransactionManager.getInstance().addTransaction(this.selectedDevice, this.multiStepGetVarTransaction, null);
                }
                if (!this.multiStepGetVarTransaction.getListener().hasBeenCanceled()) {
                    this.multiStepGetVarTransaction.setNextVarToGet((TIVar)object);
                    if (TransactionManager.getInstance().runTransactionStepNowAndWait(this.multiStepGetVarTransaction.getTIDevice(), this.multiStepGetVarTransaction) && !this.multiStepGetVarTransaction.getListener().getStatus().isFailure()) {
                        return object.frameDataWithDataFormat();
                    }
                }
            }
        }
        return null;
    }

    private void refreshLblDeviceAndArchiveInfo(TIDevice tIDevice) {
        String string;
        if (DeviceExplorerFactory.getDeviceExplorer().getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_CONNECT) {
            string = DeviceExplorerResourceBundle.BUNDLE.getString("content.pane.archiveInfo");
            string = MessageFormat.format(string, tIDevice.getFreeFLASH() / 1000, tIDevice.getFreeRAM() / 1000);
        } else {
            string = DeviceExplorerResourceBundle.BUNDLE.getString("content.pane.archiveInfo.SV");
            string = MessageFormat.format(string, tIDevice.getFreeRAM() / 1000);
        }
        this.lblDeviceInfo.setText(tIDevice.getDeviceName());
        this.lblArchiveInfo.setText(string);
    }

    private void clearLblDeviceAndArchive() {
        String string = DeviceExplorerResourceBundle.BUNDLE.getString("content.pane.archiveInfo");
        string = MessageFormat.format(string, "-", "-");
        this.lblDeviceInfo.setText("-");
        this.lblArchiveInfo.setText(string);
    }

    @Override
    public void updateDirectory(TIDevice tIDevice, TIDirectory tIDirectory) {
        if (tIDevice != null && tIDevice == this.selectedDevice) {
            int[] arrn;
            ArrayList arrayList = new ArrayList(this.tableContent.getSortOrder());
            ArrayList arrayList2 = new ArrayList(this.tableContent.getColumns());
            this.data.clear();
            this.lastSelectIndicesTable = null;
            Iterator<TIVar> iterator = tIDirectory.getIterator();
            while (iterator.hasNext()) {
                arrn = iterator.next();
                this.data.add((Object)new TableViewData(arrn.getDeviceFileName(), (TIVar)arrn));
            }
            this.sortCleared = true;
            this.tableContent.getSortOrder().clear();
            arrayList.clear();
            arrn = UserPropertyManagement.getIntArray("TableContent.lastSortOrder.values");
            int[] arrn2 = UserPropertyManagement.getIntArray("TableContent.lastSortOrderType.value");
            if (arrn.length == arrn2.length) {
                for (int i = 0; i < arrn.length && arrn[i] >= 0 && arrn[i] < 4; ++i) {
                    arrayList.add(arrayList2.get(arrn[i]));
                    TableColumn.SortType sortType = arrn2[i] == 0 ? TableColumn.SortType.ASCENDING : TableColumn.SortType.DESCENDING;
                    ((TableColumn)arrayList.get(i)).setSortType(sortType);
                }
            }
            this.tableContent.getSortOrder().addAll(arrayList);
            this.refreshLblDeviceAndArchiveInfo(tIDevice);
        }
    }

    public void refreshTIDeviceInTableView(TIDevice tIDevice) {
        if (tIDevice != null && tIDevice.isOSpresent() && !tIDevice.isUnderOSUpdate()) {
            TransactionManager.getInstance().addTransaction(tIDevice, new TIDeviceDirectoryRefreshTransaction(tIDevice), null);
        }
    }

    @Override
    public void deviceSelected(TIDevice tIDevice) {
        DeviceExplorerFactory.deviceSelected(this.selectedDevice, tIDevice);
        this.selectedDevice = tIDevice;
        this.tableContent.disableProperty().set(true);
        if (tIDevice == null) {
            this.data.clear();
            this.clearLblDeviceAndArchive();
        }
        if (tIDevice != null && tIDevice.isOSpresent() && DeviceExplorerFactory.getDeviceList().getMode() != 1) {
            this.refreshTIDeviceInTableView(tIDevice);
            this.refreshLblDeviceAndArchiveInfo(tIDevice);
        }
    }

    @Override
    public void refreshCurrentTIDevice() {
        if (this.selectedDevice != null) {
            this.refreshTIDeviceInTableView(this.selectedDevice);
        }
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
    public void showPane(Node node) {
        this.stackPaneController.showPane(node);
    }

    @Override
    public void setDeleteAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuDelete.setAction(tIAction);
        }
    }

    @Override
    public void setDeviceRefreshAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuRefresh.setAction(tIAction);
        }
    }

    @Override
    public void setSelectAllAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuSelectAll.setAction(tIAction);
        }
    }

    @Override
    public void setSendToHHAction(TIAction tIAction) {
        if (tIAction != null && this.popupmenuSendToHandhelds != null) {
            this.popupmenuSendToHandhelds.setAction(tIAction);
        }
    }

    @Override
    public void setSendToComputerAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuSendToComputer.setAction(tIAction);
        }
    }

    @Override
    public OverlayStackPaneBase getStackPane() {
        return this.stackPaneController;
    }

    @Override
    public void selectAll() {
        if (!this.data.isEmpty()) {
            this.tableContent.getSelectionModel().selectAll();
        }
    }

    @Override
    public void unselectAll() {
        this.tableContent.getSelectionModel().clearSelection();
    }

    @Override
    public TIVar[] getCurrentlySelectedVars() {
        List<TableViewData> list = this.getSelectedItemsFromTableView();
        TIVar[] arrtIVar = new TIVar[list.size()];
        int n = 0;
        for (TableViewData tableViewData : list) {
            TIVar tIVar;
            arrtIVar[n] = tIVar = tableViewData.getTiVar();
            ++n;
        }
        return arrtIVar;
    }

    private void updateSortProperty() {
        ArrayList arrayList = new ArrayList(this.tableContent.getSortOrder());
        ArrayList arrayList2 = new ArrayList(this.tableContent.getColumns());
        int[] arrn = new int[]{-1, -1, -1, -1};
        int[] arrn2 = new int[]{-1, -1, -1, -1};
        int n = 0;
        for (TableColumn tableColumn : arrayList) {
            arrn[n] = arrayList2.indexOf((Object)tableColumn);
            arrn2[n++] = tableColumn.getSortType() == TableColumn.SortType.ASCENDING ? 0 : 1;
        }
        UserPropertyManagement.setIntArray("TableContent.lastSortOrder.values", arrn);
        UserPropertyManagement.setIntArray("TableContent.lastSortOrderType.value", arrn2);
    }

    @Override
    public void setSendScreenCaptureEventHandler(EventHandler<Event> eventHandler) {
        this.sendScreenCaptureEventHandler = eventHandler;
    }

    @Override
    public TIDevice getSelectedDevice() {
        return this.selectedDevice;
    }

}

