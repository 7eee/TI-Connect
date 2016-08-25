/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

public class Constants {
    public static String PRODUCT_VERSION_STRING = "5.1.1." + Constants.getBuildNum();

    private static String getBuildNum() {
        String string = System.getProperty("ELG_TIC_BUILD_NUM");
        if (string == null || string.length() > 5) {
            string = "DEV";
        }
        return string;
    }

    public static void updateProductMajorVersion(String string) {
        PRODUCT_VERSION_STRING = string + "." + Constants.getBuildNum();
    }
}

