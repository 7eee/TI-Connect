/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.sun.javafx.scene.control.skin.LabelSkin
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.ReadOnlyBooleanProperty
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.geometry.Insets
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.control.Control
 *  javafx.scene.control.Label
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.control.Tab
 *  javafx.scene.control.TabPane
 *  javafx.scene.image.Image
 *  javafx.scene.input.Clipboard
 *  javafx.scene.input.DragEvent
 *  javafx.scene.input.Dragboard
 *  javafx.scene.input.TransferMode
 *  javafx.scene.layout.StackPane
 *  javafx.scene.layout.VBox
 */
package com.ti.et.elg.programEditor;

import com.sun.javafx.scene.control.skin.LabelSkin;
import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIButton;
import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameManager;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.translator.TIProgramDataHolder;
import com.ti.et.elg.programEditor.ProgramEditorResourceBundle;
import com.ti.et.elg.programEditor.ProgramEditorSingleProgram;
import com.ti.et.elg.programEditor.actions.CreateProgramAction;
import com.ti.et.elg.programEditor.actions.OpenProgramAction;
import com.ti.et.elg.programEditor.actions.ProgramEditorActionManager;
import com.ti.et.elg.programEditor.actions.SaveAsProgramAction;
import com.ti.et.elg.programEditor.exports.ProgramEditorContentsInterface;
import com.ti.et.elg.programEditor.exports.ProgramStateChangedListener;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ProgramEditorContents
implements ProgramEditorContentsInterface,
ProgramStateChangedListener {
    @FXML
    protected TabPane tabPaneProgramEditor;
    @FXML
    private OverlayStackPaneBase stackPane;
    @FXML
    private VBox overlayNoProgram;
    @FXML
    private TIButton butnProgramEditorAdd;
    @FXML
    private TIButton butnProgramEditorOpen;
    private static final String LOG_TAG = ProgramEditorContents.class.getSimpleName();
    private Node rootNode;
    protected ProgramEditorSingleProgram currentProgram = null;
    private int tempProgramCntr = 1;
    protected Tab currentTab = null;
    private static final String TEMP_PROG_VAR_NAME = "PROG";
    private boolean cancelCloseAll = false;
    private Map<Node, ProgramEditorSingleProgram> mapNode2SingleProgram = new HashMap<Node, ProgramEditorSingleProgram>();
    private Node tabPaneNode;
    private Node varNameNode;
    private Node protectedStateNode;
    private Node textAreaNode;
    private Node btnAddNode;
    private Node btnOpenNode;

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("programEditorContents.fxml"));
            fXMLLoader.setResources((ResourceBundle)ProgramEditorResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
        }
        catch (IOException var1_2) {
            TILogger.logError("ProgramEditorContents", "init", var1_2);
        }
        this.addTabSelectionHandler();
        this.addDnDHandler();
        this.initOverlay();
    }

    private void addTabCloseHandler(Tab tab) {
        tab.setOnClosed((EventHandler)new TabCloseEventHandler(tab));
    }

    private void addTabSelectionHandler() {
        this.tabPaneProgramEditor.getSelectionModel().selectedItemProperty().addListener((ChangeListener)new ChangeListener<Tab>(){

            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                if (tab2 != null) {
                    Node node = tab2.getContent();
                    ProgramEditorContents.this.setCurrentProgram((ProgramEditorSingleProgram)ProgramEditorContents.this.mapNode2SingleProgram.get((Object)node));
                } else {
                    ProgramEditorContents.this.setCurrentProgram(null);
                }
                ProgramEditorContents.this.currentTab = tab2;
                if (ProgramEditorFactory.getProgramEditor() != null) {
                    ProgramEditorFactory.getProgramEditor().updateFocusableNodes();
                }
            }
        });
    }

    private void addDnDHandler() {
        this.rootNode.setOnDragDropped((EventHandler)new EventHandler<DragEvent>(){

            public void handle(DragEvent dragEvent) {
                Dragboard dragboard = dragEvent.getDragboard();
                if (dragboard.hasFiles()) {
                    final List list = dragboard.getFiles();
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            for (File file : list) {
                                ProgramEditorContents.this.openProgram(file);
                            }
                        }
                    });
                    dragEvent.setDropCompleted(true);
                    dragEvent.consume();
                }
            }

        });
        this.rootNode.setOnDragOver((EventHandler)new EventHandler<DragEvent>(){

            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource() != ProgramEditorContents.this.rootNode && dragEvent.getDragboard().hasFiles()) {
                    dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.COPY});
                }
                dragEvent.consume();
            }
        });
    }

    @Override
    public TIStatus saveProgram(File file, boolean bl) {
        String string = this.getProgramAsString();
        StringBuilder stringBuilder = new StringBuilder();
        TIStatus tIStatus = ProgramEditorContents.saveProgramToDisk(string, file, this.getProgramVarName(), this.getProgramProtectedState(), stringBuilder);
        if (!tIStatus.isFailure() && bl) {
            this.currentProgram.setSrcFilePath(stringBuilder.toString());
            this.updateProgramTabName();
            this.makeDirty(false);
            this.currentProgram.setUnsavedNewFile(false);
            ProgramEditorActionManager.getInstance().programStateChanged();
        }
        TILogger.logDetail(LOG_TAG, "saveProgram errCode: " + tIStatus.getStatusCode());
        return tIStatus;
    }

    @Override
    public TIStatus saveTempProgram(StringBuilder stringBuilder) {
        File file;
        try {
            file = File.createTempFile("ProgramEditor", "");
        }
        catch (IOException var3_3) {
            return new TIStatus(-7);
        }
        file.delete();
        if (!file.mkdirs()) {
            return new TIStatus(-1);
        }
        File file2 = new File(this.currentProgram.getSrcFilePath());
        File file3 = new File(file, file2.getName());
        stringBuilder.setLength(0);
        stringBuilder.append(file3.getAbsolutePath());
        file.deleteOnExit();
        file3.deleteOnExit();
        return this.saveProgram(file3, false);
    }

    protected static TIProgramDataHolder openProgramFromFile(File file) {
        return ProgramEditorContents.openProgramFromFile(file, false);
    }

    protected static TIProgramDataHolder openProgramFromFile(File file, boolean bl) {
        TIProgramDataHolder tIProgramDataHolder = null;
        if (file.isFile() && file.canRead()) {
            TIVar tIVar = ProgramEditorFactory.getProgramDataProcessing().makeProgramVarFromFile(file, bl);
            if (tIVar != null) {
                tIProgramDataHolder = ProgramEditorContents.createProgramHolderFromVar(tIVar);
            }
        } else {
            tIProgramDataHolder = new TIProgramDataHolder();
            tIProgramDataHolder.setTIStatus(new TIStatus(-1));
        }
        return tIProgramDataHolder;
    }

    static TIProgramDataHolder createProgramHolderFromVar(TIVar tIVar) {
        TIProgramDataHolder tIProgramDataHolder = new TIProgramDataHolder();
        if (tIVar.getOwnerCalculatorID() == -1) {
            tIProgramDataHolder.setTIStatus(new TIStatus(101));
        } else if (tIVar.isBadCheckSum()) {
            tIProgramDataHolder.setTIStatus(new TIStatus(114));
        } else if (tIVar.isACorruptedVar()) {
            tIProgramDataHolder.setTIStatus(new TIStatus(113));
        } else if (tIVar.getVarType() != 5 && tIVar.getVarType() != 6) {
            tIProgramDataHolder.setTIStatus(new TIStatus(111));
        } else {
            tIProgramDataHolder = ProgramEditorContents.getProgramFromTIVar(tIVar);
        }
        return tIProgramDataHolder;
    }

    @Override
    public void openProgram(File file) {
        TIProgramDataHolder tIProgramDataHolder = ProgramEditorContents.openProgramFromFile(file);
        if (!(!tIProgramDataHolder.getTIStatus().isFailure() || tIProgramDataHolder.getTIStatus().getStatusCode() != -1 || file.isFile() && file.canRead())) {
            try {
                file = new File(URLDecoder.decode(file.getAbsolutePath(), "UTF-8"));
                tIProgramDataHolder = ProgramEditorContents.openProgramFromFile(file);
            }
            catch (Exception var3_3) {
                TILogger.logError(LOG_TAG, "openProgram", var3_3);
            }
        }
        if (tIProgramDataHolder.getTIStatus().isFailure() && tIProgramDataHolder.getTIStatus().getStatusCode() == 114) {
            int n = this.promptForIgnoreFileChecksumConfirmation(file);
            if (n == 1) {
                tIProgramDataHolder = ProgramEditorContents.openProgramFromFile(file, true);
            } else {
                return;
            }
        }
        this.openCreateProgramFromDataHolder(tIProgramDataHolder, file, false);
    }

    @Override
    public void openProgram(TIVar tIVar) {
        if (tIVar != null && (tIVar.getVarType() == 5 || tIVar.getVarType() == 6)) {
            TIProgramDataHolder tIProgramDataHolder = ProgramEditorContents.createProgramHolderFromVar(tIVar);
            String string = UserPropertyManagement.getUserProperty("ProgramEditor.lastOpenOrSaveFolder.path");
            string = null == string ? PlatformManager.getDocsFolder() : string + File.separator;
            String string2 = tIVar.getHostFileNameFromDeviceFileName();
            File file = new File(string + string2);
            this.openCreateProgramFromDataHolder(tIProgramDataHolder, file, true);
        }
    }

    public void openCreateProgramFromDataHolder(TIProgramDataHolder tIProgramDataHolder, File file, final boolean bl) {
        TIStatus tIStatus = null;
        if (tIProgramDataHolder != null && file != null) {
            tIStatus = tIProgramDataHolder.getTIStatus();
            if (!tIStatus.isFailure() && !(tIStatus = this.createNewProgram(file)).isFailure()) {
                this.setProgramAsString(tIProgramDataHolder.getProgramAsString());
                this.setProgramVarName(tIProgramDataHolder.getProgramTIVar().getDeviceFileName());
                this.setProgramProtectedState(tIProgramDataHolder.getProgramTIVar().getVarType() != 5);
                if (!tIStatus.isFailure()) {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            ProgramEditorContents.this.makeDirty(bl);
                            ProgramEditorActionManager.getInstance().programStateChanged();
                        }
                    });
                    this.currentProgram.setUnsavedNewFile(bl);
                }
            }
        } else {
            tIStatus = new TIStatus(15);
        }
        if (tIStatus.isFailure()) {
            String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.DATA_EDITING, tIStatus, null, file.getName());
            TILogger.logError(LOG_TAG, "openCreateProgramFromDataHolder err: " + tIStatus.getStatusCode());
            PromptDialog.showUserError(arrstring[0], arrstring[1]);
        }
    }

    @Override
    public TIStatus createNewProgram(File file) {
        if (this.checkIfTabAlreadyPresent(file)) {
            return new TIStatus(112);
        }
        Tab tab = new Tab(file.getName());
        this.addCloseButton(tab);
        this.tabPaneProgramEditor.getTabs().add((Object)tab);
        ProgramEditorSingleProgram programEditorSingleProgram = new ProgramEditorSingleProgram();
        programEditorSingleProgram.init();
        String string = this.tempProgramCntr < 10 ? "0" + String.valueOf(this.tempProgramCntr) : String.valueOf(this.tempProgramCntr);
        programEditorSingleProgram.setVarNameText("PROG" + string);
        ++this.tempProgramCntr;
        programEditorSingleProgram.setSrcFilePath(file.getAbsolutePath());
        tab.setContent(programEditorSingleProgram.getRootNode());
        if (this.mapNode2SingleProgram.isEmpty()) {
            this.removeOverlay();
        }
        this.mapNode2SingleProgram.put(programEditorSingleProgram.getRootNode(), programEditorSingleProgram);
        this.setCurrentProgram(programEditorSingleProgram);
        this.tabPaneProgramEditor.getSelectionModel().select((Object)tab);
        this.addTabCloseHandler(tab);
        if (ProgramEditorFactory.getProgramEditor() != null) {
            ProgramEditorFactory.getProgramEditor().updateFocusableNodes();
        }
        return new TIStatus(0);
    }

    private void addCloseButton(final Tab tab) {
        tab.setClosable(false);
        final StackPane stackPane = new StackPane(){

            protected void layoutChildren() {
                super.layoutChildren();
                ((Label)((LabelSkin)this.getParent()).getSkinnable()).setStyle("-fx-content-display:right");
            }
        };
        stackPane.setPadding(new Insets(0.0, 0.0, 0.0, 15.0));
        stackPane.getStyleClass().setAll((Object[])new String[]{"tab-close-button"});
        stackPane.visibleProperty().bind((ObservableValue)tab.selectedProperty());
        stackPane.setOnMouseReleased((EventHandler)new EventHandler<Event>(){

            public void handle(Event event) {
                ProgramEditorContents.this.closeProgram();
            }
        });
        tab.selectedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                if (bl2.booleanValue()) {
                    tab.setGraphic((Node)stackPane);
                } else {
                    tab.setGraphic(null);
                }
            }
        });
    }

    private boolean checkIfTabAlreadyPresent(File file) {
        if (file != null) {
            for (Tab tab : this.tabPaneProgramEditor.getTabs()) {
                ProgramEditorSingleProgram programEditorSingleProgram = this.mapNode2SingleProgram.get((Object)tab.getContent());
                if (programEditorSingleProgram == null || !programEditorSingleProgram.getSrcFilePath().equals(file.getAbsolutePath())) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public void closeAllPrograms() {
        ArrayList arrayList = new ArrayList(this.tabPaneProgramEditor.getTabs());
        for (Tab tab : arrayList) {
            this.tabPaneProgramEditor.getSelectionModel().select((Object)tab);
            this.closeProgram();
            if (!this.cancelCloseAll) continue;
            break;
        }
    }

    @Override
    public void closeProgram() {
        if (this.currentTab != null) {
            this.cancelCloseAll = false;
            int n = this.promptForUnsavedProgram(this.currentTab.getContent());
            if (n == 0) {
                this.cancelCloseAll = true;
            } else {
                if (n == 2 && !SaveAsProgramAction.performSave(true)) {
                    this.cancelCloseAll = true;
                    return;
                }
                EventHandler eventHandler = this.currentTab.getOnClosed();
                if (null != eventHandler) {
                    eventHandler.handle(null);
                }
            }
        }
    }

    @Override
    public void insertText(String string) {
        if (this.currentProgram == null) {
            TILogger.logError(LOG_TAG, "Cannot insert text into program because there is NO current program");
        } else {
            this.currentProgram.insertText(string);
        }
    }

    @Override
    public boolean isThereACurrentProgram() {
        return this.currentProgram != null;
    }

    @Override
    public boolean isRedoable() {
        if (this.currentProgram == null) {
            return false;
        }
        return this.currentProgram.isRedoable();
    }

    @Override
    public boolean isUndoable() {
        if (this.currentProgram == null) {
            return false;
        }
        return this.currentProgram.isUndoable();
    }

    @Override
    public boolean hasSelection() {
        if (this.currentProgram == null) {
            return false;
        }
        return this.currentProgram.hasSelection();
    }

    @Override
    public boolean isPasteable() {
        if (this.currentProgram == null) {
            return false;
        }
        return Clipboard.getSystemClipboard().hasString();
    }

    @Override
    public boolean isDirty() {
        if (this.currentProgram == null) {
            return false;
        }
        return this.currentProgram.isDirty();
    }

    @Override
    public String getFilePath() {
        if (this.currentProgram == null) {
            return null;
        }
        return this.currentProgram.getSrcFilePath();
    }

    @Override
    public boolean unsavedNewFile() {
        if (this.currentProgram == null) {
            return false;
        }
        return this.currentProgram.unsavedNewFile();
    }

    @Override
    public void redo() {
        if (this.currentProgram != null) {
            this.currentProgram.redo();
        }
    }

    @Override
    public void undo() {
        if (this.currentProgram != null) {
            this.currentProgram.undo();
        }
    }

    @Override
    public void copy() {
        if (this.currentProgram != null) {
            this.currentProgram.copy();
        }
    }

    @Override
    public void cut() {
        if (this.currentProgram != null) {
            this.currentProgram.cut();
        }
    }

    @Override
    public void paste() {
        if (this.currentProgram != null) {
            this.currentProgram.paste();
        }
    }

    @Override
    public void selectAll() {
        if (this.currentProgram != null) {
            this.currentProgram.selectAll();
        }
    }

    @Override
    public void unselectAll() {
        if (this.currentProgram != null) {
            this.currentProgram.unselectAll();
        }
    }

    @Override
    public void delete() {
        if (this.currentProgram != null) {
            this.currentProgram.delete();
        }
    }

    protected void makeDirty(boolean bl) {
        if (this.currentProgram != null) {
            File file = new File(this.currentProgram.getSrcFilePath());
            if (!bl && file != null && this.currentProgram.isDirty()) {
                this.currentProgram.setDirty(bl);
                this.currentTab.setText(file.getName());
            } else if (bl && file != null && !this.currentProgram.isDirty()) {
                this.currentProgram.setDirty(true);
                this.currentTab.setText("*" + file.getName());
            }
        }
    }

    protected void updateProgramTabName() {
        File file;
        if (this.currentProgram != null && this.currentTab != null && (file = new File(this.currentProgram.getSrcFilePath())) != null) {
            this.currentTab.setText(file.getName());
        }
    }

    private void setCurrentProgram(ProgramEditorSingleProgram programEditorSingleProgram) {
        if (this.currentProgram != programEditorSingleProgram) {
            if (this.currentProgram != null) {
                this.currentProgram.removeProgramStateChangedListener(this);
            }
            this.currentProgram = programEditorSingleProgram;
            if (this.currentProgram != null) {
                this.currentProgram.addProgramStateChangedListener(this);
            }
            ProgramEditorFactory.getProgramEditorCatalog().setDisabled(programEditorSingleProgram == null);
            ProgramEditorActionManager.getInstance().setCurrentProgram(programEditorSingleProgram != null);
        }
    }

    private void initOverlay() {
        this.butnProgramEditorAdd.setAction(CreateProgramAction.getInstance());
        this.butnProgramEditorOpen.setAction(OpenProgramAction.getInstance());
    }

    private void addOverlay() {
        this.stackPane.getChildren().add((Object)this.overlayNoProgram);
    }

    private void removeOverlay() {
        this.stackPane.getChildren().remove((Object)this.overlayNoProgram);
    }

    @Override
    public void programTextChanged() {
        this.makeDirty(true);
        ProgramEditorActionManager.getInstance().programStateChanged();
    }

    @Override
    public void programPropertiesChanged() {
        this.makeDirty(true);
        ProgramEditorActionManager.getInstance().programStateChanged();
    }

    @Override
    public void programSelectionChanged() {
        ProgramEditorActionManager.getInstance().programStateChanged();
    }

    protected static TIStatus saveProgramToDisk(String string, File file, String string2, boolean bl, StringBuilder stringBuilder) {
        TIStatus tIStatus;
        TIProgramDataHolder tIProgramDataHolder = ProgramEditorContents.saveProgramToTIVar(string);
        if (tIProgramDataHolder.getTIStatus().getStatusCode() != 0) {
            return tIProgramDataHolder.getTIStatus();
        }
        TIVar tIVar = tIProgramDataHolder.getProgramTIVar();
        if (tIVar != null && file != null) {
            tIVar.setHostFile(file);
            if (stringBuilder != null) {
                stringBuilder.append(tIVar.getHostFile().getAbsolutePath());
            }
            if (!VarNameManager.isCustomNameValid(5, string2)) {
                return new TIStatus(57);
            }
            byte[] arrby = null;
            try {
                arrby = string2.toUpperCase().getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException var9_8) {
                TILogger.logError(LOG_TAG, "saveProgramToDisk", var9_8);
            }
            if (arrby != null) {
                tIVar.setObjectName(arrby);
            }
            int n = bl ? 6 : 5;
            tIVar.setVarType(n);
            byte[] arrby2 = tIVar.frameDataWithDataFormat();
            tIStatus = tIVar.saveToDisk(arrby2);
            arrby2 = null;
        } else {
            tIStatus = new TIStatus(-5);
        }
        return tIStatus;
    }

    protected static TIProgramDataHolder saveProgramToTIVar(String string) {
        TIProgramDataHolder tIProgramDataHolder = ProgramEditorFactory.getProgramDataProcessing().tokenizeProgram(string);
        if (tIProgramDataHolder.getTIStatus().getStatusCode() == 0) {
            TIVar tIVar = ProgramEditorFactory.getProgramDataProcessing().makeProgramVar(tIProgramDataHolder);
            tIProgramDataHolder.setProgramTIVar(tIVar);
        }
        return tIProgramDataHolder;
    }

    protected String getProgramAsString() {
        if (this.currentProgram == null) {
            TILogger.logError(LOG_TAG, "Cannot get program text because there is NO current program");
            return "";
        }
        return this.currentProgram.getText();
    }

    protected void setProgramAsString(String string) {
        if (this.currentProgram == null) {
            TILogger.logError(LOG_TAG, "Cannot set program text because there is NO current program");
        } else {
            this.currentProgram.setText(string);
        }
    }

    protected String getProgramVarName() {
        if (this.currentProgram == null) {
            TILogger.logError(LOG_TAG, "Cannot get program variable name property because there is NO current program");
            return "";
        }
        return this.currentProgram.getVarNameText();
    }

    protected void setProgramVarName(String string) {
        if (this.currentProgram == null) {
            TILogger.logError(LOG_TAG, "Cannot set program varialbe name property because there is NO current program");
        } else {
            this.currentProgram.setVarNameText(string);
        }
    }

    protected boolean getProgramProtectedState() {
        if (this.currentProgram == null) {
            TILogger.logError(LOG_TAG, "Cannot set program protected state property because there is NO current program");
            return false;
        }
        return this.currentProgram.getProtectedState();
    }

    protected void setProgramProtectedState(boolean bl) {
        if (this.currentProgram == null) {
            TILogger.logError(LOG_TAG, "Cannot set program protected state property because there is NO current program");
        } else {
            this.currentProgram.setProtectedState(bl);
        }
    }

    protected static TIProgramDataHolder getProgramFromTIVar(TIVar tIVar) {
        TIProgramDataHolder tIProgramDataHolder = ProgramEditorFactory.getProgramDataProcessing().detokenizeProgram(tIVar.getData());
        if (tIProgramDataHolder.getTIStatus().getStatusCode() == 0) {
            tIProgramDataHolder.setProgramTIVar(tIVar);
        }
        return tIProgramDataHolder;
    }

    protected int promptForUnsavedProgram(Node node) {
        int n = -1;
        ProgramEditorSingleProgram programEditorSingleProgram = this.mapNode2SingleProgram.get((Object)node);
        if (programEditorSingleProgram != null && programEditorSingleProgram.isDirty()) {
            String[] arrstring = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("No"), "butnNo", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedProgram.SaveButton"), "butnSave"};
            File file = new File(programEditorSingleProgram.getSrcFilePath());
            String string = MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedProgram.Message"), file.getName());
            n = PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedProgram.Title"), string, "dlgSaveUnsavedProgram", arrstring, new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
        }
        return n;
    }

    protected int promptForIgnoreFileChecksumConfirmation(File file) {
        int n = -1;
        if (file != null) {
            String[] arrstring = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Skip"), "butnSkip", CommonUISupportResourceBundle.BUNDLE.getString("OpenAnyway"), "butnOpenAnyway"};
            String string = MessageFormat.format(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.IgnoreFileChecksum.Message"), file.getName());
            n = PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.IgnoreFileChecksum.Title"), string, "dlgIgnoreProgramChecksum", arrstring, new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
        }
        return n;
    }

    @Override
    public boolean hasUnsavedItems() {
        ArrayList arrayList = new ArrayList(this.tabPaneProgramEditor.getTabs());
        for (Tab tab : arrayList) {
            if (!this.mapNode2SingleProgram.get((Object)tab.getContent()).isDirty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public Node[] getFocusableNodes() {
        return new Node[]{this.btnAddNode, this.btnOpenNode, this.tabPaneNode, this.varNameNode, this.protectedStateNode, this.textAreaNode};
    }

    @Override
    public void updateFocusableNodes() {
        if (this.currentProgram == null) {
            this.btnAddNode = this.butnProgramEditorAdd;
            this.btnOpenNode = this.butnProgramEditorOpen;
            this.tabPaneNode = null;
            this.varNameNode = null;
            this.protectedStateNode = null;
            this.textAreaNode = null;
        } else {
            this.btnAddNode = null;
            this.btnOpenNode = null;
            this.tabPaneNode = this.tabPaneProgramEditor;
            this.varNameNode = this.currentProgram.getVarNameNode();
            this.protectedStateNode = this.currentProgram.getProtectedStateNode();
            this.textAreaNode = this.currentProgram.getTextAreaNode();
        }
    }

    @Override
    public OverlayStackPaneBase getStackPane() {
        return this.stackPane;
    }

    class TabCloseEventHandler
    implements EventHandler<Event> {
        private Tab tab;

        public TabCloseEventHandler(Tab tab) {
            this.tab = tab;
        }

        public void handle(Event event) {
            if (this.tab != null) {
                Node node = this.tab.getContent();
                ProgramEditorContents.this.mapNode2SingleProgram.remove((Object)node);
                if (ProgramEditorContents.this.mapNode2SingleProgram.isEmpty()) {
                    ProgramEditorContents.this.addOverlay();
                }
                this.tab.setDisable(true);
                ProgramEditorContents.this.tabPaneProgramEditor.getTabs().remove((Object)this.tab);
            }
        }
    }

}

