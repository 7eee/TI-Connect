/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Application
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.geometry.Pos
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Button
 *  javafx.scene.control.ComboBox
 *  javafx.scene.layout.StackPane
 *  javafx.scene.layout.VBox
 *  javafx.stage.Stage
 */
package com.ti.et.elg.dataimport.ui.dialog;

import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.DIFactory;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DIDialogTestApp
extends Application {
    private static final String TAG = DIDialogTestApp.class.getSimpleName();

    public static void main(String[] arrstring) {
        DIDialogTestApp.launch((String[])arrstring);
    }

    public void start(Stage stage) {
        stage.setTitle("DIDialogApp");
        UserPropertyManagement.loadUserProperties();
        Locale.setDefault(new Locale("us"));
        ObservableList observableList = FXCollections.observableArrayList((Object[])new Locale[]{new Locale("en"), new Locale("de"), new Locale("es"), new Locale("fr"), new Locale("nl"), new Locale("pt"), new Locale("sv")});
        ComboBox comboBox = new ComboBox(observableList);
        comboBox.setValue((Object)Locale.getDefault());
        comboBox.setMinWidth(150.0);
        comboBox.valueProperty().addListener((ChangeListener)new ChangeListener<Locale>(){

            public void changed(ObservableValue<? extends Locale> observableValue, Locale locale, Locale locale2) {
                TILogger.logInfo(TAG, MessageFormat.format("Locale arg1: {0}", locale));
                TILogger.logInfo(TAG, MessageFormat.format("Locale arg2: {0}", locale2));
                Locale.setDefault(locale2);
            }
        });
        Button button = new Button();
        button.setText("Launch DIDialog");
        button.setMinWidth(150.0);
        button.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                try {
                    DIFactory.configureFactory();
                    DIFactory.getDataImportWizard().importFiles(Arrays.asList(new File("unit/com/ti/et/elg/dataimport/datatest/List_valid.csv")));
                }
                catch (DIException var2_2) {
                    TILogger.logError(TAG, "Error while running DIDialog", var2_2);
                }
            }
        });
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox(10.0);
        vBox.autosize();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add((Object)comboBox);
        vBox.getChildren().add((Object)button);
        stackPane.getChildren().add((Object)vBox);
        stage.setScene(new Scene((Parent)stackPane, 300.0, 250.0));
        stage.show();
    }

}

