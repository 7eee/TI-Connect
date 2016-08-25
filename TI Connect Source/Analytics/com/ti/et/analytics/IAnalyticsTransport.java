/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.IAnalyticsData;

interface IAnalyticsTransport {
    public void sendData(IAnalyticsData var1);

    public String getAppName();

    public void setAppName(String var1);

    public String getAppVersion();

    public void setAppVersion(String var1);

    public String getUserLanguage();

    public void setUserLanguage(String var1);

    public String getCustomDimension1();

    public void setCustomDimension1(String var1);

    public String getCustomDimension2();

    public void setCustomDimension2(String var1);

    public String getScreenResolution();

    public void setScreenResolution(String var1);
}

