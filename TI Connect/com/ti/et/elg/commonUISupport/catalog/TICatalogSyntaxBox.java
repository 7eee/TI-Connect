/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextArea
 *  javafx.scene.control.TreeItem
 */
package com.ti.et.elg.commonUISupport.catalog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.catalog.trees.TITreeItem;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;

public class TICatalogSyntaxBox {
    private Node rootNode;
    @FXML
    protected TextArea syntaxesTextArea;
    @FXML
    protected Label syntaxCountLabel;
    @FXML
    protected Button syntaxCountBtnMinus;
    @FXML
    protected Button syntaxCountBtnPlus;
    private static final String LOG_TAG = TICatalogSyntaxBox.class.getSimpleName();
    private String defaultSyntaxText = CommonUISupportResourceBundle.BUNDLE.getString("programEditor.syntaxReference.defaultSyntaxText");
    private String defaultLabelText = CommonUISupportResourceBundle.BUNDLE.getString("programEditor.syntaxReference.defaultSyntaxLabel");
    private int currentSyntaxIndexShown = 0;
    private int numOfSyntaxAvailable = 1;
    private TITreeItem currentItem = null;

    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("catalogSyntaxReference.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError(LOG_TAG, "Could not initialize the syntax box, please retry.", var1_2);
        }
        this.syntaxesTextArea.setEditable(false);
        this.updateTextAreaSyntaxWithIndex(this.currentSyntaxIndexShown);
        this.updateLabelCounter();
        this.syntaxCountBtnMinus.setDisable(true);
        this.syntaxCountBtnPlus.setDisable(true);
        this.syntaxCountBtnMinus.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                if (TICatalogSyntaxBox.this.currentSyntaxIndexShown > 0) {
                    TICatalogSyntaxBox.this.updateTextAreaSyntaxWithIndex(--TICatalogSyntaxBox.this.currentSyntaxIndexShown);
                    TICatalogSyntaxBox.this.updateLabelCounter();
                    if (TICatalogSyntaxBox.this.currentSyntaxIndexShown == 0) {
                        TICatalogSyntaxBox.this.syntaxCountBtnMinus.setDisable(true);
                    }
                } else {
                    TICatalogSyntaxBox.this.syntaxCountBtnMinus.setDisable(true);
                }
                if (TICatalogSyntaxBox.this.currentSyntaxIndexShown < TICatalogSyntaxBox.this.numOfSyntaxAvailable - 1) {
                    TICatalogSyntaxBox.this.syntaxCountBtnPlus.setDisable(false);
                }
            }
        });
        this.syntaxCountBtnPlus.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                if (TICatalogSyntaxBox.this.currentSyntaxIndexShown < TICatalogSyntaxBox.this.numOfSyntaxAvailable - 1) {
                    TICatalogSyntaxBox.this.updateTextAreaSyntaxWithIndex(++TICatalogSyntaxBox.this.currentSyntaxIndexShown);
                    TICatalogSyntaxBox.this.updateLabelCounter();
                    if (TICatalogSyntaxBox.this.currentSyntaxIndexShown == TICatalogSyntaxBox.this.numOfSyntaxAvailable - 1) {
                        TICatalogSyntaxBox.this.syntaxCountBtnPlus.setDisable(true);
                    }
                } else {
                    TICatalogSyntaxBox.this.syntaxCountBtnPlus.setDisable(true);
                }
                if (TICatalogSyntaxBox.this.currentSyntaxIndexShown > 0) {
                    TICatalogSyntaxBox.this.syntaxCountBtnMinus.setDisable(false);
                }
            }
        });
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    private void updateTextAreaSyntaxWithIndex(int n) {
        String string;
        String string2 = string = this.currentItem != null ? this.currentItem.getSyntax(n) : null;
        if (string != null) {
            this.syntaxesTextArea.setText(string);
        } else {
            this.syntaxesTextArea.setText(this.defaultSyntaxText);
        }
    }

    private void updateLabelCounter() {
        this.syntaxCountLabel.setText(MessageFormat.format(this.defaultLabelText, this.currentSyntaxIndexShown + 1, this.numOfSyntaxAvailable));
    }

    public void updateSyntaxBox(TreeItem<String> treeItem) {
        this.currentSyntaxIndexShown = 0;
        this.numOfSyntaxAvailable = 1;
        TITreeItem tITreeItem = this.currentItem = treeItem != null ? (TITreeItem)treeItem : null;
        if (this.currentItem != null && this.currentItem.isLeaf() && this.currentItem.hasSyntaxes()) {
            this.numOfSyntaxAvailable = this.currentItem.getSyntaxesCount();
            this.updateLabelCounter();
            this.syntaxCountBtnMinus.setDisable(true);
            this.syntaxCountBtnPlus.setDisable(this.numOfSyntaxAvailable == 1);
            this.updateTextAreaSyntaxWithIndex(this.currentSyntaxIndexShown);
        } else {
            if (this.currentItem != null && this.currentItem.isLeaf()) {
                this.syntaxesTextArea.setText((String)this.currentItem.getValue());
            } else {
                this.syntaxesTextArea.setText(this.defaultSyntaxText);
            }
            this.updateLabelCounter();
            this.syntaxCountBtnMinus.setDisable(true);
            this.syntaxCountBtnPlus.setDisable(true);
        }
    }

}

