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
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.HBox
 */
package com.ti.et.elg.programEditor;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIButton;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.actions.CreateProgramAction;
import com.ti.et.elg.programEditor.actions.OpenProgramAction;
import com.ti.et.elg.programEditor.actions.RedoAction;
import com.ti.et.elg.programEditor.actions.SaveProgramAction;
import com.ti.et.elg.programEditor.actions.SendProgramToHHAction;
import com.ti.et.elg.programEditor.actions.UndoAction;
import com.ti.et.elg.programEditor.exports.ProgramEditorToolbarInterface;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ProgramEditorToolbar
implements ProgramEditorToolbarInterface {
    @FXML
    TIButton butnProgramEditorAdd;
    @FXML
    TIButton butnProgramEditorOpen;
    @FXML
    TIButton butnProgramEditorSave;
    @FXML
    TIButton butnProgramEditorSendToHHs;
    @FXML
    TIButton butnProgramEditorUndo;
    @FXML
    TIButton butnProgramEditorRedo;
    @FXML
    HBox toolBarSpacerHbox;
    private Node rootNode = null;
    private int offsetPadding = 11;

    @Override
    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("programEditorToolbar.fxml"));
            fXMLLoader.setResources((ResourceBundle)ProgramEditorResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
            this.initToolBar();
        }
        catch (IOException var1_2) {
            TILogger.logError("ProgramEditorToolbar", "init", var1_2);
        }
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    private void initToolBar() {
        this.butnProgramEditorAdd.setAction(CreateProgramAction.getInstance());
        this.butnProgramEditorOpen.setAction(OpenProgramAction.getInstance());
        this.butnProgramEditorSave.setAction(SaveProgramAction.getInstance());
        this.butnProgramEditorSendToHHs.setAction(SendProgramToHHAction.getInstance());
        this.butnProgramEditorUndo.setAction(UndoAction.getInstance());
        this.butnProgramEditorRedo.setAction(RedoAction.getInstance());
        BorderPane borderPane = (BorderPane)ProgramEditorFactory.getProgramEditorCatalog().getRootNode();
        if (borderPane != null) {
            borderPane.widthProperty().addListener((ChangeListener)new ChangeListener<Object>(){

                public void changed(ObservableValue<?> observableValue, Object object, Object object2) {
                    Double d = (Double)object2;
                    ProgramEditorToolbar.this.toolBarSpacerHbox.setPrefWidth(d - (double)ProgramEditorToolbar.this.offsetPadding);
                }
            });
        }
    }

}

