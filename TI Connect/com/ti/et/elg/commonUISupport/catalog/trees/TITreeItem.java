/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.control.TreeItem
 */
package com.ti.et.elg.commonUISupport.catalog.trees;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

public class TITreeItem
extends TreeItem<String> {
    private String name = "";
    private int hierLevel = 0;
    private final List<String> descriptionsList = new ArrayList<String>();
    private final List<String> syntaxList = new ArrayList<String>();
    private String lastSyntaxAdded = "";

    public void setName(String string) {
        if (null != string && !string.isEmpty()) {
            this.name = string;
        }
    }

    public void setLevel(int n) {
        this.hierLevel = n;
    }

    public int getLevel() {
        return this.hierLevel;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < this.hierLevel - 1; ++i) {
            string = string + "\t";
        }
        return new String((string + this.name + (String)this.getValue()).getBytes(), Charset.forName("UTF-8"));
    }

    private boolean addStringToList(String string, List<String> list) {
        if (null != string && null != list && !list.contains(string)) {
            return list.add(string);
        }
        return false;
    }

    public boolean addDescription(String string) {
        return this.addStringToList(string, this.descriptionsList);
    }

    public boolean addSyntax(String string) {
        this.lastSyntaxAdded = string;
        return this.addStringToList(string, this.syntaxList);
    }

    public String getDescription(int n) {
        return this.descriptionsList.get(n);
    }

    public String getSyntax(int n) {
        return this.syntaxList.get(n);
    }

    public String getLastSyntax() {
        return this.lastSyntaxAdded;
    }

    public boolean hasSyntaxes() {
        return null != this.syntaxList && this.syntaxList.size() > 0;
    }

    public int getSyntaxesCount() {
        return this.syntaxList.size();
    }
}

