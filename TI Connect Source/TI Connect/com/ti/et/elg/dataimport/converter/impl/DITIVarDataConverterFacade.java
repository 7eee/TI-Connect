/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter.impl;

import com.ti.et.elg.dataimport.converter.IDITIVarDataConverter;
import com.ti.et.elg.dataimport.converter.impl.ADITIVarDataConverter;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.converter.impl.DITIVarDataListConverter;
import com.ti.et.elg.dataimport.converter.impl.DITIVarDataMatrixConverter;
import com.ti.et.elg.dataimport.model.DITIDataType;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.model.IDITIBcdNumber;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import java.util.List;

public final class DITIVarDataConverterFacade
extends ADITIVarDataConverter {
    private IDITIVarDataConverter converter = null;

    @Override
    public final List<IDITIVarData> convert(List<List<IDITIBcdNumber>> list) throws DIConverterException {
        if (this.getConfig() == null) {
            throw new DIConverterException("There is no converter type configured");
        }
        switch (this.getConfig().getType()) {
            case LIST: {
                this.converter = this.getListConverter();
                break;
            }
            case MATRIX: {
                this.converter = this.getMatrixConverter();
                break;
            }
            default: {
                throw new DIConverterException("Unknown converter type configured");
            }
        }
        return (List)this.converter.convert(list);
    }

    private final IDITIVarDataConverter getListConverter() throws DIConverterException {
        if (this.converter != null && this.converter instanceof DITIVarDataListConverter) {
            this.converter.setConfig(this.getConfig());
            return this.converter;
        }
        return new DITIVarDataListConverter(this.getConfig());
    }

    private final IDITIVarDataConverter getMatrixConverter() throws DIConverterException {
        if (this.converter != null && this.converter instanceof DITIVarDataMatrixConverter) {
            this.converter.setConfig(this.getConfig());
            return this.converter;
        }
        return new DITIVarDataMatrixConverter(this.getConfig());
    }

}

