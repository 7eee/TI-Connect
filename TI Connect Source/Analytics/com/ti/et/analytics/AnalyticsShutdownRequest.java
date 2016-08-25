/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AbstractAnalyticsData;
import com.ti.et.analytics.AnalyticDataTypes;
import com.ti.et.analytics.IAnalyticsData;

public class AnalyticsShutdownRequest
extends AbstractAnalyticsData
implements IAnalyticsData {
    @Override
    public Boolean isShutDownRequest() {
        return true;
    }

    @Override
    public AnalyticDataTypes dataType() {
        return AnalyticDataTypes.SHUTDOWN;
    }

    @Override
    public Boolean dataIsValid() {
        return true;
    }
}

