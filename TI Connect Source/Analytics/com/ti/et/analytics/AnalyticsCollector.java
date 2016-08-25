/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AnalyticDataTypes;
import com.ti.et.analytics.AnalyticsDispatcher;
import com.ti.et.analytics.AnalyticsEvent;
import com.ti.et.analytics.AnalyticsException;
import com.ti.et.analytics.AnalyticsScreen;
import com.ti.et.analytics.AnalyticsShutdownRequest;
import com.ti.et.analytics.AnalyticsTiming;
import com.ti.et.analytics.GoogleAnalyticsTransport;
import com.ti.et.analytics.IAnalyticsData;
import com.ti.et.analytics.IAnalyticsDispatcher;
import com.ti.et.analytics.IAnalyticsTransport;
import java.util.concurrent.LinkedTransferQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsCollector {
    private static final Logger LOGGER = Logger.getLogger(AnalyticsCollector.class.getCanonicalName());
    private static LinkedTransferQueue<IAnalyticsData> transferQueue = null;
    private static IAnalyticsDispatcher dispatcher = null;
    protected static IAnalyticsTransport transportObject = null;
    private static Boolean temporarilyDisabled = true;
    private static Boolean isInitialized = false;
    private static final long RETRY_MULTIPLIER = 2;
    private static long timeToWaitMilli = 1000;
    private static final long MAX_TIME_TO_WAIT_MILLI = 12000000;
    private static long nextTimeToTryMilli = 0;

    public static final synchronized void init(String string, String string2, String string3, String string4, String string5, String string6) {
        Thread thread = null;
        if (isInitialized.booleanValue()) {
            LOGGER.log(Level.WARNING, "Call to initialize the Analytics Subsystem what has already been initialized!");
        } else if (AnalyticsCollector.inputParametersAreValid(string, string2, string3, string4).booleanValue()) {
            transferQueue = new LinkedTransferQueue();
            dispatcher = new AnalyticsDispatcher(transferQueue);
            if (transportObject == null) {
                transportObject = new GoogleAnalyticsTransport(string, string2, string3, string4, string5, string6);
                dispatcher.initTransport(transportObject);
            }
            thread = new Thread(dispatcher);
            thread.setPriority(1);
            thread.setName("AnalyticsDispatcher");
            thread.start();
            isInitialized = true;
            temporarilyDisabled = false;
        }
    }

    private static final Boolean inputParametersAreValid(String string, String string2, String string3, String string4) {
        Boolean bl = true;
        if (string == null) {
            bl = false;
            LOGGER.log(Level.WARNING, "Null Tracking ID: Cannot initialize Analytics subsystem.");
        }
        if (string2 == null) {
            bl = false;
            LOGGER.log(Level.WARNING, "Null client ID: Cannot initialize Analytics subsystem.");
        }
        if (string3 == null) {
            bl = false;
            LOGGER.log(Level.WARNING, "Null App Name: Cannot initialize Analytics subsystem.");
        }
        if (string4 == null) {
            bl = false;
            LOGGER.log(Level.WARNING, "Null App Version: Cannot initialize Analytics subsystem.");
        }
        return bl;
    }

    protected static final void setTransport(IAnalyticsTransport iAnalyticsTransport) {
        transportObject = iAnalyticsTransport;
    }

    public static final void postEvent(String string, String string2, String string3, Integer n) {
        if (isInitialized.booleanValue() && AnalyticsCollector.okayToSend().booleanValue()) {
            AnalyticsEvent analyticsEvent = new AnalyticsEvent(string, string2, string3, n);
            AnalyticsCollector.queueTheData(analyticsEvent);
        }
    }

    public static final void postEvent(String string, String string2) {
        if (isInitialized.booleanValue() && AnalyticsCollector.okayToSend().booleanValue()) {
            AnalyticsCollector.postEvent(string, string2, null, null);
        }
    }

    public static final void postScreenVisit(String string) {
        if (isInitialized.booleanValue() && AnalyticsCollector.okayToSend().booleanValue()) {
            AnalyticsScreen analyticsScreen = new AnalyticsScreen(string);
            AnalyticsCollector.queueTheData(analyticsScreen);
        }
    }

    public static final void postTimingData(String string, String string2, String string3, long l) {
        if (isInitialized.booleanValue() && AnalyticsCollector.okayToSend().booleanValue()) {
            AnalyticsTiming analyticsTiming = new AnalyticsTiming(string, string2, string3, l);
            AnalyticsCollector.queueTheData(analyticsTiming);
        }
    }

    public static final void postTimingData(String string, String string2, long l) {
        if (isInitialized.booleanValue() && AnalyticsCollector.okayToSend().booleanValue()) {
            AnalyticsCollector.postTimingData(string, string2, null, l);
        }
    }

    public static final void postException(String string, Boolean bl) {
        if (isInitialized.booleanValue() && AnalyticsCollector.okayToSend().booleanValue()) {
            AnalyticsException analyticsException = new AnalyticsException(string, bl);
            AnalyticsCollector.queueTheData(analyticsException);
        }
    }

    public static final void shutDown() {
        AnalyticsShutdownRequest analyticsShutdownRequest = new AnalyticsShutdownRequest();
        AnalyticsCollector.queueTheData(analyticsShutdownRequest);
    }

    public static final Boolean isEnabled() {
        return isInitialized != false && AnalyticsCollector.okayToSend() != false;
    }

    protected static final void transportFailure() {
        if (!temporarilyDisabled.booleanValue()) {
            if ((timeToWaitMilli *= 2) > 12000000) {
                timeToWaitMilli = 12000000;
            }
            nextTimeToTryMilli = System.currentTimeMillis() + timeToWaitMilli;
            temporarilyDisabled = true;
            LOGGER.log(Level.INFO, "Analytics Transport Falure Detected - waiting" + timeToWaitMilli + "ms before trying again.");
        }
    }

    protected static final void transportSuccess() {
        timeToWaitMilli = 1000;
        nextTimeToTryMilli = 0;
    }

    protected static final void shutdownComplete() {
        isInitialized = false;
        temporarilyDisabled = true;
        transferQueue = null;
        dispatcher = null;
        transportObject = null;
    }

    private static final Boolean okayToSend() {
        Boolean bl = true;
        if (temporarilyDisabled.booleanValue()) {
            if (System.currentTimeMillis() > nextTimeToTryMilli) {
                temporarilyDisabled = false;
            } else {
                bl = false;
            }
        }
        return bl;
    }

    private static final void queueTheData(IAnalyticsData iAnalyticsData) {
        try {
            if (iAnalyticsData.dataIsValid().booleanValue()) {
                transferQueue.add(iAnalyticsData);
            } else {
                LOGGER.log(Level.WARNING, "Bad Analytics data for hit type: " + iAnalyticsData.dataType().toString());
            }
        }
        catch (Exception var1_1) {
            LOGGER.log(Level.WARNING, "Error Queueing Analytics Data: " + var1_1.getMessage());
        }
    }
}

