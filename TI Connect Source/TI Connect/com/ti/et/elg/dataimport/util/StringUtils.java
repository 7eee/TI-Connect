/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.util;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() != 0 && !string.trim().isEmpty();
    }
}

