/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AbstractAnalyticsData;
import com.ti.et.analytics.AnalyticDataTypes;
import com.ti.et.analytics.IAnalyticsData;

public class AnalyticsEvent
extends AbstractAnalyticsData
implements IAnalyticsData {
    private String eventCategory = null;
    private String eventName = null;
    private String eventLabel = null;
    private String eventValue = null;

    public AnalyticsEvent(String string, String string2, String string3, Integer n) {
        this.eventCategory = string;
        this.eventName = string2;
        this.eventLabel = string3;
        if (n != null) {
            this.eventValue = n.toString();
        }
    }

    public AnalyticsEvent(String string, String string2, String string3) {
        this(string, string2, string3, null);
    }

    public AnalyticsEvent(String string, String string2) {
        this(string, string2, null, null);
    }

    @Override
    public AnalyticDataTypes dataType() {
        return AnalyticDataTypes.EVENT;
    }

    public String getEventCategory() {
        return this.getURLEncoding(this.eventCategory);
    }

    public String getEventName() {
        return this.getURLEncoding(this.eventName);
    }

    public String getEventLabel() {
        return this.eventLabel == null ? null : this.getURLEncoding(this.eventLabel);
    }

    public String getEventValue() {
        return this.eventValue == null ? null : this.getURLEncoding(this.eventValue);
    }

    @Override
    public Boolean dataIsValid() {
        return this.eventCategory != null && this.eventName != null;
    }
}

