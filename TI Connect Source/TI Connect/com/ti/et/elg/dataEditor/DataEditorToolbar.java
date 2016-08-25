/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ReadOnlyDoubleProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextField
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.HBox
 */
package com.ti.et.elg.dataEditor;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIButton;
import com.ti.et.elg.dataEditor.actions.OpenDataAction;
import com.ti.et.elg.dataEditor.actions.SaveDataAction;
import com.ti.et.elg.dataEditor.customComponents.TINumberPicker;
import com.ti.et.elg.dataEditor.exports.DataEditorToolbarInterface;
import com.ti.et.elg.dataEditor.factory.DataEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DataEditorToolbar
implements DataEditorToolbarInterface {
    @FXML
    protected ComboBox<String> dataEditorTypeChooser = null;
    @FXML
    protected HBox dataEditorToolbarSpacer = null;
    @FXML
    protected TIButton dataEditorOpen = null;
    @FXML
    protected TIButton dataEditorSave = null;
    @FXML
    protected Label colsTitle = null;
    @FXML
    protected Label rowsTitle = null;
    @FXML
    protected Button moreCols = null;
    @FXML
    protected Button moreRows = null;
    @FXML
    protected Button lessCols = null;
    @FXML
    protected Button lessRows = null;
    @FXML
    protected TextField colsNum = null;
    @FXML
    protected TextField rowsNum = null;
    private Node root = null;
    private static final String TAG = DataEditorToolbar.class.getSimpleName();
    private TINumberPicker colsPicker = null;
    private TINumberPicker rowsPicker = null;

    public DataEditorToolbar() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("dataEditorToolbar.fxml"));
            fXMLLoader.setClassLoader(this.getClass().getClassLoader());
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.root = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError(TAG, "Could not load Toolbar", var1_2);
        }
        if (null != this.root) {
            if (null != this.colsNum) {
                this.colsPicker = new TINumberPicker(this.lessCols, this.moreCols, this.colsNum, this.colsTitle);
                this.colsPicker.setTitle("COLUMNS");
                this.colsPicker.setInitialParameters(1.0, 1.0, 1.0, 86.0);
            }
            if (null != this.rowsNum) {
                this.rowsPicker = new TINumberPicker(this.lessRows, this.moreRows, this.rowsNum, this.rowsTitle);
                this.rowsPicker.setTitle("ROWS");
                this.rowsPicker.setInitialParameters(1.0, 1.0, 1.0, 86.0);
            }
            if (null != this.dataEditorOpen) {
                this.dataEditorOpen.setAction(OpenDataAction.getInstance());
            }
            if (null != this.dataEditorSave) {
                this.dataEditorSave.setAction(SaveDataAction.getInstance());
            }
            this.init();
        }
    }

    @Override
    public Node getRootNode() {
        return this.root;
    }

    @Override
    public void init() {
        BorderPane borderPane = (BorderPane)DataEditorFactory.getDataEditorLeftList().getRootNode();
        borderPane.widthProperty().addListener((ChangeListener)new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                DataEditorToolbar.this.dataEditorToolbarSpacer.setPrefWidth(number2.doubleValue());
            }
        });
    }

}

