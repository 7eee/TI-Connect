/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.property.ReadOnlyBooleanProperty
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.MultipleSelectionModel
 *  javafx.scene.control.SplitPane
 *  javafx.scene.control.SplitPane$Divider
 *  javafx.scene.control.TreeItem
 *  javafx.scene.control.TreeView
 *  javafx.scene.input.MouseEvent
 */
package com.ti.et.elg.commonUISupport.catalog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.catalog.TICatalogBase;
import com.ti.et.elg.commonUISupport.catalog.TICatalogSyntaxBox;
import com.ti.et.elg.commonUISupport.catalog.TITreeItemDataReceiver;
import com.ti.et.elg.commonUISupport.catalog.trees.TITreeItem;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

public class TICatalog
extends TICatalogBase {
    private static final double DEFAULT_SPLITTER_POSITION = 0.8;
    @FXML
    protected TreeView<String> catalogTree;
    @FXML
    protected ComboBox<String> catalogSelector;
    @FXML
    protected SplitPane catalogSplitPane;
    protected Node rootNode;
    private static final String TAG = TICatalog.class.getSimpleName();
    private Set<TITreeItemDataReceiver> textListeners = new HashSet<TITreeItemDataReceiver>();
    private TICatalogSyntaxBox catalogSyntaxBox = new TICatalogSyntaxBox();

    public void init(URL uRL, TIResourceBundle[] arrtIResourceBundle) throws MalformedURLException, IllegalArgumentException {
        if (null == uRL || null == uRL.getFile() || uRL.getFile().isEmpty()) {
            throw new MalformedURLException("The URL is not valid");
        }
        if (null == arrtIResourceBundle || arrtIResourceBundle.length < 1) {
            throw new IllegalArgumentException("Resources were empty or null.\n");
        }
        this.resourceBundles = arrtIResourceBundle;
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(uRL);
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
        }
        catch (IOException var3_4) {
            TILogger.logError(TAG, "Could not initialize the catalog, please retry.", var3_4);
        }
        this.catalogSyntaxBox.init();
        this.catalogSplitPane.getItems().add((Object)this.catalogSyntaxBox.getRootNode());
        SplitPane.setResizableWithParent((Node)this.catalogSyntaxBox.getRootNode(), (Boolean)false);
        this.catalogSyntaxBox.getRootNode().disableProperty().bind((ObservableValue)this.catalogTree.disabledProperty());
        this.catalogTree.setEditable(false);
        this.catalogTree.setShowRoot(false);
        this.catalogTree.setOnMouseClicked((EventHandler)new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    TICatalog.this.insertItemToEditor();
                }
            }
        });
        this.catalogTree.getSelectionModel().selectedItemProperty().addListener((ChangeListener)new ChangeListener<TreeItem<String>>(){

            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> treeItem, TreeItem<String> treeItem2) {
                if (treeItem != treeItem2) {
                    TICatalog.this.catalogSyntaxBox.updateSyntaxBox(treeItem2);
                }
            }
        });
        this.catalogSelector.setEditable(false);
        double d = 0.8;
        String string = UserPropertyManagement.getUserProperty("PECatalogSyntaxSplitter.position.value");
        if (null != string && !string.isEmpty()) {
            try {
                d = Double.parseDouble(string);
            }
            catch (Exception var6_7) {
                TILogger.logDetail(TAG, "Splitter Position property was not set.");
            }
        }
        ((SplitPane.Divider)this.catalogSplitPane.getDividers().get(0)).setPosition(d);
        ((SplitPane.Divider)this.catalogSplitPane.getDividers().get(0)).positionProperty().addListener((ChangeListener)new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                UserPropertyManagement.setUserProperty("PECatalogSyntaxSplitter.position.value", number2.toString());
            }
        });
    }

    protected void insertItemToEditor() {
        TITreeItem tITreeItem = (TITreeItem)((Object)this.catalogTree.getSelectionModel().getSelectedItem());
        if (tITreeItem != null && tITreeItem.isLeaf()) {
            for (TITreeItemDataReceiver tITreeItemDataReceiver : this.textListeners) {
                tITreeItemDataReceiver.insertTextFromTreeItem((String)tITreeItem.getValue(), tITreeItem);
            }
        }
    }

    public void addDataReceiver(TITreeItemDataReceiver tITreeItemDataReceiver) {
        if (null != tITreeItemDataReceiver) {
            this.textListeners.add(tITreeItemDataReceiver);
        }
    }

    public void removeDataReceiver(TITreeItemDataReceiver tITreeItemDataReceiver) {
        if (null != tITreeItemDataReceiver) {
            this.textListeners.remove(tITreeItemDataReceiver);
        }
    }

}

