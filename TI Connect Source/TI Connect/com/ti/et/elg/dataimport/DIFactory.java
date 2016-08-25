/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport;

import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.converter.DIConverterFactory;
import com.ti.et.elg.dataimport.converter.IDITIBcdNumberConverter;
import com.ti.et.elg.dataimport.converter.IDITIVarDataConverter;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.parser.DIParserFactory;
import com.ti.et.elg.dataimport.parser.DIParserType;
import com.ti.et.elg.dataimport.parser.IDIParser;
import com.ti.et.elg.dataimport.ui.IDIController;
import com.ti.et.elg.dataimport.ui.dialog.IDIDialog;
import com.ti.et.elg.dataimport.ui.wizard.DIWizardController;
import com.ti.et.utils.logger.TILogger;

public final class DIFactory {
    private static IDIController dataImportInterface = null;
    private static IDIParser parser = null;

    private DIFactory() {
    }

    public static final void configureFactory() {
        try {
            DIParserFactory.configureParser(DIParserType.CSV);
            DIConverterFactory.configureFactory();
            parser = DIParserFactory.getParser();
            dataImportInterface = new DIWizardController();
        }
        catch (DIException var0) {
            TILogger.logError("DIFactory", "configureFactory", var0);
        }
    }

    public static final void configureFactory(DIParserType dIParserType, IDIDialog iDIDialog) throws DIException {
        DIParserFactory.configureParser(dIParserType);
        DIConverterFactory.configureFactory();
        parser = DIParserFactory.getParser();
        dataImportInterface = new DIWizardController();
        dataImportInterface.setDialog(iDIDialog);
    }

    public static final IDIController getDataImportWizard() throws DIException {
        if (dataImportInterface == null) {
            throw new DIException("The DIW Controller is null, the configureFactory() method needs to be executed before");
        }
        return dataImportInterface;
    }

    public static final IDITIVarDataConverter getTIVarConverter(IDIConfig iDIConfig) throws DIConverterException {
        if (iDIConfig == null) {
            throw new DIConverterException("No configuration has been provided");
        }
        return DIConverterFactory.getTIVarDataConverter(iDIConfig);
    }

    public static final IDITIBcdNumberConverter getTIBcdNumberConverter() throws DIConverterException {
        return DIConverterFactory.getBcdConverter();
    }

    public static final IDIParser getParser() throws DIException {
        if (parser == null) {
            throw new DIException("The parser is null, the configureFactory() method needs to be executed before");
        }
        return parser;
    }
}

