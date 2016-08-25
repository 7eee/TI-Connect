/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.util;

import com.ti.et.elg.dataimport.util.StringUtils;

public class BCDUtils {
    private static final byte BYTES_REPRESENTATION_NUMBER_SIZE = 9;
    private static final int RADIX = 16;
    private static final byte EXPONENT_BIAS = -128;
    private static final byte BYTES_REPRESENTATION_MANTISSA = 7;
    private static final int MANTISSA_DIGITS_IN_BYTES_ARRAY = 14;
    private static final String ZERO_DIGIT_FOR_MANTISSA = "0";
    protected static final byte FLAG_BIT_MASK_POSITIVE = 0;
    protected static final byte FLAG_BIT_MASK_NEGATIVE = -128;
    protected static final int MAX_EXPONENT = 99;
    protected static final int MIN_EXPONENT = -99;

    public static final String getMantissa(String string) {
        if (StringUtils.isNotEmpty(string)) {
            if (BCDUtils.isExponentSign(string = string.toLowerCase())) {
                string = BCDUtils.removeExponentSign(string);
            }
            string = BCDUtils.removeMinusMantissaSign(string);
            string = BCDUtils.removeZeroes(string);
            string = BCDUtils.removeDecimalPoint(string);
            string = BCDUtils.removeZeroes(string);
            string = BCDUtils.fillNumberWithZeroes(string);
        }
        return string;
    }

    public static final byte getMantissaSign(String string) {
        int n;
        int n2 = 0;
        int n3 = 0;
        if (BCDUtils.isExponentSign(string)) {
            n3 = BCDUtils.getExponentPosition(string);
        }
        n2 = n3 < (n = BCDUtils.getMinusSignPosition(string)) ? 0 : (n != -1 ? -128 : 0);
        return (byte)n2;
    }

    private static final int getMinusSignPosition(String string) {
        int n = -1;
        n = string.indexOf("-");
        return n;
    }

    protected static final String removeExponentSign(String string) {
        int n = BCDUtils.getExponentPosition(string);
        if (n > -1 && StringUtils.isNotEmpty(string = string.substring(0, n))) {
            string = string.trim();
        }
        return string;
    }

    private static final String removeMinusMantissaSign(String string) {
        int n = BCDUtils.getMinusSignPosition(string);
        if (n >= 0 && StringUtils.isNotEmpty(string = string.replace("-", ""))) {
            string = string.trim();
        }
        return string;
    }

    private static final int getExponentPosition(String string) {
        int n = -1;
        if ((string = string.toLowerCase()).indexOf("e") > -1) {
            n = string.indexOf("e");
        }
        return n;
    }

    public static final byte[] convertStringToBCD(String string) {
        byte[] arrby = new byte[9];
        if (StringUtils.isNotEmpty(string)) {
            string = string.toLowerCase();
            arrby[0] = BCDUtils.getMantissaSign(string);
            String string2 = BCDUtils.getMantissa(string);
            arrby[1] = BCDUtils.getByteExponent(string);
            arrby = BCDUtils.mantissaToByteArray(string2, arrby, 2);
        }
        return arrby;
    }

    public static final byte[] getMantissaByteArray(String string) {
        byte[] arrby = new byte[7];
        if (StringUtils.isNotEmpty(string)) {
            String string2 = BCDUtils.getMantissa(string);
            arrby = BCDUtils.mantissaToByteArray(string2, arrby, 0);
        }
        return arrby;
    }

    private static final int getExponentFromNumber(String string) {
        int n;
        int n2 = 0;
        if (BCDUtils.isExponentSign(string) && (n = BCDUtils.getExponentPosition(string)) != -1) {
            String string2 = string.substring(n + 1, string.length());
            n2 = Integer.valueOf(string2);
        }
        return n2;
    }

    private static final boolean isExponentSign(String string) {
        if ((string = string.toLowerCase()).indexOf("e") > -1) {
            return true;
        }
        return false;
    }

    protected static final String removeDecimalPoint(String string) {
        return string.replace(".", "");
    }

