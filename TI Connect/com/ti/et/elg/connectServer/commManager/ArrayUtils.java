/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

class ArrayUtils {
    ArrayUtils() {
    }

    public static /* varargs */ String[] concat(String[] ... arrstring) {
        int n = 0;
        for (String[] arrstring2 : arrstring) {
            n += arrstring2.length;
        }
        String[][] arrstring3 = new String[n];
        int n2 = 0;
        for (String[] arrstring4 : arrstring) {
            System.arraycopy(arrstring4, 0, arrstring3, n2, arrstring4.length);
            n2 += arrstring4.length;
        }
        return arrstring3;
    }
}

