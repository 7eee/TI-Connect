/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.IAnalyticsTransport;

public interface IAnalyticsDispatcher
extends Runnable {
    public void initTransport(IAnalyticsTransport var1);
}

