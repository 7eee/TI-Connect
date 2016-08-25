/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.ITimingHit;

public enum TimingHit implements ITimingHit
{
    SESSION_DURATION("Session Duration", "Session Duration");
    
    private final String category;
    private String variable;

    private TimingHit(String string2, String string3) {
        this.variable = string3;
        this.category = string2;
    }

    @Override
    public final String category() {
        return this.category;
    }

    @Override
    public final String variable() {
        return this.variable;
    }
}

