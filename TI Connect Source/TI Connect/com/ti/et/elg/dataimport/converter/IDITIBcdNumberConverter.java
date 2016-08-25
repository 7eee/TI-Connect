/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter;

import com.ti.et.elg.dataimport.converter.IConverter;
import com.ti.et.elg.dataimport.converter.impl.ConverterException;
import com.ti.et.elg.dataimport.model.IDITIBcdNumber;
import java.util.List;

public interface IDITIBcdNumberConverter
extends IConverter<List<List<String>>, List<List<IDITIBcdNumber>>, ConverterException> {
}

