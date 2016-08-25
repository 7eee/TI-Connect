/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.parser;

import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface IDIParser {
    public static final String DEFAULT_DELIMITER = ",";

    public void useDelimeter(String var1);

    public void useDelimeter(Pattern var1);

    public Pattern getDelimeter();

    public List<Map<String, String>> getParsedData();

    public void setFirstRecordHeader(boolean var1);

    public boolean isFirstRecordHeader();

    public Map<String, String> parseRecord(String var1) throws DIConverterException;

    public List<Map<String, String>> parse(File var1) throws DIConverterException;

    public List<Map<String, String>> parse(String var1) throws DIConverterException;
}

