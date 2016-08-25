/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.converter.impl;

import com.ti.et.elg.dataimport.converter.impl.ConverterException;

public final class DIConverterException
extends ConverterException {
    private static final long serialVersionUID = -2126958395229561748L;

    public DIConverterException() {
    }

    public DIConverterException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public DIConverterException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DIConverterException(String string) {
        super(string);
    }

    public DIConverterException(Throwable throwable) {
        super(throwable);
    }
}

