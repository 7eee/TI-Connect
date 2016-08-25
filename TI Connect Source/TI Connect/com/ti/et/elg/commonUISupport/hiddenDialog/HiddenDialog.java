/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.scene.Group
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 *  javafx.stage.Window
 */
package com.ti.et.elg.commonUISupport.hiddenDialog;

import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class HiddenDialog {
    public static Stage createPixelOffScreenDialog(Stage stage) {
        Stage stage2 = new Stage();
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initOwner((Window)stage);
        stage2.initStyle(StageStyle.UNDECORATED);
        Group group = new Group();
        Scene scene = new Scene((Parent)group, 1.0, 1.0);
        scene.getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
        stage2.setScene(scene);
        stage2.setResizable(false);
        stage2.setX(35000.0);
        stage2.setY(35000.0);
        return stage2;
    }
}

