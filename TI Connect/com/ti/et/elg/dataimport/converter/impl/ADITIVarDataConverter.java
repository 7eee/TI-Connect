/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter.impl;

import com.ti.et.elg.dataimport.converter.IDITIVarDataConverter;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.model.impl.DIConfig;

public abstract class ADITIVarDataConverter
implements IDITIVarDataConverter {
    protected IDIConfig config = new DIConfig();

    @Override
    public final IDIConfig getConfig() {
        return this.config;
    }

    @Override
    public final void setConfig(IDIConfig iDIConfig) throws DIConverterException {
        if (iDIConfig == null) {
            throw new DIConverterException("the config should not be null");
        }
        this.config = iDIConfig;
    }
}

