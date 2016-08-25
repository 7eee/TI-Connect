/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AbstractAnalyticsData;
import com.ti.et.analytics.AnalyticDataTypes;
import com.ti.et.analytics.IAnalyticsData;

public class AnalyticsScreen
extends AbstractAnalyticsData
implements IAnalyticsData {
    private String screenName = null;

    @Override
    public AnalyticDataTypes dataType() {
        return AnalyticDataTypes.SCREEN;
    }

    AnalyticsScreen(String string) {
        this.screenName = string;
    }

    public String getScreenName() {
        return this.getURLEncoding(this.screenName);
    }

    @Override
    public Boolean dataIsValid() {
        return this.screenName != null;
    }
}

