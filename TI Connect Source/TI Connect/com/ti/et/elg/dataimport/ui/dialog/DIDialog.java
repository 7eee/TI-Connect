/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.StringProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.ComboBox
 *  javafx.scene.control.Label
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.VBox
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 */
package com.ti.et.elg.dataimport.ui.dialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.customComponents.TIComboBoxImage;
import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.commonUISupport.customComponents.TIPatternTextField;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.utils.CustomComboImage;
import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.DITIDataType;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.model.impl.DIConfig;
import com.ti.et.elg.dataimport.ui.dialog.ADIDialog;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class DIDialog
extends ADIDialog {
    @FXML
    private TIComboBoxImage<CustomComboImage> cmbConversionTypes;
    @FXML
    protected ComboBox<String> cmbColsList;
    @FXML
    protected VBox vboxList;
    @FXML
    protected VBox vboxMatrix;
    @FXML
    protected Label lblSuggestion;
    @FXML
    private Label lblCells;
    @FXML
    protected TIDialogButton butnCancel;
    @FXML
    protected TIDialogButton butnSend;
    @FXML
    protected TIPatternTextField txtRowMatrix;
    @FXML
    protected TIPatternTextField txtColsMatrix;
    @FXML
    private Label lblFileName;
    @FXML
    private BorderPane paneContents;
    private final Stage dialogStage = new Stage();
    private Image warningImage;
    private boolean dialogCancelled = true;
    protected int conversionSelected;
    private File file;
    private IDIConfig config;
    private static final String LOG_TAG = DIDialog.class.getSimpleName();

    private final void init() {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/dataimport/importDataDialog.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Parent parent = (Parent)fXMLLoader.load();
            Scene scene = new Scene(parent);
            this.dialogStage.setResizable(false);
            this.dialogStage.setScene(scene);
            if (!this.dialogStage.getModality().equals((Object)Modality.APPLICATION_MODAL)) {
                this.dialogStage.initModality(Modality.APPLICATION_MODAL);
            }
            this.dialogStage.getScene().getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
            this.addDimensionsListener();
            this.initConvesionTypes();
            this.initDefaultValues();
            this.setTittle();
            this.butnSend.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    DIDialog.this.dialogCancelled = false;
                    DIDialog.this.setConvesionType();
                    DIDialog.this.dialogStage.close();
                }
            });
            this.butnCancel.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    DIDialog.this.dialogCancelled = true;
                    DIDialog.this.dialogStage.close();
                }
            });
            scene.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        DIDialog.this.dialogCancelled = true;
                        DIDialog.this.dialogStage.close();
                    }
                }
            });
            this.warningImage = new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/send to hh dialog_warning.png").toExternalForm());
            this.setFocusToButtons();
            if (PlatformManager.isWindows()) {
                this.dialogStage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
            }
        }
        catch (IOException var1_2) {
            TILogger.logError(LOG_TAG, "showAndWait", var1_2);
        }
    }

    private final void initDefaultValues() {
        this.initDimensionValues();
        this.lblFileName.setText(this.file.getName());
        this.cmbColsList.getItems().addAll(DEFAULT_VALUES_LISTS);
        this.switchConversion(2);
    }

    private final void initDimensionValues() {
        this.removeStyleClass();
        this.lblSuggestion.getStyleClass().add((Object)"overlay-Label-Header-Text-Bold");
        this.txtRowMatrix.setCustomNamePattern("^([0-9]|[1-9][0-9])$");
        this.txtRowMatrix.setText("2");
        this.txtColsMatrix.setCustomNamePattern("^([0-9]|[1-9][0-9])$");
        this.txtColsMatrix.setText("2");
        this.cmbColsList.setValue((Object)"1");
        this.lblCells.setText(String.valueOf(this.getInt(this.txtRowMatrix.getText()) * this.getInt(this.txtColsMatrix.getText())));
    }

    private final ObservableList<CustomComboImage> getConversionTypesItems() {
        ObservableList observableList = FXCollections.observableArrayList();
        Image image = new Image(this.getClass().getResourceAsStream("/com/ti/et/elg/commonUISupport/icons/list.png"));
        Image image2 = new Image(this.getClass().getResourceAsStream("/com/ti/et/elg/commonUISupport/icons/matrix.png"));
        observableList.addAll((Object[])new CustomComboImage[]{new CustomComboImage(CommonUISupportResourceBundle.BUNDLE.getString("importDataDialog.conversiontype.lists"), new ImageView(image), 2), new CustomComboImage(CommonUISupportResourceBundle.BUNDLE.getString("importDataDialog.conversiontype.matrix"), new ImageView(image2), 1)});
        return observableList;
    }

    private final EventHandler<ActionEvent> changeListenerCombo() {
        return new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                DIDialog.this.initDimensionValues();
                switch (((CustomComboImage)DIDialog.this.cmbConversionTypes.getSelectionModel().getSelectedItem()).getId()) {
                    case 1: {
                        DIDialog.this.switchConversion(1);
                        break;
                    }
                    default: {
                        DIDialog.this.switchConversion(2);
                    }
                }
            }
        };
    }

    private final void switchConversion(int n) {
        this.setConversionSelected(n);
        if (this.conversionSelected == 2) {
            this.vboxList.setVisible(true);
            this.vboxMatrix.setVisible(false);
            this.lblSuggestion.setText(CommonUISupportResourceBundle.BUNDLE.getString("importDataDialog.restrictions.lists"));
        } else if (this.conversionSelected == 1) {
            this.vboxMatrix.setVisible(true);
            this.vboxList.setVisible(false);
            this.lblSuggestion.setText(CommonUISupportResourceBundle.BUNDLE.getString("importDataDialog.restrictions.matrix"));
        }
        this.setFocusToButtons();
    }

    private final void setConvesionType() {
        if (this.conversionSelected == 2) {
            int n = this.getInt((String)this.cmbColsList.getSelectionModel().getSelectedItem());
            this.config.setNumberOfCols(n);
            this.config.setNumberOfRows(0);
            this.config.setType(DITIDataType.LIST);
        } else if (this.conversionSelected == 1) {
            int n = this.getInt(this.txtColsMatrix.getText());
            int n2 = this.getInt(this.txtRowMatrix.getText());
            this.config.setNumberOfCols(n);
            this.config.setNumberOfRows(n2);
            this.config.setType(DITIDataType.MATRIX);
        }
    }

    private final void initConvesionTypes() {
        this.cmbConversionTypes.setCustomItems(this.getConversionTypesItems());
        this.cmbConversionTypes.getSelectionModel().select(0);
        this.cmbConversionTypes.setOnAction(this.changeListenerCombo());
    }

    private final void setTittle() {
        String string = CommonUISupportResourceBundle.BUNDLE.getString("importDataDialog.title");
        this.dialogStage.setTitle(string);
    }

    public final void setFocusToButtons() {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                if (DIDialog.this.butnSend.isDisabled()) {
                    DIDialog.this.butnCancel.requestFocus();
                } else {
                    DIDialog.this.butnSend.requestFocus();
                }
            }
        });
    }

    private final void addDimensionsListener() {
        this.txtRowMatrix.textProperty().addListener((ChangeListener)new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                Integer n = DIDialog.this.getInt(DIDialog.this.txtRowMatrix.getText()) * DIDialog.this.getInt(DIDialog.this.txtColsMatrix.getText());
                DIDialog.this.lblCells.setText(n.toString());
                DIDialog.this.notifyChangeDimensions(n);
            }
        });
        this.txtColsMatrix.textProperty().addListener((ChangeListener)new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                Integer n = DIDialog.this.getInt(DIDialog.this.txtRowMatrix.getText()) * DIDialog.this.getInt(DIDialog.this.txtColsMatrix.getText());
                DIDialog.this.lblCells.setText(n.toString());
                DIDialog.this.notifyChangeDimensions(n);
            }
        });
    }

    private final void notifyChangeDimensions(int n) {
        this.removeStyleClass();
        if (n == 0) {
            this.setWarningMode(false);
            this.butnSend.setDisable(true);
        } else if (this.isMaxCells(n)) {
            this.setWarningMode(true);
            this.butnSend.setDisable(true);
        } else {
            this.setWarningMode(false);
            this.butnSend.setDisable(false);
        }
    }

    private final void setWarningMode(boolean bl) {
        if (bl) {
            this.lblSuggestion.getStyleClass().add((Object)"overlay-Label-Error-Text-Bold");
            this.lblCells.getStyleClass().add((Object)"overlay-Label-Error-Number-Bold");
            this.lblSuggestion.setGraphic((Node)new ImageView(this.warningImage));
        } else {
            this.lblSuggestion.getStyleClass().add((Object)"overlay-Label-Header-Text-Bold");
            this.lblSuggestion.setGraphic(null);
        }
    }

    private final void removeStyleClass() {
        this.lblCells.getStyleClass().removeAll((Object[])new String[]{"overlay-Label-Error-Number-Bold"});
        this.lblSuggestion.getStyleClass().removeAll((Object[])new String[]{"overlay-Label-Header-Text-Bold", "overlay-Label-Error-Text-Bold"});
    }

    private final boolean isMaxCells(int n) {
        return n > 400;
    }

    @Override
    public final IDIConfig showAndWait(List<Map<String, String>> list) throws DIException {
        if (this.config == null) {
            throw new DIException("The object DIConfig is null. The method init() must be excecuted before");
        }
        if (PromptDialog.arePromptsSupressed()) {
            this.dialogCancelled = false;
            return null;
        }
        this.init();
        this.dialogStage.showAndWait();
        return this.config;
    }

    public final int getInt(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException var2_2) {
            return 0;
        }
    }

    public final int getConversionSelected() {
        return this.conversionSelected;
    }

    public final void setConversionSelected(int n) {
        this.conversionSelected = n;
    }

    @Override
    public final boolean isDialogCancelled() {
        return this.dialogCancelled;
    }

    public final DITIDataType getType() {
        return this.config.getType();
    }

    public final int getNumberOfCols() {
        return this.config.getNumberOfCols();
    }

    public final int getNumberOfRows() {
        return this.config.getNumberOfRows();
    }

    public final void setType(DITIDataType dITIDataType) {
        this.config.setType(dITIDataType);
    }

    public final void setNumberOfCols(int n) {
        this.config.setNumberOfCols(n);
    }

    public final void setNumberOfRows(int n) {
        this.config.setNumberOfRows(n);
    }

    @Override
    public void init(File file) throws DIException, DIConverterException {
        if (file == null) {
            throw new DIException("Given file cannot be null");
        }
        this.file = file;
        this.dialogCancelled = true;
        this.config = new DIConfig();
        this.config.setScrFilePath(this.file.getAbsolutePath());
    }

}

