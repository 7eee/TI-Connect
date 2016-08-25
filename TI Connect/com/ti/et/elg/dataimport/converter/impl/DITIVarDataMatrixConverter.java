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
import java.util.ArrayList;
import java.util.List;

public class DITIVarDataMatrixConverter
extends ADITIVarDataConverter {
    public DITIVarDataMatrixConverter(IDIConfig iDIConfig) throws DIConverterException {
        this.setConfig(iDIConfig);
    }

    @Override
    public List<IDITIVarData> convert(List<List<IDITIBcdNumber>> list) throws DIConverterException {
        ArrayList<IDITIVarData> arrayList = new ArrayList<IDITIVarData>();
        if (list != null && !list.isEmpty()) {
            arrayList.add(new DITIVarData(DITIDataType.MATRIX, this.toByteArray(list), this.getConfig().getSrcFilePath()));
        }
        return arrayList;
    }

    private byte[] toByteArray(List<List<IDITIBcdNumber>> list) {
        byte[] arrby = new byte[list.size() * list.get(0).size() * 9 + 2];
        if (list != null && !list.isEmpty()) {
            int n = 0;
            arrby[n++] = (byte)list.get(0).size();
            arrby[n++] = (byte)list.size();
            for (List<IDITIBcdNumber> list2 : list) {
                for (IDITIBcdNumber iDITIBcdNumber : list2) {
                    System.arraycopy(iDITIBcdNumber.getTIBcdNumber(), 0, arrby, n, 9);
                    n += 9;
                }
            }
        }
        return arrby;
    }
}

