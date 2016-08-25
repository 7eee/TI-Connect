/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.ti.et.analytics.AnalyticsCollector
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.analytics.AnalyticsCollector;
import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class AnalyticsLogHandler
extends Handler {
    private static final String ANALYTICS_EXCEPTION_SEPARATOR = ";";
    private static final int STACK_TRACE_DEPTH = 5;

    @Override
    public void publish(LogRecord logRecord) {
        Level level;
        if (logRecord != null && (level = logRecord.getLevel()).intValue() == Level.SEVERE.intValue()) {
            String string = "ClassName: " + logRecord.getSourceClassName() + ";" + "Method: " + logRecord.getSourceMethodName() + ";" + "Message: " + this.removePathInformation(logRecord.getMessage());
            Throwable throwable = logRecord.getThrown();
            if (throwable != null) {
                string = string + ";ThrowableInformation: " + this.removePathInformation(throwable.toString());
                string = string + ";StackTrace: \n" + this.removePathInformation(this.stackTrace2String(throwable, 5));
            }
            AnalyticsCollector.postException((String)string, (Boolean)false);
        }
    }

    private String removePathInformation(String string) {
        if (string == null) {
            return null;
        }
        int n = string.indexOf(File.separatorChar);
        if (n == -1) {
            return string;
        }
        return string.substring(0, n);
    }

    private String stackTrace2String(Throwable throwable, int n) {
        if (null == throwable) {
            throw new IllegalArgumentException("throwable cannot be null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("lastStack out of range");
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            if (n2 >= n) break;
            stringBuilder.append(stackTraceElement.toString()).append('\n');
            ++n2;
        }
        return stringBuilder.toString();
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}

