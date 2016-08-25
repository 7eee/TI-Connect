/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter;

import com.ti.et.elg.dataimport.converter.IConverter;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.model.IDITIBcdNumber;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import java.util.List;

public interface IDITIVarDataConverter
extends IConverter<List<List<IDITIBcdNumber>>, List<IDITIVarData>, DIConverterException> {
    public void setConfig(IDIConfig var1) throws DIConverterException;

    public IDIConfig getConfig();
}

