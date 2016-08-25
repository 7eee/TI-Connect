/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.translator;

import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class TranslatorResourceBundles {
    public static TIResourceBundle CATALOGS_BUNDLE = new TIResourceBundle(ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs"));
    public static TIResourceBundle LOCALE_TO_TOKEN_BUNDLE = TranslatorResourceBundles.getLocalizedText2TokenBundle();
    public static final TIResourceBundle TOKEN_TO_PROPERTY_KEY_BUNDLE = new TIResourceBundle(ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.token2propertyKey"));

    private static TIResourceBundle getLocalizedText2TokenBundle() {
        TIResourceBundle tIResourceBundle = TranslatorResourceBundles.isSupportedForeignLanguage() ? new TIResourceBundle(ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.localizedText2token")) : new TIResourceBundle(ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.localizedText2token", new Locale("en", "US")));
        return tIResourceBundle;
    }

    private static boolean isSupportedForeignLanguage() {
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(new Locale("de").getLanguage())) {
            return true;
        }
        if (locale.getLanguage().equals(new Locale("es").getLanguage())) {
            return true;
        }
        if (locale.getLanguage().equals(new Locale("fr").getLanguage())) {
            return true;
        }
        if (locale.getLanguage().equals(new Locale("nl").getLanguage())) {
            return true;
        }
        if (locale.getLanguage().equals(new Locale("pt").getLanguage())) {
            return true;
        }
        if (locale.getLanguage().equals(new Locale("sv").getLanguage())) {
            return true;
        }
        return false;
    }

    public static void reloadResourceBundlesWithCurrentLocale() {
        CATALOGS_BUNDLE = new TIResourceBundle(ResourceBundle.getBundle("com.ti.et.elg.connectServer.translator.catalogs"));
        LOCALE_TO_TOKEN_BUNDLE = TranslatorResourceBundles.getLocalizedText2TokenBundle();
        TILogger.logDetail(TranslatorResourceBundles.class.getSimpleName(), "TranslatorResourceBundles with current Locale reloaded successfully");
    }
}

