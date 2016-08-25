/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.StringProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.CheckBox
 *  javafx.scene.layout.BorderPane
 */
package com.ti.et.elg.programEditor;

import com.ti.et.elg.commonUISupport.customComponents.TIPatternTextField;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameManager;
import com.ti.et.elg.programEditor.ProgramEditorLineNumberedTextArea;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.exports.ProgramStateChangedListener;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;

public class ProgramEditorSingleProgram {
    @FXML
    private TIPatternTextField txtFldVarName;
    @FXML
    private CheckBox chkBoxProtected;
    private final String LOG_TAG = ProgramEditorSingleProgram.class.getSimpleName();
    private Node rootNode;
    private ProgramEditorLineNumberedTextArea programEditorLineNumberedTextArea = new ProgramEditorLineNumberedTextArea();
    private boolean dirty = false;
    private boolean unsavedNewFile = true;
    private String srcFilePath = "";
    List<ProgramStateChangedListener> listeners = new ArrayList<ProgramStateChangedListener>();

    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("programEditorSingleProgram.fxml"));
            fXMLLoader.setResources((ResourceBundle)ProgramEditorResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError(this.LOG_TAG, "init", var1_2);
        }
        this.txtFldVarName.setCustomNamePattern(VarNameManager.getRegularExpression(5));
        this.programEditorLineNumberedTextArea.init();
        ((BorderPane)this.rootNode).setCenter(this.programEditorLineNumberedTextArea.getRootNode());
        this.txtFldVarName.textProperty().addListener((ChangeListener)new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                ProgramEditorSingleProgram.this.fireProgramPropertiesChanged();
            }
        });
        this.chkBoxProtected.selectedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                ProgramEditorSingleProgram.this.fireProgramPropertiesChanged();
            }
        });
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public String getText() {
        return this.programEditorLineNumberedTextArea.getText();
    }

    public Node getTextAreaNode() {
        return this.programEditorLineNumberedTextArea.getTextAreaNode();
    }

    public void setText(String string) {
        this.programEditorLineNumberedTextArea.setText(string);
    }

    public void insertText(String string) {
        this.programEditorLineNumberedTextArea.insertText(string);
    }

    public String getVarNameText() {
        return this.txtFldVarName.getText();
    }

    public Node getVarNameNode() {
        return this.txtFldVarName;
    }

    public void setVarNameText(String string) {
        this.txtFldVarName.setText(string);
    }

    public boolean getProtectedState() {
        return this.chkBoxProtected.isSelected();
    }

    public Node getProtectedStateNode() {
        return this.chkBoxProtected;
    }

    public void setProtectedState(boolean bl) {
        this.chkBoxProtected.setSelected(bl);
    }

    public boolean isUndoable() {
        return this.programEditorLineNumberedTextArea.isUndoable();
    }

    public boolean isRedoable() {
        return this.programEditorLineNumberedTextArea.isRedoable();
    }

    public boolean hasSelection() {
        return this.programEditorLineNumberedTextArea.hasSelection();
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean bl) {
        this.dirty = bl;
    }

    public boolean unsavedNewFile() {
        return this.unsavedNewFile;
    }

    public void setUnsavedNewFile(boolean bl) {
        this.unsavedNewFile = bl;
    }

    public void selectAll() {
        this.programEditorLineNumberedTextArea.selectAll();
    }

    public void unselectAll() {
        this.programEditorLineNumberedTextArea.unselectAll();
    }

    public void delete() {
        this.programEditorLineNumberedTextArea.delete();
    }

    public void undo() {
        this.programEditorLineNumberedTextArea.undo();
    }

    public void redo() {
        this.programEditorLineNumberedTextArea.redo();
    }

    public void cut() {
        this.programEditorLineNumberedTextArea.cut();
    }

    public void copy() {
        this.programEditorLineNumberedTextArea.copy();
    }

    public void paste() {
        this.programEditorLineNumberedTextArea.paste();
    }

    public void addProgramStateChangedListener(ProgramStateChangedListener programStateChangedListener) {
        this.programEditorLineNumberedTextArea.addProgramStateChangedListener(programStateChangedListener);
        this.listeners.add(programStateChangedListener);
    }

    public void removeProgramStateChangedListener(ProgramStateChangedListener programStateChangedListener) {
        this.programEditorLineNumberedTextArea.removeProgramStateChangedListener(programStateChangedListener);
        this.listeners.remove(programStateChangedListener);
    }

    private void fireProgramPropertiesChanged() {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                for (ProgramStateChangedListener programStateChangedListener : ProgramEditorSingleProgram.this.listeners) {
                    programStateChangedListener.programPropertiesChanged();
                }
            }
        });
    }

    public String getSrcFilePath() {
        return this.srcFilePath;
    }

    public void setSrcFilePath(String string) {
        this.srcFilePath = string;
    }

}

