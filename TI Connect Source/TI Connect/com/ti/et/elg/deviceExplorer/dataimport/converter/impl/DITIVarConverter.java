/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer.dataimport.converter.impl;

import com.ti.et.elg.commonUISupport.sendToHandheldDialog.VarNameManager;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.dataimport.converter.IConverter;
import com.ti.et.elg.dataimport.converter.impl.ConverterException;
import com.ti.et.elg.dataimport.model.DITIDataType;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import java.util.ArrayList;
import java.util.List;

public class DITIVarConverter
implements IConverter<List<IDITIVarData>, List<TIVar>, ConverterException> {
    @Override
    public List<TIVar> convert(List<IDITIVarData> list) throws ConverterException {
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
        int n = 0;
        for (IDITIVarData iDITIVarData : list) {
            int n2 = 0;
            switch (iDITIVarData.getType()) {
                case LIST: {
                    n2 = 1;
                    break;
                }
                case MATRIX: {
                    n2 = 2;
                    break;
                }
                default: {
                    throw new ConverterException("Unknown converter type.");
                }
            }
            String[] arrstring = VarNameManager.getPredefinedNames(n2);
            String string = null;
            if (arrstring != null && arrstring.length > 0) {
                string = VarNameManager.getPredefinedNames(n2)[n++];
            }
            TIVar tIVar = ConnectServerFactory.getCommManager().createTIVar(iDITIVarData.getSrcFilePath(), string, null, iDITIVarData.getData().length, 0, 0, n2, false, 10, 115, false);
            tIVar.setData(iDITIVarData.getData());
            arrayList.add(tIVar);
        }
        return arrayList;
    }

}

