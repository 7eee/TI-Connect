/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.logger;

import com.ti.et.utils.logger.AppLogFormatter;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TILogger {
    private static final int TI_NS_LEVEL_NONE = 0;
    private static final int TI_NS_LEVEL_ERROR = 1;
    private static final int TI_NS_LEVEL_WARN = 2;
    private static final int TI_NS_LEVEL_BRIEF = 3;
    private static final int TI_NS_LEVEL_DETAIL = 4;
    private static final int TI_NS_LEVEL_VERBOSE = 5;
    private static final int TI_NS_LOG_LEVEL_MAX = 268435455;
    private static Level logLevel;
    private static String logFilePath;

    public static void init(Level level, String string, boolean bl) {
        Handler[] arrhandler;
        Handler[] arrhandler2;
        Logger logger = Logger.getLogger(TILogger.class.getName()).getParent();
        logger.setLevel(level);
        logLevel = level;
        logFilePath = string;
        for (Handler handler : arrhandler2 = logger.getHandlers()) {
            logger.removeHandler(handler);
        }
        if (bl) {
            arrhandler = new ConsoleHandler();
            arrhandler.setFormatter(new AppLogFormatter());
            arrhandler.setLevel(level);
            logger.addHandler((Handler)arrhandler);
        }
        if (string != null) {
            try {
                arrhandler = new FileHandler(string);
                arrhandler.setFormatter(new AppLogFormatter());
                arrhandler.setLevel(level);
                logger.addHandler((Handler)arrhandler);
            }
            catch (Exception var5_6) {
                Logger.getLogger(TILogger.class.getName()).severe("Unable to setup logging to " + string);
            }
        }
    }

    public static void logError(String string, String string2) {
        Logger logger = Logger.getLogger(string);
        logger.severe(string2);
    }

    public static void logError(String string, String string2, Throwable throwable) {
        Logger logger = Logger.getLogger(string);
        logger.log(Level.SEVERE, string2, throwable);
    }

    public static void logWarning(String string, String string2) {
        Logger logger = Logger.getLogger(string);
        logger.warning(string2);
    }

    public static void logWarning(String string, String string2, Throwable throwable) {
        Logger logger = Logger.getLogger(string);
        logger.log(Level.WARNING, string2, throwable);
    }

    public static void logInfo(String string, String string2) {
        Logger logger = Logger.getLogger(string);
        logger.info(string2);
    }

    public static void logDetail(String string, String string2) {
        Logger logger = Logger.getLogger(string);
        logger.fine(string2);
    }

    public static void logBytes(String string, String string2, byte[] arrby) {
        Logger logger = Logger.getLogger(string);
        StringBuffer stringBuffer = new StringBuffer(arrby.length * 2);
        for (int i = 0; i < arrby.length; ++i) {
            byte by = arrby[i];
            int n = (by & 240) >> 4;
            int n2 = by & 15;
            char c = (char)(n < 10 ? 48 + n : 65 + n - 10);
            char c2 = (char)(n2 < 10 ? 48 + n2 : 65 + n2 - 10);
            stringBuffer.append(c);
            stringBuffer.append(c2);
            stringBuffer.append(i != arrby.length - 1 ? " " : "");
            if ((i + 1) % 16 != 0 || i == arrby.length - 1) continue;
            stringBuffer.append("\n");
        }
        logger.fine(string2 + " bytes:\n" + stringBuffer.toString());
    }

    public static int getCLogLevel() {
        int n = logLevel.intValue();
        int n2 = 0;
        n2 = n >= AppLogFormatter.LEVEL_SEVERE ? 1 : (n >= AppLogFormatter.LEVEL_WARNING ? 2 : (n >= AppLogFormatter.LEVEL_INFO ? 3 : (n >= AppLogFormatter.LEVEL_CONFIG ? 4 : (n >= AppLogFormatter.LEVEL_FINE ? 5 : (n >= AppLogFormatter.LEVEL_FINER ? 268435455 : 268435455)))));
        return n2;
    }

    public static String getLogFolderPath() {
        File file = new File(logFilePath);
        return file.getParent() + File.separator;
    }

    public static String getLogFilePath() {
        return logFilePath;
    }

    static {
        logFilePath = null;
    }
}

