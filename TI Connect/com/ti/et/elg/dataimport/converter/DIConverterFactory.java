/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter;

import com.ti.et.elg.dataimport.converter.IDITIBcdNumberConverter;
import com.ti.et.elg.dataimport.converter.IDITIVarDataConverter;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.converter.impl.DITIBcdNumberConverter;
import com.ti.et.elg.dataimport.converter.impl.DITIVarDataConverterFacade;
import com.ti.et.elg.dataimport.model.IDIConfig;

public final class DIConverterFactory {
    private static DITIBcdNumberConverter bcdConverter = null;
    private static IDITIVarDataConverter tivarDataConverter = null;

    private DIConverterFactory() {
    }

    public static final void configureFactory() {
        DIConverterFactory.configureFactory(new DITIBcdNumberConverter(), new DITIVarDataConverterFacade());
    }

    protected static final void configureFactory(DITIBcdNumberConverter dITIBcdNumberConverter, DITIVarDataConverterFacade dITIVarDataConverterFacade) {
        bcdConverter = dITIBcdNumberConverter;
        tivarDataConverter = dITIVarDataConverterFacade;
    }

    public static final IDITIVarDataConverter getTIVarDataConverter(IDIConfig iDIConfig) throws DIConverterException {
        if (iDIConfig == null) {
            throw new DIConverterException("No configuration has been provided");
        }
        if (tivarDataConverter == null) {
            throw new DIConverterException("The Data Converter is null. The configureFactory() method needs to be executed before");
        }
        tivarDataConverter.setConfig(iDIConfig);
        return tivarDataConverter;
    }

    public static final IDITIBcdNumberConverter getBcdConverter() throws DIConverterException {
        if (bcdConverter == null) {
            throw new DIConverterException("The bcd number is null. The configureFactory() method needs to be executed before");
        }
        return bcdConverter;
    }
}

