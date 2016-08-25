/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AbstractAnalyticsData;
import com.ti.et.analytics.AnalyticDataTypes;
import com.ti.et.analytics.IAnalyticsData;

public class AnalyticsTiming
extends AbstractAnalyticsData
implements IAnalyticsData {
    private String category = null;
    private String variable = null;
    private String label = null;
    private String time = null;

    public AnalyticsTiming(String string, String string2, String string3, long l) {
        this.category = string;
        this.variable = string2;
        this.label = string3;
        this.time = String.valueOf(l);
    }

    public AnalyticsTiming(String string, String string2, long l) {
        this(string, string2, null, l);
    }

    @Override
    public AnalyticDataTypes dataType() {
        return AnalyticDataTypes.TIMING;
    }

    public String getCategory() {
        return this.getURLEncoding(this.category);
    }

    public String getVariable() {
        return this.getURLEncoding(this.variable);
    }

    public String getLabel() {
        return this.label == null ? null : this.getURLEncoding(this.label);
    }

    public String getTime() {
        return this.getURLEncoding(this.time);
    }

    @Override
    public Boolean dataIsValid() {
        return this.category != null && this.variable != null && this.time != null;
    }
}

