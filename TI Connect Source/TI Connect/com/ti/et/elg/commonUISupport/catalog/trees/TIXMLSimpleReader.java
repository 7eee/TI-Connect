/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.scene.control.TreeItem
 */
package com.ti.et.elg.commonUISupport.catalog.trees;

import com.ti.et.elg.commonUISupport.catalog.trees.TIXMLDefaultHandler;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class TIXMLSimpleReader {
    private static final String TAG = TIXMLSimpleReader.class.getSimpleName();

    public static ObservableList<TreeItem<String>> getElementsFromXML(InputStream inputStream, String string, TIResourceBundle[] arrtIResourceBundle) {
        TIXMLDefaultHandler tIXMLDefaultHandler = null;
        if (null != inputStream) {
            SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
            try {
                SAXParser sAXParser = sAXParserFactory.newSAXParser();
                tIXMLDefaultHandler = new TIXMLDefaultHandler(arrtIResourceBundle, string);
                sAXParser.parse(inputStream, (DefaultHandler)tIXMLDefaultHandler);
            }
            catch (ParserConfigurationException | SAXException var5_6) {
                TILogger.logError(TAG, "Internal Error while preparing to load XML Data. No changes to the list were made.\n" + var5_6.getMessage());
                tIXMLDefaultHandler = null;
            }
            catch (IOException var5_7) {
                TILogger.logError(TAG, "Internal Error while preparing to load XML Data. No changes to the list were made.\n" + var5_7.getMessage());
                tIXMLDefaultHandler = null;
            }
        } else {
            TILogger.logError(TAG, "File was empty or null. No changes to the list were made.");
        }
        if (null != tIXMLDefaultHandler) {
            return tIXMLDefaultHandler.getResult();
        }
        return null;
    }
}

