/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.model;

import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.DITIDataType;

public interface IDIConfig {
    public DITIDataType getType();

    public int getNumberOfCols();

    public int getNumberOfRows();

    public void setType(DITIDataType var1);

    public void setNumberOfCols(int var1);

    public void setNumberOfRows(int var1);

    public void setScrFilePath(String var1) throws DIConverterException;

    public String getSrcFilePath();
}

