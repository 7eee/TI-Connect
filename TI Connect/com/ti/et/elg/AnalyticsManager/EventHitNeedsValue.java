/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.IEventHitNeedsValue;

public enum EventHitNeedsValue implements IEventHitNeedsValue
{
    SAVE_PROGRAM("Save Program", "Save Program");
    
    private final String category;
    private final String action;

    private EventHitNeedsValue(String string2, String string3) {
        this.category = string2;
        this.action = string3;
    }

    @Override
    public final String category() {
        return this.category;
    }

    @Override
    public final String action() {
        return this.action;
    }
}

