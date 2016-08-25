/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.model.impl;

import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.DITIDataType;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import com.ti.et.elg.dataimport.util.StringUtils;

public final class DITIVarData
implements IDITIVarData {
    private DITIDataType type = DITIDataType.UNKNOWN;
    private byte[] data = new byte[0];
    private String srcFilePath = null;

    public DITIVarData(DITIDataType dITIDataType, byte[] arrby, String string) throws DIConverterException {
        if (!StringUtils.isNotEmpty(string)) {
            throw new DIConverterException("File path should not be null or empty");
        }
        if (arrby == null || arrby.length == 0) {
            throw new DIConverterException("There is no information to proccess, empty|null array");
        }
        if (dITIDataType != null) {
            this.type = dITIDataType;
        }
        this.data = (byte[])arrby.clone();
        this.srcFilePath = string;
    }

    @Override
    public final DITIDataType getType() {
        return this.type;
    }

    @Override
    public final byte[] getData() {
        return (byte[])this.data.clone();
    }

    @Override
    public final String getSrcFilePath() {
        return this.srcFilePath;
    }
}

