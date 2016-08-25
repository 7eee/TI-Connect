/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.IEventHitNeedsActionLabelAndValue;

public enum EventHitNeedsActionLabelAndValue implements IEventHitNeedsActionLabelAndValue
{
    DELETE_VARS("Delete Vars"),
    GET_VARS("Get Vars"),
    SAVE_AND_SEND_PROGRAM("Save And Send Program"),
    SEND_VARS("Send Vars");
    
    private final String category;

    private EventHitNeedsActionLabelAndValue(String string2) {
        this.category = string2;
    }

    @Override
    public final String category() {
        return this.category;
    }
}

