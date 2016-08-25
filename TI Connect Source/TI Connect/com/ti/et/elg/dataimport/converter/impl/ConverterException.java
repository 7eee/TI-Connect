/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter.impl;

public class ConverterException
extends Exception {
    private static final long serialVersionUID = 1;

    public ConverterException() {
    }

    public ConverterException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public ConverterException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ConverterException(String string) {
        super(string);
    }

    public ConverterException(Throwable throwable) {
        super(throwable);
    }
}

