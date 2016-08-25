/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Node
 *  javafx.scene.control.Label
 *  javafx.scene.control.ScrollPane
 *  javafx.scene.control.SelectionMode
 *  javafx.scene.control.TableCell
 *  javafx.scene.control.TableColumn
 *  javafx.scene.control.TableView
 *  javafx.scene.control.TableView$TableViewSelectionModel
 *  javafx.scene.control.cell.PropertyValueFactory
 *  javafx.scene.layout.HBox
 *  javafx.stage.FileChooser
 *  javafx.stage.FileChooser$ExtensionFilter
 *  javafx.util.Callback
 */
package com.ti.et.elg.dataEditor;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.dataEditor.dataTypes.TIDeviceNumberData;
import com.ti.et.elg.dataEditor.exports.DataEditorContentsInterface;
import com.ti.et.elg.dataEditor.translator.DataEditorTranslator;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class DataEditorContents
implements DataEditorContentsInterface {
    private static final String DEFAULT_COLUMN_HEADER_PREFIX = "COLUMN";
    private static final String DEFAULT_COLUMN_HEADER_FIRST_INDEX = "0";
    private static final double DEFAULT_MIN_COLUMN_WIDTH = 100.0;
    private static final double DEFAULT_MAX_COLUMN_WIDTH = 200.0;
    private static final int FIRST_COL_INDEX = 0;
    @FXML
    ScrollPane dataEditorScrollContainer;
    @FXML
    TableView<TIDeviceNumberData> dataEditorTableView;
    private Node root;
    private final String TAG = DataEditorContents.class.getSimpleName();
    private OverlayStackPaneBase stackPaneParent;
    private String[] validExtensions = new String[]{".8xn", ".8xl"};

    @Override
    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("dataEditorContents.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.root = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError(this.TAG, "Could not Initialize. Error Loading FXML Layout", var1_2);
        }
        this.initTableView();
    }

    @Override
    public Node getRootNode() {
        return this.root;
    }

    @Override
    public Node getFocusableNode() {
        return this.dataEditorTableView;
    }

    private void initTableView() {
        if (null != this.dataEditorTableView) {
            TableColumn<TIDeviceNumberData, Double> tableColumn = this.initFirstColumn();
            ObservableList observableList = FXCollections.observableArrayList();
            for (int i = 0; i < 3; ++i) {
                try {
                    TIDeviceNumberData tIDeviceNumberData = new TIDeviceNumberData();
                    tIDeviceNumberData.setRealNumber(Math.random());
                    observableList.add((Object)tIDeviceNumberData);
                    continue;
                }
                catch (Exception var4_5) {
                    TILogger.logError(this.TAG, "Error Creating first Data", var4_5);
                }
            }
            this.dataEditorTableView.getSelectionModel().setCellSelectionEnabled(true);
            this.dataEditorTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            this.dataEditorTableView.setItems(observableList);
            this.dataEditorTableView.getColumns().add(tableColumn);
            tableColumn.setSortable(false);
        } else {
            TILogger.logError(this.TAG, "Could not initialize Data Editor Table. Table was null");
        }
    }

    private TableColumn<TIDeviceNumberData, Double> initFirstColumn() {
        TIResourceBundle tIResourceBundle = CommonUISupportResourceBundle.BUNDLE;
        String string = tIResourceBundle.getString("dataeditor.table.header");
        if (null == string || string.isEmpty()) {
            string = "COLUMN 0";
        }
        return this.createColumn(string);
    }

    private TableColumn<TIDeviceNumberData, Double> createColumn(String string) {
        TableColumn tableColumn = new TableColumn(string);
        tableColumn.setMinWidth(100.0);
        tableColumn.setPrefWidth(100.0);
        tableColumn.setMaxWidth(200.0);
        tableColumn.setComparator((Comparator)new Comparator<Double>(){
            private static final int FIRST_GREATER_THAN_SECOND = 1;
            private static final int FIRST_EQUALS_THAN_SECOND = 0;
            private static final int SECOND_GREATER_THAN_FIRST = -1;

            @Override
            public int compare(Double d, Double d2) {
                if (d > d2) {
                    return 1;
                }
                if (d.doubleValue() == d2.doubleValue()) {
                    return 0;
                }
                return -1;
            }
        });
        tableColumn.setCellValueFactory((Callback)new PropertyValueFactory("realNumber"));
        tableColumn.setCellFactory((Callback)new Callback<TableColumn<TIDeviceNumberData, Double>, TableCell<TIDeviceNumberData, Double>>(){

            public TableCell<TIDeviceNumberData, Double> call(TableColumn<TIDeviceNumberData, Double> tableColumn) {
                TableCell<TIDeviceNumberData, Double> tableCell = new TableCell<TIDeviceNumberData, Double>(){

                    public void updateItem(Double d, boolean bl) {
                        super.updateItem((Object)d, bl);
                        if (null != d) {
                            HBox hBox = new HBox();
                            hBox.setPadding(new Insets(3.0, 3.0, 3.0, 5.0));
                            hBox.setSpacing(5.0);
                            hBox.setAlignment(Pos.CENTER_RIGHT);
                            Label label = new Label(d.toString());
                            hBox.getChildren().add((Object)label);
                            this.setGraphic((Node)hBox);
                            this.setEditable(true);
                        }
                    }
                };
                return tableCell;
            }

        });
        tableColumn.setId(string);
        return tableColumn;
    }

    @Override
    public void setParent(OverlayStackPaneBase overlayStackPaneBase) {
        this.stackPaneParent = overlayStackPaneBase;
    }

    @Override
    public OverlayStackPaneBase getParent() {
        return this.stackPaneParent;
    }

    @Override
    public void showPane(Node node) {
        this.stackPaneParent.showPane(node);
    }

    @Override
    public void hidePane(Node node) {
        this.stackPaneParent.hidePane(node);
    }

    @Override
    public void openFiles(List<File> list) {
        for (File file : list) {
            this.openFromFile(file);
        }
    }

    @Override
    public void openFromFile(File file) {
        CommManagerInterface commManagerInterface;
        TIVar tIVar;
        if (file == null || !file.exists()) {
            TILogger.logError(this.TAG, "Error Opening File, File does not Exist");
            String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.DATA_EDITING, new TIStatus(-1), null, file.getAbsolutePath());
            PromptDialog.showUserError(arrstring[0], arrstring[1]);
            return;
        }
        if (this.fileHasValidExtension(file) && this.isDisplayableData(tIVar = (commManagerInterface = ConnectServerFactory.getCommManager()).createTIVar(file.getAbsolutePath(), null, null, 0, 0, 0, -1, false, 0, 0, false))) {
            switch (tIVar.getVarType()) {
                case 0: {
                    TIStatus tIStatus = DataEditorTranslator.convertRealToFloat(tIVar.getData());
                    if (tIStatus.getStatusCode() != 0) break;
                    ObservableList observableList = FXCollections.observableArrayList();
                    TIDeviceNumberData tIDeviceNumberData = new TIDeviceNumberData();
                    tIDeviceNumberData.setRealNumber(DataEditorTranslator.getResult());
                    observableList.add((Object)tIDeviceNumberData);
                    this.dataEditorTableView.setItems(observableList);
                    ((TableColumn)this.dataEditorTableView.getColumns().get(0)).setText(tIVar.getDeviceFileName());
                    TILogger.logDetail(this.TAG, "TIVar Device File Name " + tIVar.getDeviceFileName());
                    break;
                }
                case 1: {
                    TIStatus tIStatus = DataEditorTranslator.convertRealListToJavaList(tIVar.getData());
                    if (tIStatus.getStatusCode() != 0) break;
                    TILogger.logDetail(this.TAG, "List was created successfully");
                    ObservableList observableList = FXCollections.observableArrayList();
                    List<Double> list = DataEditorTranslator.getResultList();
                    for (Double d : list) {
                        TIDeviceNumberData tIDeviceNumberData = new TIDeviceNumberData();
                        tIDeviceNumberData.setRealNumber(d);
                        observableList.add((Object)tIDeviceNumberData);
                    }
                    TILogger.logDetail(this.TAG, "New List has " + list.size() + " entries");
                    this.dataEditorTableView.setItems(observableList);
                    Iterator<Double> iterator = this.createColumn(tIVar.getDeviceFileName());
                    this.dataEditorTableView.getColumns().clear();
                    this.dataEditorTableView.getColumns().add(iterator);
                    TILogger.logDetail(this.TAG, "TIVar Device File Name " + tIVar.getDeviceFileName());
                    break;
                }
                case 32: {
                    break;
                }
                default: {
                    TILogger.logError(this.TAG, "Cannot display this type of data.");
                }
            }
        }
    }

    @Override
    public void saveContents(File file) {
        FileChooser fileChooser = new FileChooser();
        List<FileChooser.ExtensionFilter> list = this.getApproriateExtensions();
        if (list.size() > 0) {
            for (FileChooser.ExtensionFilter extensionFilter : list) {
                fileChooser.getExtensionFilters().add((Object)extensionFilter);
            }
        }
    }

    protected List<FileChooser.ExtensionFilter> getApproriateExtensions() {
        FileChooser.ExtensionFilter extensionFilter;
        ArrayList<FileChooser.ExtensionFilter> arrayList = new ArrayList<FileChooser.ExtensionFilter>();
        if (this.dataEditorTableView.getColumns().size() == 1 && this.dataEditorTableView.getItems().size() == 1) {
            extensionFilter = new FileChooser.ExtensionFilter("TI Graphing Calculator Real Number", new String[]{"8xn"});
            arrayList.add(extensionFilter);
            FileChooser.ExtensionFilter extensionFilter2 = new FileChooser.ExtensionFilter("TI Graphing Calculator Complex Number", new String[]{"8xc"});
            arrayList.add(extensionFilter2);
        }
        extensionFilter = new FileChooser.ExtensionFilter("TI graphing Calculator Complex Number List", new String[]{"8xl"});
        arrayList.add(extensionFilter);
        return arrayList;
    }

    private boolean isDisplayableData(TIVar tIVar) {
        switch (tIVar.getVarType()) {
            case 0: 
            case 1: 
            case 32: {
                return true;
            }
        }
        TILogger.logError(this.TAG, "Cannot display this type of data.");
        return false;
    }

    private boolean fileHasValidExtension(File file) {
        boolean bl = false;
        if (!file.isDirectory()) {
            for (String string : this.validExtensions) {
                if (!file.getName().endsWith(string)) continue;
                bl = true;
                break;
            }
        }
        return bl;
    }

}

