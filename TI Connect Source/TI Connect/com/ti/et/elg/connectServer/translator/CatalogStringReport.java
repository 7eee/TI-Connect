/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.translator;

import com.ti.et.elg.connectServer.translator.TranslatorResourceBundles;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class CatalogStringReport {
    static final ResourceBundle es = ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs", new Locale("es"));
    static final ResourceBundle fr = ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs", new Locale("fr"));
    static final ResourceBundle nl = ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs", new Locale("nl"));
    static final ResourceBundle pt = ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs", new Locale("pt"));
    static final ResourceBundle sv = ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs", new Locale("sv"));
    static final ResourceBundle de = ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs", new Locale("de"));

    public static void main(String[] arrstring) {
        Enumeration<String> enumeration = TranslatorResourceBundles.CATALOGS_BUNDLE.getKeys();
        System.out.println("key,root,es,fr,nl,pt,sv,de,Space Mismatch");
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            String string2 = TranslatorResourceBundles.CATALOGS_BUNDLE.getString(string);
            String string3 = es.getString(string);
            String string4 = fr.getString(string);
            String string5 = nl.getString(string);
            String string6 = pt.getString(string);
            String string7 = sv.getString(string);
            String string8 = de.getString(string);
            boolean bl = CatalogStringReport.spaceMismatch(string2, new String[]{string3, string4, string5, string6, string7, string8});
            System.out.print(string + "," + string2 + ",");
            System.out.print(string3 + ",");
            System.out.print(string4 + ",");
            System.out.print(string5 + ",");
            System.out.print(string6 + ",");
            System.out.print(string7 + ",");
            System.out.print(string8 + ",");
            System.out.println(!bl ? "OK" : "SPACE MISMATCH");
        }
    }

    private static boolean spaceMismatch(String string, String[] arrstring) {
        int n = CatalogStringReport.countLeadingSpaces(string);
        int n2 = CatalogStringReport.countTrailingSpaces(string);
        for (String string2 : arrstring) {
            int n3 = CatalogStringReport.countLeadingSpaces(string2);
            int n4 = CatalogStringReport.countTrailingSpaces(string2);
            if (n == n3 && n2 == n4) continue;
            return true;
        }
        return false;
    }

    private static int countLeadingSpaces(String string) {
        int n;
        for (n = 0; n < string.length() && string.charAt(n) == ' '; ++n) {
        }
        return n;
    }

    private static int countTrailingSpaces(String string) {
        int n;
        for (n = string.length() - 1; n >= 0 && string.charAt(n) == ' '; --n) {
        }
        return string.length() - 1 - n;
    }
}

