/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.scene.control.TreeItem
 */
package com.ti.et.elg.commonUISupport.catalog;

import com.ti.et.elg.commonUISupport.catalog.trees.TIXMLSimpleReader;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.InputStream;
import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public abstract class TICatalogBase {
    protected ObservableList<TreeItem<String>> allCatalogs = FXCollections.observableArrayList();
    protected TIResourceBundle[] resourceBundles;
    private static final String TAG = TICatalogBase.class.getSimpleName();

    public boolean loadInfoFromFile(InputStream inputStream) {
        if (null == inputStream) {
            TILogger.logError(TAG, "File is invalid: " + inputStream);
            return false;
        }
        ObservableList<TreeItem<String>> observableList = TIXMLSimpleReader.getElementsFromXML(inputStream, "%", this.resourceBundles);
        if (null == observableList || observableList.isEmpty()) {
            TILogger.logError(TAG, "XML File Failed to Load! Check contents of file.");
            return false;
        }
        if (null != observableList) {
            this.allCatalogs.addAll(observableList);
        }
        return true;
    }
}

