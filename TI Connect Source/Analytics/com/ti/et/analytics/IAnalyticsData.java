/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AnalyticDataTypes;

public interface IAnalyticsData {
    public AnalyticDataTypes dataType();

    public Boolean isShutDownRequest();

    public Boolean dataIsValid();
}

