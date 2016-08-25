/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport;

public final class DIException
extends Exception {
    private static final long serialVersionUID = 1;

    public DIException() {
    }

    public DIException(String string, Throwable throwable, boolean bl, boolean bl2) {
        super(string, throwable, bl, bl2);
    }

    public DIException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public DIException(String string) {
        super(string);
    }

    public DIException(Throwable throwable) {
        super(throwable);
    }
}

