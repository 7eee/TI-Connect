/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.parser.impl;

import com.ti.et.elg.dataimport.parser.IDIParser;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class ADIParser
implements IDIParser {
    protected Pattern delimeter = null;
    protected List<Map<String, String>> parsedData = null;
    protected boolean firstRecordHeader;

    @Override
    public void useDelimeter(String string) {
        this.delimeter = Pattern.compile(string);
    }

    @Override
    public void useDelimeter(Pattern pattern) {
        this.delimeter = pattern;
    }

    @Override
    public Pattern getDelimeter() {
        return this.delimeter;
    }

    @Override
    public List<Map<String, String>> getParsedData() {
        return this.parsedData;
    }

    @Override
    public void setFirstRecordHeader(boolean bl) {
        this.firstRecordHeader = bl;
    }

    @Override
    public boolean isFirstRecordHeader() {
        return this.firstRecordHeader;
    }
}

