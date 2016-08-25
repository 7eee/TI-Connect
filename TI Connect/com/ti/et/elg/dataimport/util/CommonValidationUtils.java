/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.util;

import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonValidationUtils {
    private static final String LOG_TAG = CommonValidationUtils.class.getSimpleName();
    private static final String CSV_FILE_EXTENSION = "csv";
    public static final int MAX_EXPONENT = 99;
    public static final int MIN_EXPONENT = -99;
    public static final String REGEX_FLOATING_NUMBERS_VALIDATION = "[+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*)(?:[eE][+-]?\\d+)?";
    public static final Pattern myPattern = Pattern.compile("[+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*)(?:[eE][+-]?\\d+)?");
    public static final Matcher myMatcher = myPattern.matcher("");

    private CommonValidationUtils() {
    }

    public static void parseNumber(String string) throws DIConverterException {
        myMatcher.reset(string);
        if (!myMatcher.matches()) {
            throw new DIConverterException("Not a valid number: " + string);
        }
    }

    public static boolean isCsvFile(String string) {
        File file = CommonValidationUtils.getFilePath(string);
        if (file != null && CommonValidationUtils.getFileExtension(file).equals("csv") && file != null && file.exists() && file.canRead()) {
            return true;
        }
        return false;
    }

    public static File getFilePath(String string) {
        File file = new File(string);
        if (file == null || !file.exists() || !file.canRead()) {
            try {
                file = new File(URLDecoder.decode(file.getAbsolutePath(), "UTF-8"));
            }
            catch (Exception var2_2) {
                TILogger.logError(LOG_TAG, "Couldn't decode the file" + var2_2.getMessage());
                return null;
            }
        }
        return file;
    }

    private static String getFileExtension(File file) {
        String string = file.getName();
        if (string.lastIndexOf(".") != -1 && string.lastIndexOf(".") != 0) {
            return string.substring(string.lastIndexOf(".") + 1);
        }
        return "";
    }
}

