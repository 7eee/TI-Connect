/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.translator;

import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.translator.TI83PlusProgramDataDictionary;
import com.ti.et.elg.connectServer.translator.TIProgramDataHolder;
import com.ti.et.utils.logger.TILogger;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class ProgramTranslation {
    public static final int MAX_CHARACTER_SIZE = 22;
    public static final int MAX_BLOCK_SIZE = 44;
    public static final byte FILE_LINE_FEED = 10;
    public static final byte FILE_CARRIGAGE_RETURN = 13;
    private static final char STO_CHAR = Character.toChars(8594)[0];
    private static final byte NULL_TOKEN = 0;
    private static final byte MATRIX_TOKEN = 92;
    private static final byte LIST_TOKEN = 93;
    private static final byte EQUATION_TOKEN = 94;
    private static final byte PICTURE_TOKEN = 96;
    private static final byte GDB_TOKEN = 97;
    private static final byte SYSTEMOUT_TOKEN = 98;
    private static final byte SYSTEMVAR_TOKEN = 99;
    private static final byte GRAPHFORMAT_TOKEN = 126;
    private static final byte STRINGVARS_TOKEN = -86;
    private static final byte SECONDARY_TOKEN = -69;
    private static final byte ADDITIONALSEC_TOKEN = -17;
    private static final byte IMAGES_TOKEN = 60;
    private static final String LOG_TAG = ProgramTranslation.class.getSimpleName();
    private boolean isAlphaBetweenQuotes = false;
    private static ProgramTranslation instance = new ProgramTranslation();

    public static ProgramTranslation getInstance() {
        return instance;
    }

    public TIProgramDataHolder textToProgram(String string, int n) {
        TILogger.logDetail(LOG_TAG, "In textToProgram with textProgram='" + (string.contains("\n") ? string.replaceAll("\n", "\\n") : string) + "'");
        TIProgramDataHolder tIProgramDataHolder = new TIProgramDataHolder();
        int n2 = 0;
        byte[] arrby = new byte[44];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        boolean bl = false;
        byte[] arrby2 = null;
        int n7 = 0;
        this.setAlphaBetweenQuotes(false);
        int n8 = 0;
        byte[] arrby3 = null;
        try {
            arrby3 = string.getBytes("UTF-16BE");
            TILogger.logBytes(LOG_TAG, "unicode16Array:", arrby3);
        }
        catch (UnsupportedEncodingException var15_15) {
            TILogger.logError(LOG_TAG, "textToProgram", var15_15);
        }
        if (arrby3 != null) {
            n4 = n2 = arrby3.length;
            arrby2 = new byte[n4 * 2];
        } else {
            n2 = 0;
            n4 = 0;
        }
        if (n4 != 0) {
            while (n4 > 0) {
                int n9;
                n2 = n4 > 44 ? 44 : n4;
                n3 = n2;
                Arrays.fill(arrby, 0);
                System.arraycopy(arrby3, n6, arrby, 0, n2);
                if (arrby[0] == 0 && arrby[1] == 13 || arrby[0] == 0 && arrby[1] == 10) {
                    n2 = 2;
                    n3 = 2;
                } else {
                    n5 = 3;
                    while (n5 < n3) {
                        if (arrby[n5 - 1] == 0 && arrby[n5] == 13 || arrby[n5 - 1] == 0 && arrby[n5] == 10) {
                            n3 = n2 = n5 + 1;
                            n5 = 44;
                            continue;
                        }
                        n5 += 2;
                    }
                }
                bl = false;
                n5 = 0;
                String string2 = null;
                while (!bl && n2 > 0) {
                    string2 = null;
                    try {
                        string2 = new String(arrby, 0, n3, "UTF-16BE");
                    }
                    catch (Exception var16_19) {
                        TILogger.logError(LOG_TAG, "textToProgram", var16_19);
                    }
                    string2 = this.newBetweenQuotesValidation(string2, 0);
                    n9 = TI83PlusProgramDataDictionary.getInstance().getTokenForString(string2);
                    if (n9 != 0) {
                        n8 = this.getVersionBasedOnCurrentToken(n9);
                        if (this.isAlphaBetweenQuotes() && n2 > 2 && (n8 >> 8 & 255) != 255 && (n8 >> 8 & 255) >= 9) {
                            n3 = n2 -= 2;
                            TILogger.logDetail(LOG_TAG, "String:'" + string2 + "' within quotes is v10+, tokenizing into single characters to be 84P_B&W compliant.");
                            continue;
                        }
                        n7 = this.appendTokenToOutputBuffer(arrby2, n7, n9);
                        n = this.getCurrentMaxPrgrmVersion(n8, n);
                        bl = true;
                        continue;
                    }
                    n3 = n2 -= 2;
                }
                if (!bl) {
                    n9 = 0;
                    n9 = TI83PlusProgramDataDictionary.getInstance().getTokenForString("?");
                    if (n9 != 0) {
                        n7 = this.appendTokenToOutputBuffer(arrby2, n7, n9);
                        n2 = 2;
                    }
                    TILogger.logDetail(LOG_TAG, "Tokenization fail, token not found for string='" + string2 + "', bailing out... ");
                    tIProgramDataHolder.setTIStatus(new TIStatus(63));
                    return tIProgramDataHolder;
                }
                n6 += n2;
                n4 -= n2;
            }
            tIProgramDataHolder.setProgramVersion((n & 65280) >> 8);
            int n10 = n7;
            byte[] arrby4 = new byte[n10 + 2];
            arrby4[0] = (byte)(n10 & 255);
            arrby4[1] = (byte)(n10 >> 8 & 255);
            System.arraycopy(arrby2, 0, arrby4, 2, n10);
            tIProgramDataHolder.setProgramTokens(arrby4);
            tIProgramDataHolder.setTIStatus(new TIStatus(0));
        } else {
            arrby2 = new byte[2];
            tIProgramDataHolder.setProgramVersion(0);
            tIProgramDataHolder.setProgramTokens(arrby2);
            tIProgramDataHolder.setTIStatus(new TIStatus(0));
        }
        TILogger.logInfo(LOG_TAG, "ProgramVersion=0x" + Integer.toHexString((byte)tIProgramDataHolder.getProgramVersion() + 1) + " ProgramTIStatus=" + tIProgramDataHolder.getTIStatus().getStatusCode());
        TILogger.logBytes(LOG_TAG, "ProgramTokens: ", tIProgramDataHolder.getProgramTokens());
        return tIProgramDataHolder;
    }

    private int getCurrentMaxPrgrmVersion(int n, int n2) {
        byte by = (byte)((n >> 8 & 255) + 1);
        byte by2 = (byte)((n2 >> 8 & 255) + 1);
        return by > by2 ? n : n2;
    }

    public String newBetweenQuotesValidation(String string, int n) {
        if (string != null && string.length() == 1) {
            char c = string.charAt(0);
            if (c == '\"') {
                this.setAlphaBetweenQuotes(!this.isAlphaBetweenQuotes());
            } else if (c == STO_CHAR) {
                this.setAlphaBetweenQuotes(false);
            } else if (c == '\n') {
                this.setAlphaBetweenQuotes(false);
            } else if (c >= 'a' && c <= 'z' && !this.isAlphaBetweenQuotes() && (this.isAsciiLowerCaseToken(n) || this.isAsciiLowerCaseCharacter(string))) {
                return string;
            }
        }
        return string;
    }

    private boolean isAsciiLowerCaseToken(int n) {
        return (n >> 8 & 255) == 187 && (n & 255) >= 181 && (n & 255) <= 201;
    }

    private boolean isAsciiLowerCaseCharacter(String string) {
        switch (string) {
            case "f": 
            case "g": 
            case "h": 
            case "i": 
            case "j": 
            case "k": 
            case "l": 
            case "m": 
            case "o": 
            case "q": 
            case "x": 
            case "y": {
                return true;
            }
        }
        return false;
    }

    public TIProgramDataHolder programToText(byte[] arrby) {
        byte[] arrby2;
        TILogger.logBytes(LOG_TAG, "In programToText with", arrby);
        int n = arrby.length - 2;
        int n2 = 0;
        int n3 = 2;
        TIProgramDataHolder tIProgramDataHolder = new TIProgramDataHolder();
        this.setAlphaBetweenQuotes(false);
        if (n != 0) {
            arrby2 = new byte[n * 44];
            while (n > 0) {
                int n4 = arrby[n3];
                n4 &= 255;
                if (this.isA2ByteToken(arrby[n3], n3 < n - 1 ? arrby[n3 + 1] : 255)) {
                    --n;
                    n4 |= (n4 & 255) << 8;
                    n4 &= 65280;
                    n4 |= arrby[++n3] & 255;
                }
                n4 = TI83PlusProgramDataDictionary.getInstance().convertAsciiLowerCaseToken2EquationLowerCaseToken(n4);
                String string = TI83PlusProgramDataDictionary.getInstance().getStringForToken(n4);
                if ((string = this.newBetweenQuotesValidation(string, n4)) != null) {
                    byte[] arrby3 = null;
                    try {
                        arrby3 = string.getBytes("UTF-16BE");
                    }
                    catch (UnsupportedEncodingException var10_12) {
                        TILogger.logError(LOG_TAG, "programToText", var10_12);
                    }
                    if (arrby3 != null) {
                        System.arraycopy(arrby3, 0, arrby2, n2, arrby3.length);
                        n2 += arrby3.length;
                    }
                } else {
                    TILogger.logError(LOG_TAG, "Detokenization fail, string not found for token='0x" + Integer.toHexString(n4).toUpperCase() + "', bailing out... ");
                    tIProgramDataHolder.setTIStatus(new TIStatus(63));
                    return tIProgramDataHolder;
                }
                --n;
                ++n3;
            }
        } else {
            arrby2 = new byte[]{0, 32};
        }
        String string = "";
        TILogger.logBytes(LOG_TAG, "programTextUTF16", arrby2);
        try {
            string = new String(arrby2, 0, n2, "UTF-16BE");
        }
        catch (UnsupportedEncodingException var8_10) {
            TILogger.logError(LOG_TAG, "programToText", var8_10);
        }
        TILogger.logInfo(LOG_TAG, "programString: " + string);
        tIProgramDataHolder.setProgramAsString(string);
        tIProgramDataHolder.setTIStatus(new TIStatus(0));
        return tIProgramDataHolder;
    }

    private boolean isA2ByteToken(int n, int n2) {
        switch ((byte)(n & 255)) {
            case -86: 
            case -69: 
            case -17: 
            case 0: 
            case 92: 
            case 93: 
            case 94: 
            case 96: 
            case 97: 
            case 98: 
            case 99: 
            case 126: {
                return true;
            }
            case 60: {
                if (n2 >= 0 && n2 <= 9) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private int appendTokenToOutputBuffer(byte[] arrby, int n, int n2) {
        byte by = (byte)((n2 & 65280) >> 8);
        if (by != 0) {
            arrby[n++] = by;
        }
        arrby[n++] = (byte)(n2 & 255);
        return n;
    }

    public int getVersionBasedOnCurrentToken(int n) {
        int n2 = -9175291;
        short s = 0;
        if ((n & 65280) >> 8 != 0) {
            if ((n & 65280) == 47872) {
                s = (short)((n2 & 65280) >> 8);
                if ((short)(n & 255) > 103) {
                    if (s == 255) {
                        s = 0;
                    }
                    if ((short)(n & 255) > 206) {
                        if (s < 1) {
                            s = 1;
                        }
                        if ((short)(n & 255) > 218 && s < 2) {
                            s = (short)2;
                        }
                    }
                }
                n2 &= -65281;
                n2 |= s << 8;
            } else if ((n & 65280) == 61184) {
                s = (short)((n2 & 65280) >> 8);
                if ((short)(n & 255) > 18) {
                    if (s == 255 || s < 4) {
                        s = (short)4;
                    }
                    if ((short)(n & 255) > 22) {
                        if (s == 255 || s < 5) {
                            s = (short)5;
                        }
                        if ((short)(n & 255) > 62) {
                            if (s == 255 || s < 6) {
                                s = (short)6;
                            }
                            if ((short)(n & 255) > 64) {
                                if (s == 255 || s < 9) {
                                    s = (short)9;
                                }
                                if ((short)(n & 255) > 121 && (s == 255 || s < 10)) {
                                    s = (short)10;
                                }
                            }
                        }
                    }
                } else if (s == 255 || s < 3) {
                    s = (short)3;
                }
                n2 &= -65281;
                n2 |= s << 8;
            } else if ((n & 65280) == 25344) {
                s = (short)((n2 & 65280) >> 8);
                if ((short)(n & 255) > 55 && (s == 255 || s < 9)) {
                    s = (short)9;
                }
                n2 &= -65281;
                n2 |= s << 8;
            }
        }
        return n2;
    }

    public boolean isAlphaBetweenQuotes() {
        return this.isAlphaBetweenQuotes;
    }

    public void setAlphaBetweenQuotes(boolean bl) {
        this.isAlphaBetweenQuotes = bl;
        TILogger.logDetail(LOG_TAG, "In quotes status: " + (bl ? "TRUE" : "FALSE"));
    }
}

