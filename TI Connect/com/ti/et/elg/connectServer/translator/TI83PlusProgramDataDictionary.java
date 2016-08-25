/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.translator;

import com.ti.et.elg.connectServer.translator.TranslatorResourceBundles;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.InputStream;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.Scanner;

public class TI83PlusProgramDataDictionary {
    private static final TI83PlusProgramDataDictionary INSTANCE = new TI83PlusProgramDataDictionary();
    private HashMap<String, Integer> stringKeyToTokenDictionary = new HashMap();
    private HashMap<Integer, String> tokenKeyToStringDictionary = new HashMap();

    public HashMap<String, Integer> getStringToTokenDictionary() {
        return this.stringKeyToTokenDictionary;
    }

    public HashMap<Integer, String> getTokenToStringDictionary() {
        return this.tokenKeyToStringDictionary;
    }

    private TI83PlusProgramDataDictionary() {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/com/ti/et/elg/connectServer/translator/TI83PlusProgramDictionary.txt");
            TILogger.logDetail(TI83PlusProgramDataDictionary.class.getSimpleName(), "TI83PlusProgramDictionary.txt bytesAvailable:" + inputStream.available());
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            TILogger.logInfo(TI83PlusProgramDataDictionary.class.getSimpleName(), "Starting to parse the TI83PlusProgramDictionary.txt source dictionary.");
            while (scanner.hasNextLine()) {
                String[] arrstring = scanner.nextLine().split(",");
                if (arrstring.length <= 0) continue;
                String string = this.parseInputString(arrstring[0]);
                this.stringKeyToTokenDictionary.put(string, Integer.decode(arrstring[1].trim()));
                this.tokenKeyToStringDictionary.put(Integer.decode(arrstring[1].trim()), string);
            }
            TILogger.logInfo(TI83PlusProgramDataDictionary.class.getSimpleName(), "Done parsing the source dictionary.");
            scanner.close();
        }
        catch (Exception var1_2) {
            TILogger.logError(TI83PlusProgramDataDictionary.class.getSimpleName(), "TI83PlusProgramDataDictionary", var1_2);
        }
    }

    public static TI83PlusProgramDataDictionary getInstance() {
        return INSTANCE;
    }

    public int getTokenForString(String string) {
        int n = 0;
        boolean bl = false;
        boolean bl2 = false;
        String string2 = string;
        if (string != null) {
            block30 : {
                switch (string) {
                    case "\n": {
                        n = 63;
                        bl = true;
                        break;
                    }
                    case "\r": {
                        n = 63;
                        bl = true;
                        break;
                    }
                    case ",": {
                        n = 43;
                        bl = true;
                        break;
                    }
                    case "=": {
                        n = 106;
                        bl = true;
                        break;
                    }
                    case "!": {
                        n = 45;
                        bl = true;
                        break;
                    }
                    case ":": {
                        n = 62;
                        bl = true;
                        break;
                    }
                    case "\\": {
                        n = 48087;
                        bl = true;
                        break;
                    }
                    case "#": {
                        n = 48082;
                        bl = true;
                        break;
                    }
                    case " ": {
                        n = 41;
                        bl = true;
                        break;
                    }
                }
                if (!bl) {
                    try {
                        String string3 = TranslatorResourceBundles.LOCALE_TO_TOKEN_BUNDLE.getString(string);
                        n = Integer.decode(string3);
                        bl2 = true;
                    }
                    catch (MissingResourceException var6_7) {
                        if (!string.contains(" ")) break block30;
                        try {
                            string = string.replaceAll(" ", "__");
                            if (TranslatorResourceBundles.LOCALE_TO_TOKEN_BUNDLE.containsKey(string)) {
                                String string4 = TranslatorResourceBundles.LOCALE_TO_TOKEN_BUNDLE.getString(string);
                                n = Integer.decode(string4);
                                bl2 = true;
                            }
                        }
                        catch (Exception var7_10) {
                            TILogger.logError(TI83PlusProgramDataDictionary.class.getSimpleName(), var7_10.getMessage(), var7_10);
                        }
                    }
                }
            }
            if (bl || bl2) {
                TILogger.logDetail("getTokenForString", "Token: 0x" + Integer.toHexString(n) + " for String:'" + (string.equals("\n") ? "\\n" : string2) + "'");
            }
        }
        return n;
    }

    public String getStringForToken(int n) {
        String string = null;
        boolean bl = false;
        boolean bl2 = false;
        if (n > 0) {
            switch (n) {
                case 43: {
                    string = ",";
                    bl = true;
                    break;
                }
                case 63: {
                    string = "\n";
                    bl = true;
                    break;
                }
                case 106: {
                    string = "=";
                    bl = true;
                    break;
                }
                case 45: {
                    string = "!";
                    bl = true;
                    break;
                }
                case 62: {
                    string = ":";
                    bl = true;
                    break;
                }
                case 48087: {
                    string = "\\";
                    bl = true;
                    break;
                }
                case 41: {
                    string = " ";
                    bl = true;
                }
            }
            if (!bl) {
                try {
                    String string2 = TranslatorResourceBundles.TOKEN_TO_PROPERTY_KEY_BUNDLE.getString("0x" + Integer.toHexString(n));
                    if (TranslatorResourceBundles.CATALOGS_BUNDLE.containsKey(string2)) {
                        string = TranslatorResourceBundles.CATALOGS_BUNDLE.getString(string2);
                        bl2 = true;
                    }
                }
                catch (Exception var5_6) {
                    TILogger.logError(TI83PlusProgramDataDictionary.class.getSimpleName(), var5_6.getMessage(), var5_6);
                }
            }
            if (bl || bl2) {
                TILogger.logDetail("getStringForToken", "String: '" + (string.equals("\n") ? "\\n" : string) + "' for token: 0x" + Integer.toHexString(n));
            }
        }
        return string;
    }

    public String parseInputString(String string) {
        if (!string.contains("\\u")) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.toCharArray().length; ++i) {
            char c = string.charAt(i);
            if (c == '\\' && i + 1 < string.toCharArray().length && string.charAt(i + 1) == 'u') {
                String string2 = string.substring(i + 2, i + 2 + 4);
                int n = Integer.parseInt(string2, 16);
                stringBuilder.append(Character.toChars(n));
                i += 5;
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public int convertCharacterToUpperCase(int n) {
        switch (n) {
            case 48048: {
                return 65;
            }
            case 48049: {
                return 66;
            }
            case 48050: {
                return 67;
            }
            case 48051: {
                return 68;
            }
            case 48052: {
                return 69;
            }
            case 48053: {
                return 70;
            }
            case 48054: {
                return 71;
            }
            case 48055: {
                return 72;
            }
            case 48056: {
                return 73;
            }
            case 48057: {
                return 74;
            }
            case 48058: {
                return 75;
            }
            case 48060: {
                return 76;
            }
            case 48061: {
                return 77;
            }
            case 48062: {
                return 78;
            }
            case 48063: {
                return 79;
            }
            case 48064: {
                return 80;
            }
            case 48065: {
                return 81;
            }
            case 48066: {
                return 82;
            }
            case 48067: {
                return 83;
            }
            case 48068: {
                return 84;
            }
            case 48069: {
                return 85;
            }
            case 48070: {
                return 86;
            }
            case 48071: {
                return 87;
            }
            case 48072: {
                return 88;
            }
            case 48073: {
                return 89;
            }
            case 48074: {
                return 90;
            }
        }
        return n;
    }

    public int convertAsciiLowerCaseToken2EquationLowerCaseToken(int n) {
        switch (n) {
            case 48048: {
                return 25110;
            }
            case 48049: {
                return 25111;
            }
            case 48050: {
                return 25112;
            }
            case 48051: {
                return 25113;
            }
            case 48052: {
                return 25114;
            }
            case 48062: {
                return 25090;
            }
            case 48064: {
                return 25122;
            }
            case 48066: {
                return 25106;
            }
            case 48067: {
                return 25140;
            }
            case 48068: {
                return 25124;
            }
            case 48069: {
                return 24192;
            }
            case 48070: {
                return 24193;
            }
            case 48071: {
                return 24194;
            }
            case 48074: {
                return 25123;
            }
        }
        return n;
    }
}

