/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.ITimingHitNeedsVarAndLabel;

public enum TimingHitNeedsVarAndLabel implements ITimingHitNeedsVarAndLabel
{
    DELETE_TIME("Delete"),
    GET_TIME("Get"),
    SCREEN_CAPTURE_TIME("Screen Capture"),
    SEND_TIME("Send"),
    SEND_REPLACE_TIME("Send Replace");
    
    private final String category;

    private TimingHitNeedsVarAndLabel(String string2) {
        this.category = string2;
    }

    @Override
    public final String category() {
        return this.category;
    }
}

