/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class AppLogFormatter
extends Formatter {
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    public static final int LEVEL_SEVERE = Level.SEVERE.intValue();
    public static final int LEVEL_WARNING = Level.WARNING.intValue();
    public static final int LEVEL_INFO = Level.INFO.intValue();
    public static final int LEVEL_CONFIG = Level.CONFIG.intValue();
    public static final int LEVEL_FINE = Level.FINE.intValue();
    public static final int LEVEL_FINER = Level.FINER.intValue();
    private static final Map<String, String> loggerShortNames = new HashMap<String, String>();
    private StringBuffer buf = new StringBuffer();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String format(LogRecord logRecord) {
        int n = logRecord.getLevel().intValue();
        this.buf.setLength(0);
        Object object = timeFormat;
        synchronized (object) {
            this.buf.append(timeFormat.format(logRecord.getMillis()));
        }
        this.buf.append(" <");
        if (n >= LEVEL_SEVERE) {
            this.buf.append("E");
        } else if (n >= LEVEL_WARNING) {
            this.buf.append("W");
        } else if (n >= LEVEL_INFO) {
            this.buf.append("I");
        } else if (n >= LEVEL_CONFIG) {
            this.buf.append("C");
        } else if (n >= LEVEL_FINE) {
            this.buf.append("D");
        } else if (n >= LEVEL_FINER) {
            this.buf.append("DD");
        } else {
            this.buf.append("DDD");
        }
        this.buf.append("> <");
        object = logRecord.getLoggerName();
        Object object2 = loggerShortNames.get(object);
        if (object2 == null) {
            int n2 = object.lastIndexOf(46);
            object2 = n2 >= 0 && n2 < object.length() - 1 ? object.substring(n2 + 1) : object;
            loggerShortNames.put((String)object, (String)object2);
        }
        this.buf.append((String)object2);
        this.buf.append("> ");
        this.buf.append(this.formatMessage(logRecord));
        this.buf.append("\r\n");
        this.processThrowable(this.buf, logRecord.getThrown());
        return this.buf.toString();
    }

    private void processThrowable(StringBuffer stringBuffer, Throwable throwable) {
        if (throwable != null) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            printWriter.close();
            stringBuffer.append(stringWriter.getBuffer());
        }
    }
}

