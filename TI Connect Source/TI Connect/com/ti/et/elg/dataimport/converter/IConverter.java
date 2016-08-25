/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter;

public interface IConverter<Input, Output, ConverterException extends Exception> {
    public Output convert(Input var1) throws Exception;
}

