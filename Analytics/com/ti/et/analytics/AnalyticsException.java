/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AbstractAnalyticsData;
import com.ti.et.analytics.AnalyticDataTypes;
import com.ti.et.analytics.IAnalyticsData;

public class AnalyticsException
extends AbstractAnalyticsData
implements IAnalyticsData {
    private String exceptionString = null;
    private Boolean fatalFlag = false;

    public AnalyticsException(String string, Boolean bl) {
        this.exceptionString = string;
        this.fatalFlag = bl;
    }

    public String getExeptionString() {
        return this.getURLEncoding(this.exceptionString);
    }

    public Boolean isFatal() {
        return this.fatalFlag;
    }

    @Override
    public AnalyticDataTypes dataType() {
        return AnalyticDataTypes.EXCEPTION;
    }

    @Override
    public Boolean dataIsValid() {
        return this.exceptionString != null && this.fatalFlag != null;
    }
}

