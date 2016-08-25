/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.sun.javafx.scene.control.behavior.BehaviorBase
 *  com.sun.javafx.scene.control.behavior.TextInputControlBehavior
 *  com.sun.javafx.scene.control.skin.TextAreaSkin
 *  com.sun.javafx.scene.control.skin.TextInputControlSkin
 *  javafx.application.Platform
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.property.StringProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.control.IndexRange
 *  javafx.scene.control.Skin
 *  javafx.scene.control.TextArea
 *  javafx.scene.input.Clipboard
 *  javafx.scene.input.DataFormat
 *  javafx.scene.input.DragEvent
 *  javafx.scene.input.Dragboard
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.input.TransferMode
 */
package com.ti.et.elg.programEditor;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.TextInputControlBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import com.sun.javafx.scene.control.skin.TextInputControlSkin;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.exports.ProgramStateChangedListener;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;

public class ProgramEditorLineNumberedTextArea {
    @FXML
    private TextArea programEditorTextArea;
    @FXML
    private TextArea programEditorListNumbers;
    private Node rootNode;
    private final String LOG_TAG = ProgramEditorLineNumberedTextArea.class.getSimpleName();
    private int currentLineCount = 1;
    private boolean ignoreNextAction = false;
    List<ProgramStateChangedListener> listeners = new ArrayList<ProgramStateChangedListener>();

    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("programEditorLineNumberedTextArea.fxml"));
            fXMLLoader.setResources((ResourceBundle)ProgramEditorResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError(this.LOG_TAG, "init", var1_2);
        }
        this.programEditorListNumbers.textProperty().set((Object)"001");
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                ProgramEditorLineNumberedTextArea.this.programEditorTextArea.scrollTopProperty().addListener((ChangeListener)new ChangeListener<Number>(){

                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                        ProgramEditorLineNumberedTextArea.this.programEditorListNumbers.setScrollTop(number2.doubleValue());
                    }
                });
            }

        });
        this.programEditorTextArea.textProperty().addListener((ChangeListener)new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                ProgramEditorLineNumberedTextArea.this.updateLineNumbers(string2);
                ProgramEditorLineNumberedTextArea.this.fireProgramTextChanged();
            }
        });
        this.programEditorListNumbers.scrollTopProperty().addListener((ChangeListener)new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                double d = ProgramEditorLineNumberedTextArea.this.programEditorTextArea.getScrollTop();
                ProgramEditorLineNumberedTextArea.this.programEditorListNumbers.setScrollTop(d);
            }
        });
        this.programEditorTextArea.selectionProperty().addListener((ChangeListener)new ChangeListener<IndexRange>(){

            public void changed(ObservableValue<? extends IndexRange> observableValue, IndexRange indexRange, IndexRange indexRange2) {
                ProgramEditorLineNumberedTextArea.this.fireProgramSelectionChanged();
            }
        });
        this.programEditorTextArea.setOnDragOver((EventHandler)new EventHandler<DragEvent>(){

            public void handle(DragEvent dragEvent) {
                try {
                    if (!ProgramEditorLineNumberedTextArea.this.programEditorTextArea.isFocused()) {
                        ProgramEditorLineNumberedTextArea.this.programEditorTextArea.requestFocus();
                    }
                    Node node = (Node)dragEvent.getGestureSource();
                    Node node2 = ProgramEditorFactory.getProgramEditorCatalog().getDragableNode();
                    if ((dragEvent.getGestureSource() == node2 || ((Parent)node2).getChildrenUnmodifiable().contains((Object)node)) && ProgramEditorFactory.getProgramEditorCatalog().isDragging()) {
                        dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.COPY});
                        int n = (int)ProgramEditorLineNumberedTextArea.this.programEditorTextArea.getScrollTop();
                        int n2 = (int)ProgramEditorLineNumberedTextArea.this.programEditorTextArea.getScrollLeft();
                        int n3 = ((TextAreaSkin)ProgramEditorLineNumberedTextArea.this.programEditorTextArea.getSkin()).getInsertionPoint(dragEvent.getX() - 5.0 + (double)n2, dragEvent.getY() - 15.0 + (double)n);
                        if (n3 >= 0) {
                            ProgramEditorLineNumberedTextArea.this.programEditorTextArea.positionCaret(n3);
                        }
                    }
                }
                catch (ClassCastException var2_3) {
                    TILogger.logDetail(ProgramEditorLineNumberedTextArea.this.LOG_TAG, "Dragging from non supported source.");
                    return;
                }
            }
        });
        this.programEditorTextArea.setOnDragDropped((EventHandler)new EventHandler<DragEvent>(){

            public void handle(DragEvent dragEvent) {
                Node node;
                Dragboard dragboard;
                String string;
                Node node2 = (Node)dragEvent.getGestureSource();
                if ((node2 == (node = ProgramEditorFactory.getProgramEditorCatalog().getDragableNode()) || ((Parent)node).getChildrenUnmodifiable().contains((Object)node2)) && ProgramEditorFactory.getProgramEditorCatalog().isDragging() && (dragboard = dragEvent.getDragboard()).hasString() && null != (string = dragboard.getString()) && !string.isEmpty()) {
                    ProgramEditorLineNumberedTextArea.this.insertText(string);
                }
            }
        });
        if (PlatformManager.isMac()) {
            this.programEditorTextArea.addEventHandler(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.isMetaDown() && (keyEvent.getCode() == KeyCode.X || keyEvent.getCode() == KeyCode.C || keyEvent.getCode() == KeyCode.V || keyEvent.getCode() == KeyCode.Z)) {
                        keyEvent.consume();
                        ProgramEditorLineNumberedTextArea.this.ignoreNextAction = true;
                    }
                }
            });
        }
    }

    private void updateLineNumbers(String string) {
        int n = 1;
        char c = "\n".charAt(0);
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != c) continue;
            ++n;
        }
        if (n != this.currentLineCount) {
            String string2 = (String)this.programEditorListNumbers.textProperty().get();
            this.programEditorListNumbers.textProperty().set((Object)"");
            if (n > this.currentLineCount) {
                for (int j = this.currentLineCount + 1; j <= n; ++j) {
                    string2 = string2 + "\n" + this.numberWithPadding(j);
                }
            } else {
                string2 = "001";
                for (int j = 2; j <= n; ++j) {
                    string2 = string2 + "\n" + this.numberWithPadding(j);
                }
            }
            this.currentLineCount = n;
            this.programEditorListNumbers.appendText(string2);
        }
    }

    private String numberWithPadding(int n) {
        if (n < 10) {
            return "00" + String.valueOf(n);
        }
        if (n >= 10 && n < 100) {
            return "0" + String.valueOf(n);
        }
        return String.valueOf(n);
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public String getText() {
        return this.programEditorTextArea.getText();
    }

    public Node getTextAreaNode() {
        return this.programEditorTextArea;
    }

    public void setText(String string) {
        this.programEditorTextArea.setText(string);
    }

    public void insertText(String string) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if (clipboard != null) {
            Object object = clipboard.hasString() ? clipboard.getContent(DataFormat.PLAIN_TEXT) : null;
            Object object2 = clipboard.hasFiles() ? clipboard.getContent(DataFormat.FILES) : null;
            Object object3 = clipboard.hasHtml() ? clipboard.getContent(DataFormat.HTML) : null;
            Object object4 = clipboard.hasImage() ? clipboard.getContent(DataFormat.IMAGE) : null;
            Object object5 = clipboard.hasRtf() ? clipboard.getContent(DataFormat.RTF) : null;
            Object object6 = clipboard.hasUrl() ? clipboard.getContent(DataFormat.URL) : null;
            HashMap<DataFormat, Object> hashMap = new HashMap<DataFormat, Object>();
            hashMap.put(DataFormat.PLAIN_TEXT, string);
            clipboard.setContent(hashMap);
            this.paste();
            if (object != null) {
                hashMap.put(DataFormat.PLAIN_TEXT, object);
            }
            if (object2 != null) {
                hashMap.put(DataFormat.FILES, object2);
            }
            if (object3 != null) {
                hashMap.put(DataFormat.HTML, object3);
            }
            if (object4 != null) {
                hashMap.put(DataFormat.IMAGE, object4);
            }
            if (object5 != null) {
                hashMap.put(DataFormat.RTF, object5);
            }
            if (object6 != null) {
                hashMap.put(DataFormat.URL, object6);
            }
            clipboard.setContent(hashMap);
        } else {
            TILogger.logError(this.LOG_TAG, "Cannot access clipboard so cannot insert text from catalog");
        }
    }

    public boolean isUndoable() {
        Skin skin = this.programEditorTextArea.getSkin();
        if (skin != null) {
            BehaviorBase behaviorBase = ((TextInputControlSkin)skin).getBehavior();
            if (behaviorBase != null) {
                return ((TextInputControlBehavior)behaviorBase).canUndo();
            }
            TILogger.logError(this.LOG_TAG, "isUndoable: No behavior yet for programEditorTextArea!");
        } else {
            TILogger.logError(this.LOG_TAG, "isUndoable: No skin yet for programEditorTextArea!");
        }
        return false;
    }

    public boolean isRedoable() {
        Skin skin = this.programEditorTextArea.getSkin();
        if (skin != null) {
            BehaviorBase behaviorBase = ((TextInputControlSkin)skin).getBehavior();
            if (behaviorBase != null) {
                return ((TextInputControlBehavior)behaviorBase).canRedo();
            }
            TILogger.logError(this.LOG_TAG, "isRedoable: No behavior yet for programEditorTextArea!");
        } else {
            TILogger.logError(this.LOG_TAG, "isRedoable: No skin yet for programEditorTextArea!");
        }
        return false;
    }

    public boolean hasSelection() {
        return this.programEditorTextArea.getSelection().getLength() != 0;
    }

    protected void callAction(String string) {
        if (this.ignoreNextAction) {
            this.ignoreNextAction = false;
            return;
        }
        Skin skin = this.programEditorTextArea.getSkin();
        if (skin != null) {
            BehaviorBase behaviorBase = ((TextInputControlSkin)skin).getBehavior();
            if (behaviorBase != null) {
                ((TextInputControlBehavior)behaviorBase).callAction(string);
            } else {
                TILogger.logError(this.LOG_TAG, string + ": No behavior yet for programEditorTextArea!");
            }
        } else {
            TILogger.logError(this.LOG_TAG, string + ": No skin yet for programEditorTextArea!");
        }
    }

    public void undo() {
        this.callAction("Undo");
    }

    public void redo() {
        this.callAction("Redo");
    }

    public void cut() {
        this.callAction("Cut");
    }

    public void copy() {
        this.callAction("Copy");
    }

    public void paste() {
        this.callAction("Paste");
    }

    public void delete() {
        this.programEditorTextArea.deleteText(this.programEditorTextArea.getSelection());
    }

    public void selectAll() {
        this.programEditorTextArea.selectAll();
    }

    public void unselectAll() {
        this.programEditorTextArea.deselect();
    }

    public void addProgramStateChangedListener(ProgramStateChangedListener programStateChangedListener) {
        this.listeners.add(programStateChangedListener);
    }

    public void removeProgramStateChangedListener(ProgramStateChangedListener programStateChangedListener) {
        this.listeners.remove(programStateChangedListener);
    }

    private void fireProgramTextChanged() {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                for (ProgramStateChangedListener programStateChangedListener : ProgramEditorLineNumberedTextArea.this.listeners) {
                    programStateChangedListener.programTextChanged();
                }
            }
        });
    }

    private void fireProgramSelectionChanged() {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                for (ProgramStateChangedListener programStateChangedListener : ProgramEditorLineNumberedTextArea.this.listeners) {
                    programStateChangedListener.programSelectionChanged();
                }
            }
        });
    }

}

