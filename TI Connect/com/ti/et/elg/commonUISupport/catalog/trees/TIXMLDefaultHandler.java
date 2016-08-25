/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.scene.control.TreeItem
 */
package com.ti.et.elg.commonUISupport.catalog.trees;

import com.ti.et.elg.commonUISupport.catalog.trees.TITreeItem;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.MissingResourceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class TIXMLDefaultHandler
extends DefaultHandler {
    private CharType currentState = null;
    private static final String TAG = TIXMLDefaultHandler.class.getSimpleName();
    private ObservableList<TreeItem<String>> resultList = FXCollections.observableArrayList();
    private int tagsCount = 0;
    private TITreeItem lastNode = null;
    private ObservableList<TreeItem<String>> tempList = this.resultList;
    private boolean allowWriteData = false;
    private boolean isNewNode = false;
    private final String propertyPrefix;
    private boolean lookUpProperties = false;
    private TIResourceBundle functionResources = null;
    private TIResourceBundle syntaxResources = null;

    public TIXMLDefaultHandler() {
        this(null, null);
    }

    public TIXMLDefaultHandler(TIResourceBundle[] arrtIResourceBundle, String string) {
        String string2 = this.propertyPrefix = null != string && !string.isEmpty() ? string : "";
        if (arrtIResourceBundle == null) {
            this.lookUpProperties = false;
        } else {
            if (null == this.propertyPrefix) {
                this.lookUpProperties = false;
                throw new IllegalArgumentException("Prefix cannot be null Resource Bundle when is not null.");
            }
            this.lookUpProperties = true;
            if (arrtIResourceBundle.length > 1) {
                this.functionResources = arrtIResourceBundle[0];
                this.syntaxResources = arrtIResourceBundle[1];
            } else if (arrtIResourceBundle.length == 1) {
                this.functionResources = arrtIResourceBundle[0];
            } else if (arrtIResourceBundle.length < 1) {
                this.lookUpProperties = false;
            }
        }
    }

    @Override
    public void startDocument() {
        this.tagsCount = 0;
        this.resultList.clear();
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        if (string3.equalsIgnoreCase("node")) {
            TITreeItem tITreeItem = new TITreeItem();
            tITreeItem.setLevel(this.tagsCount);
            if (null != this.tempList) {
                this.tempList.add(this.tempList.size(), (Object)tITreeItem);
            }
            this.lastNode = tITreeItem;
        } else if (string3.equalsIgnoreCase("array")) {
            if (null != this.lastNode) {
                this.tempList = this.lastNode.getChildren();
            }
        } else if (string3.equalsIgnoreCase("string")) {
            if (null != this.lastNode.getValue() && !((String)this.lastNode.getValue()).isEmpty()) {
                TITreeItem tITreeItem = new TITreeItem();
                tITreeItem.setLevel(this.tagsCount);
                if (null != this.tempList) {
                    this.tempList.add((Object)tITreeItem);
                }
                this.isNewNode = true;
                this.lastNode = tITreeItem;
            }
            this.allowWriteData = true;
            this.currentState = CharType.IS_VALUE;
        } else if (string3.equalsIgnoreCase("syntax")) {
            this.allowWriteData = true;
            this.currentState = CharType.IS_SYNTAX;
        }
        ++this.tagsCount;
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        if (string3.equalsIgnoreCase("string")) {
            this.allowWriteData = false;
            if (this.isNewNode) {
                TITreeItem tITreeItem;
                this.lastNode = tITreeItem = (TITreeItem)this.lastNode.getParent();
                this.isNewNode = false;
            }
        } else if (string3.equalsIgnoreCase("node")) {
            TITreeItem tITreeItem;
            this.lastNode = tITreeItem = (TITreeItem)this.lastNode.getParent();
        } else if (string3.equalsIgnoreCase("array")) {
            if (null != this.lastNode) {
                if (null != this.lastNode.getParent()) {
                    if (this.lastNode.getChildren() == this.tempList && null != this.lastNode.getParent()) {
                        this.tempList = this.lastNode.getParent().getChildren();
                    }
                } else {
                    this.tempList = this.resultList;
                }
            }
        } else if (string3.equalsIgnoreCase("syntax")) {
            this.allowWriteData = false;
            this.currentState = CharType.IS_SYNTAX;
        }
        --this.tagsCount;
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        String string = null;
        try {
            string = new String(arrc, n, n2);
        }
        catch (IndexOutOfBoundsException var5_5) {
            string = new String(arrc, n, n2 - 1);
        }
        if (null != string && this.allowWriteData) {
            switch (this.currentState) {
                case IS_VALUE: {
                    if (this.lookUpProperties) {
                        if (string.startsWith(this.propertyPrefix) && string.length() > 1) {
                            int n3 = string.length() - this.propertyPrefix.length() + 1;
                            String string2 = null;
                            try {
                                string2 = this.functionResources.getString(string.substring(this.propertyPrefix.length(), n3));
                            }
                            catch (MissingResourceException var7_14) {
                                TILogger.logBytes(TAG, "Could not find value for ", string.getBytes());
                                string2 = string;
                            }
                            if (null == string2 || string2.isEmpty()) {
                                string2 = string;
                            }
                            this.lastNode.setValue((Object)string2);
                            break;
                        }
                        String string3 = null == this.lastNode.getValue() ? "" : (String)this.lastNode.getValue();
                        this.lastNode.setValue((Object)(string3 + string));
                        break;
                    }
                    TILogger.logDetail(TAG, "Could not look up " + string);
                    String string4 = null == this.lastNode.getValue() ? "" : (String)this.lastNode.getValue();
                    this.lastNode.setValue((Object)(string4 + string));
                    break;
                }
                case IS_SYNTAX: {
                    if (this.lookUpProperties) {
                        if (string.startsWith(this.propertyPrefix) && string.length() > 1) {
                            int n4 = string.length() - this.propertyPrefix.length() + 1;
                            String string5 = null;
                            try {
                                string5 = this.syntaxResources.getString(string.substring(this.propertyPrefix.length(), n4));
                            }
                            catch (MissingResourceException var7_15) {
                                TILogger.logBytes(TAG, "Could not find value for ", string.getBytes());
                                string5 = string;
                            }
                            if (null == string5 || string5.isEmpty()) {
                                string5 = string;
                            }
                            this.lastNode.addSyntax(string5);
                            break;
                        }
                        String string6 = null == this.lastNode.getLastSyntax() ? "" : this.lastNode.getLastSyntax();
                        this.lastNode.addSyntax(string6 + string);
                        break;
                    }
                    TILogger.logDetail(TAG, "Could not look up " + string);
                    String string7 = null == this.lastNode.getValue() ? "" : (String)this.lastNode.getValue();
                    this.lastNode.setValue((Object)(string7 + string));
                }
            }
        }
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws SAXException, IOException {
        TILogger.logDetail(TAG, "AVOID DTD VALIDATION FOR: " + string + ", " + string2);
        return new InputSource(new StringReader(""));
    }

    @Override
    public void endDocument() throws SAXParseException {
        if (this.tagsCount != 0) {
            String string = "";
            if (this.tagsCount > 1) {
                string = "Missing end of " + this.tagsCount + " element(s).";
                throw new SAXParseException("Could not parse all items properly." + string, null);
            }
            if (this.tagsCount < 0) {
                string = "Extra end of element(s) found.";
                throw new SAXParseException("Could not parse all items properly." + string, null);
            }
        }
        if (null == this.resultList || this.resultList.isEmpty()) {
            throw new SAXParseException("Parsing resulted in an empty Hierarchy.", null);
        }
    }

    public ObservableList<TreeItem<String>> getResult() {
        for (TreeItem treeItem : this.resultList) {
            if (null != treeItem) continue;
            this.resultList.remove((Object)treeItem);
        }
        return this.resultList;
    }

    private static enum CharType {
        IS_VALUE,
        IS_SYNTAX;
        

        private CharType() {
        }
    }

    private final class PListTags {
        public static final String ARRAY = "array";
        public static final String NODE = "node";
        public static final String STRING = "string";
        public static final String SYNTAX = "syntax";

        private PListTags() {
        }
    }

}

