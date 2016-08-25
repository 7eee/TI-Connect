/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.model.impl;

import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.DITIDataType;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.util.StringUtils;

public final class DIConfig
implements IDIConfig {
    private int numberOfCols;
    private int numberOfRows;
    private DITIDataType type = DITIDataType.UNKNOWN;
    private String srcFilePath = null;

    public DIConfig() {
    }

    public DIConfig(int n, int n2, DITIDataType dITIDataType, String string) throws DIConverterException {
        this.setNumberOfCols(n);
        this.setNumberOfRows(n2);
        this.setType(dITIDataType);
        this.setScrFilePath(string);
    }

    @Override
    public final DITIDataType getType() {
        return this.type;
    }

    @Override
    public final int getNumberOfCols() {
        return this.numberOfCols;
    }

    @Override
    public final int getNumberOfRows() {
        return this.numberOfRows;
    }

    @Override
    public final void setType(DITIDataType dITIDataType) {
        if (dITIDataType != null) {
            this.type = dITIDataType;
        }
    }

    @Override
    public final void setNumberOfCols(int n) {
        this.numberOfCols = n;
    }

    @Override
    public final void setNumberOfRows(int n) {
        this.numberOfRows = n;
    }

    @Override
    public final void setScrFilePath(String string) throws DIConverterException {
        if (!StringUtils.isNotEmpty(string)) {
            throw new DIConverterException("The file path should not be null or empty");
        }
        this.srcFilePath = string;
    }

    @Override
    public final String getSrcFilePath() {
        return this.srcFilePath;
    }
}