    protected static final String fillNumberWithZeroes(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int n = string.length();
        if (n < 14) {
            for (int i = n; i < 14; ++i) {
                stringBuilder.append("0");
            }
        } else {
            stringBuilder = new StringBuilder(string.substring(0, 14));
        }
        return stringBuilder.toString();
    }

    protected static final String removeZeroes(String string) {
        string = BCDUtils.removeLeadingZeroes(string);
        string = StringUtils.isNotEmpty(string = BCDUtils.removeTrailingZeroes(string)) ? string.trim() : "0";
        if (string.equals(".")) {
            string = "";
        }
        return string;
    }

    private static final String removeLeadingZeroes(String string) {
        char[] arrc = string.toCharArray();
        for (int i = 0; i < arrc.length && String.valueOf(arrc[i]).equals("0"); ++i) {
            arrc[i] = '\u0000';
        }
        return String.valueOf(arrc);
    }

    private static final String removeTrailingZeroes(String string) {
        char[] arrc = string.toCharArray();
        if (BCDUtils.isFloatingPointNumber(string)) {
            int n = BCDUtils.getDecimalPointPosition(string);
            if (arrc != null && arrc.length > 0) {
                boolean bl = false;
                for (int i = n + 1; i < arrc.length; ++i) {
                    if (String.valueOf(arrc[i]).equals("0")) continue;
                    bl = true;
                    break;
                }
                if (!bl) {
                    string = string.substring(0, n + 1);
                }
            }
        }
        return string;
    }

    protected static final int getDecimalPointPosition(String string) {
        int n = -1;
        if (BCDUtils.isFloatingPointNumber(string)) {
            n = string.indexOf(".");
        }
        return n;
    }

    private static final boolean isFloatingPointNumber(String string) {
        boolean bl = false;
        if (string.indexOf(".") > -1) {
            bl = true;
        }
        return bl;
    }

    private static final String calculateExponent(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        string = BCDUtils.removeMinusMantissaSign(string);
        if (StringUtils.isNotEmpty(string = BCDUtils.removeZeroes(string))) {
            boolean bl = BCDUtils.isFloatingPointNumber(string);
            if (!bl) {
                string = string.concat(".");
            }
            int n = BCDUtils.getDecimalPointPosition(string);
            int n2 = BCDUtils.countZeroesAfterDecimalPoint(string);
            if (n == 0 && n2 >= 0) {
                stringBuilder.append("-").append(n2 + 1);
            }
            if (n > 1 && bl) {
                int n3 = n - 1;
                if (BCDUtils.getMinusSignPosition(string) > -1) {
                    --n3;
                }
                stringBuilder.append(n3);
            } else if (!bl) {
                stringBuilder.append(n - 1);
            }
        }
        if (stringBuilder.length() == 0) {
            stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }

    protected static final int countZeroesAfterDecimalPoint(String string) {
        char[] arrc;
        int n = BCDUtils.getDecimalPointPosition(string);
        int n2 = 0;
        if (n != -1 && (arrc = string.toCharArray()) != null && arrc.length > 0) {
            for (int i = n + 1; i < arrc.length && String.valueOf(arrc[i]).equals("0"); ++i) {
                ++n2;
            }
        }
        return n2;
    }

    protected static final int getExponent(String string) {
        int n = 0;
        boolean bl = BCDUtils.isExponentSign(string);
        if (bl) {
            n += BCDUtils.getExponentFromNumber(string);
            string = BCDUtils.removeExponentSign(string);
        }
        String string2 = BCDUtils.calculateExponent(string);
        return n += Integer.valueOf(string2).intValue();
    }

    public static final byte getByteExponent(String string) throws ArithmeticException {
        int n = BCDUtils.getExponent(string);
        if (n > 99 || n < -99) {
            throw new ArithmeticException("Value out of range: " + string);
        }
        return (byte)(n + -128 & 255);
    }

    private static final byte[] mantissaToByteArray(String string, byte[] arrby, int n) {
        for (int i = 0; i < string.length(); ++i) {
            if (i % 2 != 0) continue;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(string.charAt(i)).append(string.charAt(i + 1));
            arrby[n] = (byte)Integer.parseInt(stringBuffer.toString(), 16);
            ++n;
        }
        return arrby;
    }
}

