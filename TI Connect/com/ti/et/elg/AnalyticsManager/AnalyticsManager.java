/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  com.ti.et.analytics.AnalyticsCollector
 *  javafx.geometry.Rectangle2D
 *  javafx.stage.Screen
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.analytics.AnalyticsCollector;
import com.ti.et.elg.AnalyticsManager.AnalyticsLogHandler;
import com.ti.et.elg.AnalyticsManager.IEventHit;
import com.ti.et.elg.AnalyticsManager.IEventHitNeedsAction;
import com.ti.et.elg.AnalyticsManager.IEventHitNeedsActionAndLabel;
import com.ti.et.elg.AnalyticsManager.IEventHitNeedsActionLabelAndValue;
import com.ti.et.elg.AnalyticsManager.IEventHitNeedsValue;
import com.ti.et.elg.AnalyticsManager.IScreenHit;
import com.ti.et.elg.AnalyticsManager.ITimingHit;
import com.ti.et.elg.AnalyticsManager.ITimingHitNeedsVarAndLabel;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Handler;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public final class AnalyticsManager {
    private static final String LOG_TAG = AnalyticsManager.class.getSimpleName();
    public static final String ANALYTICS_ENABLED_PROPERTY = "analytics.userenabled";
    private static final String CID_PROPERTY = "analytics.cid";
    private static final String ANALYTICS_TEST_APPEND_TO_VERSION_PROPERTY = "analytics.testAppendToVersion";
    private static String appName = null;
    private static String appVersion = null;
    private static String trackingID = null;
    private static String cid = null;
    private static String testVersionString = null;
    private static boolean analyticsEnabled = false;
    private static boolean hasBeenInitialized = false;

    private AnalyticsManager() {
    }

    public static synchronized void enableAnalytics(String string, String string2) {
        analyticsEnabled = true;
        AnalyticsManager.initAnalytics(string, string2);
        TILogger.logInfo(LOG_TAG, "Analytics is ENABLED.");
    }

    public static synchronized void disableAnalytics() {
        analyticsEnabled = false;
        TILogger.logInfo(LOG_TAG, "Analytics is DISABLED.");
    }

    public static void shutDown() {
        if (analyticsEnabled || AnalyticsCollector.isEnabled().booleanValue()) {
            AnalyticsCollector.shutDown();
        }
    }

    private static void initAnalytics(String string, String string2) {
        try {
            if (!hasBeenInitialized) {
                AnalyticsManager.initProxy();
                appName = string;
                appVersion = string2;
                cid = AnalyticsManager.getCID();
                AnalyticsManager.setTrackingID(appVersion);
                testVersionString = AnalyticsManager.getTestVersionString();
                AnalyticsCollector.init((String)trackingID, (String)cid, (String)appName, (String)appVersion, (String)testVersionString, (String)AnalyticsManager.getScreenResolution());
                AnalyticsManager.addExceptionLogHandler();
                hasBeenInitialized = true;
                TILogger.logDetail(LOG_TAG, "Analytics Subsystem is enabled and has been initialized.");
            } else {
                TILogger.logDetail(LOG_TAG, "Analytics Subsystem is already initialized.");
            }
        }
        catch (Exception var2_2) {
            analyticsEnabled = false;
            TILogger.logError(LOG_TAG, "Unable to initialize the Analytics Manager:" + var2_2.getMessage(), var2_2);
        }
    }

    public static void postHit(IScreenHit iScreenHit) {
        if (analyticsEnabled) {
            AnalyticsCollector.postScreenVisit((String)iScreenHit.screen());
        }
    }

    public static void postHit(IEventHit iEventHit) {
        if (analyticsEnabled) {
            AnalyticsCollector.postEvent((String)iEventHit.category(), (String)iEventHit.action());
        }
    }

    public static void postHit(IEventHitNeedsAction iEventHitNeedsAction, String string) {
        if (analyticsEnabled && string != null && !string.isEmpty()) {
            AnalyticsCollector.postEvent((String)iEventHitNeedsAction.category(), (String)string);
        }
    }

    public static void postHit(IEventHitNeedsActionAndLabel iEventHitNeedsActionAndLabel, String string, String string2) {
        if (analyticsEnabled && string != null && !string.isEmpty()) {
            AnalyticsCollector.postEvent((String)iEventHitNeedsActionAndLabel.category(), (String)string, (String)string2, (Integer)null);
        }
    }

    public static void postHit(IEventHitNeedsValue iEventHitNeedsValue, Integer n) {
        if (analyticsEnabled) {
            AnalyticsCollector.postEvent((String)iEventHitNeedsValue.category(), (String)iEventHitNeedsValue.action(), (String)null, (Integer)n);
        }
    }

    public static void postHit(IEventHitNeedsActionLabelAndValue iEventHitNeedsActionLabelAndValue, String string, String string2, Integer n) {
        if (analyticsEnabled && string != null && !string.isEmpty()) {
            AnalyticsCollector.postEvent((String)iEventHitNeedsActionLabelAndValue.category(), (String)string, (String)string2, (Integer)n);
        }
    }

    public static void postHit(ITimingHit iTimingHit, long l) {
        if (analyticsEnabled) {
            AnalyticsCollector.postTimingData((String)iTimingHit.category(), (String)iTimingHit.variable(), (long)l);
        }
    }

    public static void postHit(ITimingHitNeedsVarAndLabel iTimingHitNeedsVarAndLabel, String string, String string2, long l) {
        if (analyticsEnabled) {
            AnalyticsCollector.postTimingData((String)iTimingHitNeedsVarAndLabel.category(), (String)string, (String)string2, (long)l);
        }
    }

    public static void postException(String string, Boolean bl) {
        if (analyticsEnabled) {
            AnalyticsCollector.postException((String)string, (Boolean)bl);
        }
    }

    private static String getScreenResolution() {
        String string = "0x0";
        try {
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            int n = (int)rectangle2D.getWidth();
            int n2 = (int)rectangle2D.getHeight();
            string = Integer.toString(n) + "x" + Integer.toString(n2);
        }
        catch (Exception var4_4) {
            TILogger.logError(LOG_TAG, var4_4.getMessage(), var4_4);
        }
        return string;
    }

    private static void setTrackingID(String string) {
        boolean bl = false;
        String string2 = System.getenv("TI.HttpProxy");
        if (string2 == null || string2.isEmpty()) {
            string2 = System.getenv("TI.HttpsProxy");
        }
        if (string2 == null && (string2 = System.getProperty("http.proxyHost")) != null && !string2.contains("ti.com")) {
            string2 = null;
        }
        if (null != string2 && !string2.isEmpty()) {
            bl = true;
        } else if (string != null && string.contains("DEV")) {
            bl = true;
        }
        trackingID = bl ? "UA-41318001-4" : "UA-41318001-5";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String getCID() {
        String string = null;
        reference var1_1 = AnalyticsManager.class;
        synchronized (AnalyticsManager.class) {
            string = UserPropertyManagement.getString("analytics.cid", null);
            if (string == null) {
                string = UUID.randomUUID().toString();
                UserPropertyManagement.setUserProperty("analytics.cid", string);
                UserPropertyManagement.storeUserProperties();
                TILogger.logDetail(LOG_TAG, "Generated a new CID.");
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return string;
        }
    }

    public static void initProxy() {
        String string = "No Host Init";
        String string2 = "No Port Init";
        try {
            System.setProperty("java.net.useSystemProxies", "true");
            List<Proxy> list = ProxySelector.getDefault().select(new URI("http://education.ti.com/"));
            for (Proxy proxy : list) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
                if (inetSocketAddress == null) {
                    TILogger.logInfo(LOG_TAG, "[Proxy Setup]: Proxy auto-detect found no default proxy on this system.");
                    continue;
                }
                TILogger.logInfo(LOG_TAG, "[Proxy Setup]:  hasSystemProxy Proxy auto-detect found proxy type : " + (Object)((Object)proxy.type()));
                TILogger.logInfo(LOG_TAG, "[Proxy Setup]: Proxy auto-detect found proxy hostname '" + inetSocketAddress.getHostName() + "' port '" + inetSocketAddress.getPort() + "'");
                string = inetSocketAddress.getHostName();
                string2 = "" + inetSocketAddress.getPort() + "";
                AnalyticsManager.setHttpProxy(string, string2);
            }
            if (string != null && string2 != null && !string.trim().equals("") && !string2.trim().equals("")) {
                TILogger.logInfo(LOG_TAG, "[Proxy Setup]: Proxy found proxyHost: '" + string + "'\n" + " proxyPort: '" + string2 + "'");
            }
        }
        catch (Exception var2_3) {
            TILogger.logError(LOG_TAG, "[Proxy Setup Failure]: " + var2_3.getMessage(), var2_3);
        }
    }

    private static void setHttpProxy(String string, String string2) {
        Properties properties = System.getProperties();
        properties.put("proxySet", "true");
        properties.put("http.proxyHost", string);
        properties.put("http.proxyPort", string2);
        properties.put("https.proxyHost", string);
        properties.put("https.proxyPort", string2);
    }

    private static String getTestVersionString() {
        return UserPropertyManagement.getString("analytics.testAppendToVersion", null);
    }

    private static void addExceptionLogHandler() {
        AnalyticsLogHandler analyticsLogHandler = new AnalyticsLogHandler();
        Logger logger = null;
        Logger logger2 = null;
        if (analyticsLogHandler != null) {
            logger = Logger.getGlobal();
            for (logger2 = logger.getParent(); logger2 != null; logger2 = logger2.getParent()) {
                logger = logger2;
            }
            if (logger != null) {
                logger.addHandler(analyticsLogHandler);
            }
        }
    }
}

