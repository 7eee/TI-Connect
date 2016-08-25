/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter.impl;

import com.ti.et.elg.dataimport.converter.IDITIBcdNumberConverter;
import com.ti.et.elg.dataimport.converter.impl.ConverterException;
import com.ti.et.elg.dataimport.model.IDITIBcdNumber;
import com.ti.et.elg.dataimport.model.impl.DITIBcdNumber;
import java.util.LinkedList;
import java.util.List;

public final class DITIBcdNumberConverter
implements IDITIBcdNumberConverter {
    @Override
    public final List<List<IDITIBcdNumber>> convert(List<List<String>> list) throws ConverterException {
        LinkedList<List<IDITIBcdNumber>> linkedList = new LinkedList<List<IDITIBcdNumber>>();
        if (list != null) {
            for (List<String> list2 : list) {
                LinkedList<DITIBcdNumber> linkedList2 = new LinkedList<DITIBcdNumber>();
                for (String string : list2) {
                    linkedList2.add(new DITIBcdNumber(string));
                }
                linkedList.add(linkedList2);
            }
        }
        return linkedList;
    }
}

