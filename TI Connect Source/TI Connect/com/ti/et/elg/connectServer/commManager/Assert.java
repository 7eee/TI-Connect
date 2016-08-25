/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

class Assert {
    Assert() {
    }

    public static void assertTrue(boolean bl) throws Error {
        if (!bl) {
            throw new Error("assertTrue failed!");
        }
    }

    public static void assertEquals(Object object, Object object2) throws Error {
        if (object == null || !object.equals(object2)) {
            throw new Error("assertEquals(" + object + "," + object2 + ")");
        }
    }

    public static void assertEquals(int n, int n2) throws Error {
        if (n != n2) {
            throw new Error("assertEquals(" + n + "," + n2 + ")");
        }
    }
}

