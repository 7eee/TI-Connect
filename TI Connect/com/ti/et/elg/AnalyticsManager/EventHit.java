/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.IEventHit;

public enum EventHit implements IEventHit
{
    HELP("Help", "Help"),
    NEW_PROGRAM("New Program", "New Program"),
    LAUNCH("Launch", "Launch");
    
    private final String category;
    private final String action;

    private EventHit(String string2, String string3) {
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

