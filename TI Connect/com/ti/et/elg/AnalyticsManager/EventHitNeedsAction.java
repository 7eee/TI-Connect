/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.IEventHitNeedsAction;

public enum EventHitNeedsAction implements IEventHitNeedsAction
{
    NEW_POWER_STATE("New Power State"),
    OPEN_PROGRAM("Open Program");
    
    private final String category;

    private EventHitNeedsAction(String string2) {
        this.category = string2;
    }

    @Override
    public final String category() {
        return this.category;
    }
}

