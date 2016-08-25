/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.encodingUtils;

public class EncodingUtils {
    public static String parseEscapedUnicodeInString(String string) {
        if (string == null) {
            return string;
        }
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
}

