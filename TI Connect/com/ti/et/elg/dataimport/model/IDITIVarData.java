/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.model;

import com.ti.et.elg.dataimport.model.DITIDataType;

public interface IDITIVarData {
    public DITIDataType getType();

    public byte[] getData();

    public String getSrcFilePath();
}

