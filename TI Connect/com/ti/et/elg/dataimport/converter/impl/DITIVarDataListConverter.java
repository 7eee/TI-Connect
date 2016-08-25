/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter.impl;

import com.ti.et.elg.dataimport.converter.impl.ADITIVarDataConverter;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.DITIDataType;
import com.ti.et.elg.dataimport.model.IDIConfig;
import com.ti.et.elg.dataimport.model.IDITIBcdNumber;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import com.ti.et.elg.dataimport.model.impl.DITIVarData;
import java.util.LinkedList;
import java.util.List;

public final class DITIVarDataListConverter
extends ADITIVarDataConverter {
    public DITIVarDataListConverter(IDIConfig iDIConfig) throws DIConverterException {
        this.setConfig(iDIConfig);
    }

    @Override
    public final List<IDITIVarData> convert(List<List<IDITIBcdNumber>> list) throws DIConverterException {
        LinkedList<IDITIVarData> linkedList = new LinkedList<IDITIVarData>();
        if (list != null) {
            for (List<IDITIBcdNumber> list2 : list) {
                linkedList.add(new DITIVarData(DITIDataType.LIST, this.toByteArray(list2), this.getConfig().getSrcFilePath()));
            }
        }
        return linkedList;
    }

    private final byte[] toByteArray(List<IDITIBcdNumber> list) throws DIConverterException {
        byte[] arrby = new byte[list.size() * 9 + 2];
        if (list != null && !list.isEmpty()) {
            int n = 0;
            arrby[n++] = this.getSizeLSB(list);
            arrby[n++] = this.getSizeMSB(list);
            for (IDITIBcdNumber iDITIBcdNumber : list) {
                System.arraycopy(iDITIBcdNumber.getTIBcdNumber(), 0, arrby, n, 9);
                n += 9;
            }
        }
        return arrby;
    }

    private final byte getSizeLSB(List<IDITIBcdNumber> list) {
        return (byte)(list.size() & 255);
    }

    private final byte getSizeMSB(List<IDITIBcdNumber> list) {
        return (byte)(list.size() >> 8 & 255);
    }
}

