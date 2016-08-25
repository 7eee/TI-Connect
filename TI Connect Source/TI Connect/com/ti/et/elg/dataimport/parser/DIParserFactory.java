/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.parser;

import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.parser.DIParserType;
import com.ti.et.elg.dataimport.parser.IDIParser;
import com.ti.et.elg.dataimport.parser.impl.CSVDIParser;

public final class DIParserFactory {
    private static IDIParser parser;

    public static final void configureParser(DIParserType dIParserType) throws DIException {
        if (dIParserType == null) {
            throw new DIException("No Parser type has been configured for the Parser Factory");
        }
        switch (dIParserType) {
            case CSV: {
                DIParserFactory.setParser(DIParserFactory.getCSVParser());
                break;
            }
            default: {
                throw new DIException("Given parser type is not supported yet");
            }
        }
    }

    private static final IDIParser getCSVParser() {
        if (parser != null && parser instanceof CSVDIParser) {
            return parser;
        }
        return new CSVDIParser();
    }

    public static final IDIParser getParser() throws DIException {
        if (parser == null) {
            throw new DIException("There is no parser configured");
        }
        return parser;
    }

    protected static final void setParser(IDIParser iDIParser) throws DIException {
        if (iDIParser == null) {
            throw new DIException("The parser cannot be null");
        }
        parser = iDIParser;
    }

}

