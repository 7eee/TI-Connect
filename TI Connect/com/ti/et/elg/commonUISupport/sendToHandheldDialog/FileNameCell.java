/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.value.ObservableValue
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.Label
 *  javafx.scene.control.TableCell
 *  javafx.scene.image.ImageView
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.imageUtils.TIConvertedImageVarMultiplexer;
import com.ti.et.elg.commonUISupport.imageUtils.TIVarMultiplexer;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIVarHolder;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarTableData;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;

public class FileNameCell
extends TableCell<VarTableData, TIVarHolder> {
    private Node rootNode;
    @FXML
    Label lblFileName;
    @FXML
    ImageView imgWarningIcon;

    protected void updateItem(TIVarHolder tIVarHolder, boolean bl) {
        super.updateItem((Object)tIVarHolder, bl);
        if (tIVarHolder != null) {
            try {
                String string;
                int n;
                if (this.rootNode == null) {
                    string = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/commonUISupport/sendToHandheldDialog/fileNameCell.fxml"));
                    string.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
                    string.setController((Object)this);
                    this.rootNode = (Node)string.load();
                }
                string = tIVarHolder.getTIVar().getHostFile().getName();
                if (tIVarHolder.getTIVarMux() instanceof TIConvertedImageVarMultiplexer && (n = string.lastIndexOf(".8ca")) > 0) {
                    string = string.substring(0, n);
                }
                this.lblFileName.setText(string);
                this.imgWarningIcon.visibleProperty().bind((ObservableValue)tIVarHolder.duplicatedProperty());
                this.setGraphic(this.rootNode);
            }
            catch (IOException var3_4) {
                TILogger.logError("SendToHandheldDialog", "showAndWait", var3_4);
            }
        }
    }
}

