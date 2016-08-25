/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.ReadOnlyBooleanProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Node
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.MultipleSelectionModel
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.control.TreeCell
 *  javafx.scene.control.TreeItem
 *  javafx.scene.control.TreeView
 *  javafx.scene.input.ClipboardContent
 *  javafx.scene.input.Dragboard
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.input.TransferMode
 *  javafx.util.Callback
 */
package com.ti.et.elg.programEditor;

import com.ti.et.elg.commonUISupport.catalog.TICatalog;
import com.ti.et.elg.commonUISupport.catalog.TITreeItemDataReceiver;
import com.ti.et.elg.commonUISupport.catalog.trees.TITreeItem;
import com.ti.et.elg.connectServer.translator.TranslatorResourceBundles;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.exports.ProgramEditorCatalogInterface;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;

public class ProgramEditorCatalog
extends TICatalog
implements ProgramEditorCatalogInterface,
TITreeItemDataReceiver {
    private static final String TAG = ProgramEditorCatalog.class.getSimpleName();
    private final Map<String, String> catalogName2CatalogFilePathMap = new HashMap<String, String>();
    private final Map<String, Integer> itemName2TreeItemMap = new HashMap<String, Integer>();
    private final BooleanProperty isDragging = new SimpleBooleanProperty();
    private final ArrayList<String> keysAsList = new ArrayList();
    private static final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-text-fill:#414141;";
    private static final String STYLE_SELECTED = "-fx-background-color: #28a8e0; -fx-text-fill:#ffffff;";
    private static final String STYLE_HOVER = "-fx-background-color: #28a8e026;";
    private static final String STYLE_NO_FOCUS = "-fx-background-color: #d1d3d4; -fx-text-fill:#414141;";

    @Override
    public void init() {
        this.addDataReceiver(this);
        try {
            TIResourceBundle[] arrtIResourceBundle = new TIResourceBundle[]{TranslatorResourceBundles.CATALOGS_BUNDLE, ProgramEditorResourceBundle.SYNTAXES_BUNDLE};
            this.init(this.getClass().getResource("/com/ti/et/elg/commonUISupport/catalog/catalogTreeView.fxml"), arrtIResourceBundle);
            String string = ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84PlusCE");
            this.catalogName2CatalogFilePathMap.put(string, "/com/ti/et/elg/programEditor/catalogData/properties/TI-84_Plus_CE.xml");
            this.keysAsList.add(string);
            this.catalogName2CatalogFilePathMap.put(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti83PremiumCE"), "/com/ti/et/elg/programEditor/catalogData/properties/TI-83_Premium_CE.xml");
            this.keysAsList.add(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti83PremiumCE"));
            this.catalogName2CatalogFilePathMap.put(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84PlusCSE"), "/com/ti/et/elg/programEditor/catalogData/properties/TI-84_Plus_C_Silver_Edition.xml");
            this.keysAsList.add(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84PlusCSE"));
            this.catalogName2CatalogFilePathMap.put(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84PlusSE"), "/com/ti/et/elg/programEditor/catalogData/properties/TI-84_Plus_Silver_Edition.xml");
            this.keysAsList.add(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84PlusSE"));
            this.catalogName2CatalogFilePathMap.put(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84Plus"), "/com/ti/et/elg/programEditor/catalogData/properties/TI-84_Plus.xml");
            this.keysAsList.add(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84Plus"));
            this.catalogName2CatalogFilePathMap.put(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84PlusT"), "/com/ti/et/elg/programEditor/catalogData/properties/TI-84_Plus_T.xml");
            this.keysAsList.add(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti84PlusT"));
            this.catalogName2CatalogFilePathMap.put(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti83Plus.fr"), "/com/ti/et/elg/programEditor/catalogData/properties/TI-83_Plus.xml");
            this.keysAsList.add(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti83Plus.fr"));
            this.catalogName2CatalogFilePathMap.put(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti82Advanced"), "/com/ti/et/elg/programEditor/catalogData/properties/TI-82_Advanced.xml");
            this.keysAsList.add(ProgramEditorResourceBundle.BUNDLE.getString("programEditor.catalog.selector.item.ti82Advanced"));
            this.catalogSelector.getItems().clear();
            this.catalogSelector.getItems().addAll(this.keysAsList);
            for (String string2 : this.keysAsList) {
                this.itemName2TreeItemMap.put(string2, -1);
            }
            this.catalogSelector.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    TreeItem treeItem;
                    String string = (String)ProgramEditorCatalog.this.catalogSelector.getSelectionModel().getSelectedItem();
                    UserPropertyManagement.setUserProperty("ProgramEditor.lastCatalog.value", string);
                    Integer n = (Integer)ProgramEditorCatalog.this.itemName2TreeItemMap.get(string);
                    if (null == n || n < 0) {
                        boolean bl = ProgramEditorCatalog.this.loadInfoFromFile(this.getClass().getResourceAsStream((String)ProgramEditorCatalog.this.catalogName2CatalogFilePathMap.get(string)));
                        if (!bl) {
                            TILogger.logError(TAG, "No Catalog was loaded for " + string);
                            return;
                        }
                        n = ProgramEditorCatalog.this.allCatalogs.size() - 1;
                        ProgramEditorCatalog.this.itemName2TreeItemMap.put(string, n);
                    }
                    if (null != (treeItem = (TreeItem)ProgramEditorCatalog.this.allCatalogs.get(n.intValue()))) {
                        if (null != treeItem.getValue() && ((String)treeItem.getValue()).equals(string)) {
                            ProgramEditorCatalog.this.catalogTree.setRoot(treeItem);
                        }
                    } else {
                        ProgramEditorCatalog.this.allCatalogs.remove((Object)treeItem);
                    }
                }
            });
            Object object = UserPropertyManagement.getString("ProgramEditor.lastCatalog.value", string);
            object = this.catalogName2CatalogFilePathMap.containsKey(object) ? object : string;
            this.catalogSelector.getSelectionModel().select(object);
            this.setDisabled(true);
            this.catalogTree.setOnDragDetected((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    ProgramEditorCatalog.this.isDragging.set(((TreeItem)ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedItem()).isLeaf());
                    if (ProgramEditorCatalog.this.isDragging()) {
                        Dragboard dragboard = ProgramEditorCatalog.this.catalogTree.startDragAndDrop(new TransferMode[]{TransferMode.COPY});
                        ClipboardContent clipboardContent = new ClipboardContent();
                        clipboardContent.putString((String)((TreeItem)ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedItem()).getValue());
                        dragboard.setContent((Map)clipboardContent);
                    }
                }
            });
            this.catalogTree.setOnKeyPressed((EventHandler)new CatalogTreeKeyHandler());
            this.catalogTree.setCellFactory((Callback)new Callback<TreeView<String>, TreeCell<String>>(){

                public TreeCell<String> call(TreeView<String> treeView) {
                    return new CatalogTreeCellFactory();
                }
            });
            this.catalogTree.focusedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    TreeItem treeItem = (TreeItem)ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedItem();
                    if (treeItem != null && treeItem.getGraphic() != null) {
                        Node node = treeItem.getGraphic();
                        if (bl2.booleanValue()) {
                            node.setStyle("-fx-background-color: #28a8e0; -fx-text-fill:#ffffff;");
                        } else {
                            node.setStyle("-fx-background-color: #d1d3d4; -fx-text-fill:#414141;");
                        }
                    }
                }
            });
        }
        catch (ClassCastException | IllegalArgumentException | NullPointerException | UnsupportedOperationException | MalformedURLException var1_2) {
            TILogger.logError(TAG, "Could not load catlog Files please retry.", var1_2);
        }
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public void setDisabled(boolean bl) {
        this.catalogSelector.setDisable(bl);
        this.catalogTree.setDisable(bl);
    }

    @Override
    public void insertTextFromTreeItem(String string, TITreeItem tITreeItem) {
        ProgramEditorFactory.getProgramEditorContents().insertText(string);
    }

    @Override
    public Node getDragableNode() {
        return this.catalogTree;
    }

    @Override
    public boolean isDragging() {
        return this.isDragging.get();
    }

    @Override
    public Node getFocusableNode() {
        return this.catalogTree;
    }

    private class CatalogTreeKeyHandler
    implements EventHandler<KeyEvent> {
        private CatalogTreeKeyHandler() {
        }

        public void handle(KeyEvent keyEvent) {
            TITreeItem tITreeItem = null;
            switch (keyEvent.getCode()) {
                case ENTER: {
                    ProgramEditorCatalog.this.insertItemToEditor();
                    break;
                }
                case ESCAPE: {
                    TITreeItem tITreeItem2;
                    tITreeItem = (TITreeItem)((Object)ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedItem());
                    if (tITreeItem == null || (tITreeItem2 = (TITreeItem)tITreeItem.getParent()) == null || tITreeItem2.getParent() == null) break;
                    ProgramEditorCatalog.this.catalogTree.getSelectionModel().select((Object)tITreeItem2);
                    ProgramEditorCatalog.this.catalogTree.scrollTo(ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedIndex());
                    break;
                }
                case LEFT: {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            ProgramEditorCatalog.this.catalogTree.scrollTo(ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedIndex());
                        }
                    });
                    break;
                }
                case RIGHT: {
                    tITreeItem = (TITreeItem)((Object)ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedItem());
                    if (tITreeItem == null || tITreeItem.getChildren().size() <= 0) break;
                    final TITreeItem tITreeItem3 = (TITreeItem)((Object)tITreeItem.getChildren().get(0));
                    final boolean bl = tITreeItem3.isExpanded();
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            ProgramEditorCatalog.this.catalogTree.getSelectionModel().select((Object)tITreeItem3);
                            if (!tITreeItem3.isLeaf()) {
                                tITreeItem3.setExpanded(bl);
                            }
                            ProgramEditorCatalog.this.catalogTree.scrollTo(ProgramEditorCatalog.this.catalogTree.getSelectionModel().getSelectedIndex());
                        }
                    });
                    break;
                }
            }
        }

    }

    private class CatalogTreeCellFactory
    extends TreeCell<String> {
        private CatalogTreeCellFactory _instance;

        CatalogTreeCellFactory() {
            this._instance = this;
            this.addListeners();
        }

        private void addListeners() {
            this.hoverProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (!CatalogTreeCellFactory.this._instance.isSelected() && !CatalogTreeCellFactory.this._instance.isFocused()) {
                        if (bl2.booleanValue()) {
                            CatalogTreeCellFactory.this._instance.setStyle("-fx-background-color: #28a8e026;");
                        } else {
                            CatalogTreeCellFactory.this._instance.setStyle("-fx-background-color: transparent; -fx-text-fill:#414141;");
                        }
                    }
                }
            });
            this.selectedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (bl2.booleanValue()) {
                        CatalogTreeCellFactory.this._instance.setStyle("-fx-background-color: #28a8e0; -fx-text-fill:#ffffff;");
                    } else {
                        CatalogTreeCellFactory.this._instance.setStyle("-fx-background-color: transparent; -fx-text-fill:#414141;");
                    }
                }
            });
            this.setOnMousePressed((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    if (CatalogTreeCellFactory.this.getItem() != null) {
                        boolean bl = CatalogTreeCellFactory.this.getTreeItem().isExpanded();
                        ProgramEditorCatalog.this.catalogTree.getSelectionModel().select((Object)CatalogTreeCellFactory.this.getTreeItem());
                        if (!CatalogTreeCellFactory.this.getTreeItem().isLeaf()) {
                            CatalogTreeCellFactory.this.getTreeItem().setExpanded(bl);
                        }
                    }
                }
            });
        }

        protected void updateItem(String string, boolean bl) {
            super.updateItem((Object)string, bl);
            if (bl || string == null) {
                this.setText(null);
                this.setGraphic(null);
                this.setDisable(true);
            } else {
                this.setText(string);
                this.getTreeItem().setGraphic((Node)this);
                this.setDisable(false);
            }
        }

    }

}

