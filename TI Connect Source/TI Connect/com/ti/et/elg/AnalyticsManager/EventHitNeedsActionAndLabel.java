/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.IEventHitNeedsActionAndLabel;

public enum EventHitNeedsActionAndLabel implements IEventHitNeedsActionAndLabel
{
    CONNECTED("Connected"),
    DATA_CONVERSION("Data Conversion"),
    SCREEN_CAPTURE_CONVERSION("Screen Capture Conversion"),
    SCREEN_CAPTURES("Screen Captures"),
    USER_IMAGE_CONVERSION("User Image Conversion");
    
    private final String category;

    private EventHitNeedsActionAndLabel(String string2) {
        this.category = string2;
    }

    @Override
    public final String category() {
        return this.category;
    }
}

