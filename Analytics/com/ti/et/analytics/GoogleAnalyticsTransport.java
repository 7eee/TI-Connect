/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AnalyticDataTypes;
import com.ti.et.analytics.AnalyticsCollector;
import com.ti.et.analytics.AnalyticsEvent;
import com.ti.et.analytics.AnalyticsException;
import com.ti.et.analytics.AnalyticsScreen;
import com.ti.et.analytics.AnalyticsTiming;
import com.ti.et.analytics.IAnalyticsData;
import com.ti.et.analytics.IAnalyticsTransport;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

final class GoogleAnalyticsTransport
implements IAnalyticsTransport {
    private static final Logger LOGGER = Logger.getLogger(GoogleAnalyticsTransport.class.getCanonicalName());
    private static final String HOST = "http://www.google-analytics.com/collect";
    private String userAgent = null;
    private String trackingID = null;
    private String clientID = null;
    private String appName = "NspireDefault";
    private String appVersion = "NspireDefault";
    private String userLanguage = "NotSet";
    private String customDimension1 = "Dimension1NotSet";
    private String customDimension2 = "Dimension2NotSet";
    private String screenResolution = "SRNotSet";
    private String testVersionString = null;
    private Level hitPostLogLevel = Level.FINE;
    private String basePayloadString = null;

    GoogleAnalyticsTransport(String string, String string2, String string3, String string4, String string5, String string6) {
        if (this.inputParametersAreValid(string, string2, string3, string4).booleanValue()) {
            this.setTestVersionString(string5);
            this.setScreenResolution(string6);
            this.setUserLanguage(this.getURLEncoding(System.getProperty("user.language") + "-" + System.getProperty("user.country")));
            this.setAppName(string3);
            this.setAppVersion(string4);
            try {
                this.setCustomDimension1(System.getProperty("os.name"));
                this.setCustomDimension2(System.getProperty("os.version"));
            }
            catch (Exception var7_7) {
                this.setCustomDimension1("OSNameNotFound");
                this.setCustomDimension2("OSVersionNotFound");
            }
            this.trackingID = "&tid=" + string;
            this.clientID = "&cid=" + string2;
            if (this.testVersionString != null) {
                this.hitPostLogLevel = Level.INFO;
            }
        }
    }

    private final Boolean inputParametersAreValid(String string, String string2, String string3, String string4) {
        Boolean bl = true;
        if (!this.isValid(string).booleanValue()) {
            bl = false;
            LOGGER.log(Level.SEVERE, "Null Tracking ID: Cannot instantiate Transport properly.");
        }
        if (!this.isValid(string2).booleanValue()) {
            bl = false;
            LOGGER.log(Level.SEVERE, "Null client ID: Cannot instantiate Transport properly.");
        }
        if (!this.isValid(string3).booleanValue()) {
            bl = false;
            LOGGER.log(Level.SEVERE, "Null App Name: Cannot instantiate Transport properly.");
        }
        if (!this.isValid(string4).booleanValue()) {
            bl = false;
            LOGGER.log(Level.SEVERE, "Null App Version: Cannot instantiate Transport properly.");
        }
        return bl;
    }

    private Boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }

    @Override
    public String getAppName() {
        return this.appName;
    }

    @Override
    public void setAppName(String string) {
        if (this.isValid(string).booleanValue()) {
            this.appName = "&an=" + this.getURLEncoding(string);
        }
    }

    @Override
    public String getAppVersion() {
        return this.appVersion;
    }

    @Override
    public void setAppVersion(String string) {
        String string2 = this.getTestVersionString();
        if (this.isValid(string).booleanValue()) {
            this.appVersion = this.isValid(string2) != false ? "&av=" + this.getURLEncoding(new StringBuilder().append(string).append(string2).toString()) : "&av=" + this.getURLEncoding(string);
        }
    }

    @Override
    public String getUserLanguage() {
        return this.userLanguage;
    }

    @Override
    public void setUserLanguage(String string) {
        if (this.isValid(string).booleanValue()) {
            this.userLanguage = "&ul=" + this.getURLEncoding(string);
        }
    }

    @Override
    public String getCustomDimension1() {
        return this.customDimension1;
    }

    @Override
    public void setCustomDimension1(String string) {
        if (this.isValid(string).booleanValue()) {
            this.customDimension1 = "&cd1=" + this.getURLEncoding(string);
        }
    }

    @Override
    public String getCustomDimension2() {
        return this.customDimension2;
    }

    @Override
    public void setCustomDimension2(String string) {
        if (this.isValid(this.customDimension2).booleanValue()) {
            this.customDimension2 = "&cd2=" + this.getURLEncoding(string);
        }
    }

    @Override
    public String getScreenResolution() {
        return this.screenResolution;
    }

    @Override
    public void setScreenResolution(String string) {
        this.screenResolution = this.isValid(this.screenResolution) != false ? "&sr=" + this.getURLEncoding(string) : "&sr=0x0";
    }

    public String getTestVersionString() {
        return this.testVersionString;
    }

    public void setTestVersionString(String string) {
        if (this.isValid(string).booleanValue()) {
            this.testVersionString = string;
        }
    }

    @Override
    public void sendData(IAnalyticsData iAnalyticsData) {
        String string = this.buildFormattedPayloadString(iAnalyticsData);
        if (this.isValid(string).booleanValue()) {
            try {
                this.sendPost("http://www.google-analytics.com/collect", string);
                AnalyticsCollector.transportSuccess();
            }
            catch (Exception var3_3) {
                if (LOGGER.isLoggable(Level.WARNING)) {
                    LOGGER.log(Level.WARNING, "Analytics Data Send Failure", var3_3.getMessage());
                }
                AnalyticsCollector.transportFailure();
            }
        } else {
            LOGGER.log(Level.WARNING, "Either null or empty payload passed into sendData()");
        }
    }

    private String buildFormattedPayloadString(IAnalyticsData iAnalyticsData) {
        StringBuilder stringBuilder = new StringBuilder(512);
        this.addBasePayloadString(stringBuilder);
        AnalyticDataTypes analyticDataTypes = iAnalyticsData.dataType();
        switch (analyticDataTypes) {
            case EVENT: {
                this.buildEventPayloadString(stringBuilder, (AnalyticsEvent)iAnalyticsData);
                break;
            }
            case SCREEN: {
                this.buildScreenPayloadString(stringBuilder, (AnalyticsScreen)iAnalyticsData);
                break;
            }
            case TIMING: {
                this.buildTimingPayloadString(stringBuilder, (AnalyticsTiming)iAnalyticsData);
                break;
            }
            case EXCEPTION: {
                this.buildExceptionPayloadString(stringBuilder, (AnalyticsException)iAnalyticsData);
                break;
            }
            case SHUTDOWN: {
                LOGGER.log(Level.WARNING, "Analytics Data Send Failure: Shutdown data type should not have been sent to the transport.");
                break;
            }
            default: {
                LOGGER.log(Level.WARNING, "Analytics Data Send Failure: Unrecognized data type: " + analyticDataTypes.toString());
            }
        }
        return stringBuilder.toString();
    }

    private void addBasePayloadString(StringBuilder stringBuilder) {
        if (this.basePayloadString == null) {
            stringBuilder.append("v=1").append(this.trackingID).append(this.clientID).append(this.appName).append(this.appVersion);
            stringBuilder.append(this.userLanguage).append(this.screenResolution).append(this.customDimension1).append(this.customDimension2);
            this.basePayloadString = stringBuilder.toString();
        } else {
            stringBuilder.append(this.basePayloadString);
        }
    }

    private void buildEventPayloadString(StringBuilder stringBuilder, AnalyticsEvent analyticsEvent) {
        String string = analyticsEvent.getEventLabel();
        String string2 = analyticsEvent.getEventValue();
        stringBuilder.append("&t=event&ec=").append(analyticsEvent.getEventCategory()).append("&ea=").append(analyticsEvent.getEventName());
        if (string != null) {
            stringBuilder.append("&el=").append(string);
        }
        if (string2 != null) {
            stringBuilder.append("&ev=").append(string2);
        }
    }

    private void buildScreenPayloadString(StringBuilder stringBuilder, AnalyticsScreen analyticsScreen) {
        stringBuilder.append("&t=appview&cd=").append(analyticsScreen.getScreenName());
    }

    private void buildTimingPayloadString(StringBuilder stringBuilder, AnalyticsTiming analyticsTiming) {
        String string = analyticsTiming.getLabel();
        stringBuilder.append("&t=timing&utc=").append(analyticsTiming.getCategory()).append("&utv=").append(analyticsTiming.getVariable()).append("&utt=").append(analyticsTiming.getTime());
        if (string != null) {
            stringBuilder.append("&utl=").append(string);
        }
    }

    private void buildExceptionPayloadString(StringBuilder stringBuilder, AnalyticsException analyticsException) {
        stringBuilder.append("&t=exception&exd=").append(analyticsException.getExeptionString());
        if (analyticsException.isFatal().booleanValue()) {
            stringBuilder.append("&exf=1");
        } else {
            stringBuilder.append("&exf=0");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sendPost(String string, String string2) throws IOException, MalformedURLException {
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Starting request to: {0}", string2);
        }
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        FilterOutputStream filterOutputStream = null;
        URL uRL = new URL(string);
        String string3 = string2;
        LOGGER.log(this.hitPostLogLevel, string2);
        try {
            httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setRequestProperty("User-Agent", this.getUserAgentString());
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            filterOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            filterOutputStream.writeBytes(string3);
            filterOutputStream.flush();
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while (bufferedReader.readLine() != null) {
                }
            }
            finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            int n = httpURLConnection.getResponseCode();
            if (n != 200) {
                throw new RuntimeException("The request wasn't successful - please revisit payload for payload: " + string2);
            }
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (filterOutputStream != null) {
                filterOutputStream.close();
            }
        }
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Request done for payload: {0}", string2);
        }
    }

    private String getUserAgentString() {
        String string = null;
        String string2 = null;
        String string3 = null;
        if (this.userAgent == null) {
            string = System.getProperty("os.name");
            string2 = System.getProperty("os.version");
            string3 = string.contains("Mac") ? "(Macintosh; Intel " + string + " " + string2.replace('.', '_') + ")" : "(Windows NT " + string2 + ")";
            this.userAgent = "Mozilla/5.0 " + string3;
        }
        return this.userAgent;
    }

    private String getURLEncoding(String string) {
        String string2 = "URLEncodingFail";
        try {
            if (string != null) {
                string2 = URLEncoder.encode(string, "UTF-8");
            }
        }
        catch (Exception var3_3) {
            string2 = "URLEncodingFailed for " + string;
        }
        return string2;
    }

}

