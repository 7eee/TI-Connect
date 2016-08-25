/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.parser.impl;

import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.parser.impl.ADIParser;
import com.ti.et.elg.dataimport.util.CommonValidationUtils;
import com.ti.et.elg.dataimport.util.StringUtils;
import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class CSVDIParser
extends ADIParser {
    private static final String LOG_TAG = CSVDIParser.class.getSimpleName();
    private static final String POSITION = "p";

    @Override
    public final Map<String, String> parseRecord(String string) throws DIConverterException {
        String[] arrstring;
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        if (StringUtils.isNotEmpty(string) && (arrstring = string.split(",", -1)) != null && arrstring.length > 0) {
            int n = 0;
            for (String string2 : arrstring) {
                if (StringUtils.isNotEmpty(string2 = string2.trim())) {
                    CommonValidationUtils.parseNumber(string2);
                }
                linkedHashMap.put("p" + n, string2);
                ++n;
            }
        }
        return linkedHashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final List<Map<String, String>> parse(File file) throws DIConverterException {
        Scanner scanner = null;
        LinkedList<Map<String, String>> linkedList = new LinkedList<Map<String, String>>();
        try {
            scanner = new Scanner(file);
            linkedList.addAll(this.parse(scanner));
        }
        catch (IOException var4_4) {
            TILogger.logError(LOG_TAG, var4_4.getMessage());
        }
        finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        this.parsedData = linkedList;
        return linkedList;
    }

    @Override
    public final List<Map<String, String>> parse(String string) throws DIConverterException {
        LinkedList<Map<String, String>> linkedList = new LinkedList<Map<String, String>>();
        if (StringUtils.isNotEmpty(string)) {
            Scanner scanner = new Scanner(string);
            linkedList.addAll(this.parse(scanner));
        }
        this.parsedData = linkedList;
        return linkedList;
    }

    private final List<Map<String, String>> parse(Scanner scanner) throws DIConverterException {
        LinkedList<Map<String, String>> linkedList = new LinkedList<Map<String, String>>();
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            if (!StringUtils.isNotEmpty(string)) continue;
            linkedList.add(this.parseRecord(string));
        }
        return linkedList;
    }
}

