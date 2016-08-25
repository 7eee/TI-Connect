/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.AnalyticsManager;

import com.ti.et.elg.AnalyticsManager.IScreenHit;

public enum ScreenHit implements IScreenHit
{
    PROGRAM_EDITOR("Program Editor"),
    CALCULATOR_EXPLORER("Calculator Explorer"),
    CALCULATOR_INFORMATION_DLG("Calculator Information Dlg"),
    SCREEN_CAPTURE("Screen Capture"),
    SEND_TO_CALC_DGL_OS("Send to Calculators Dlg: OS"),
    SEND_TO_CALC_DGL_SCREENS("Send to Calculators Dlg: Screens"),
    SEND_TO_CALC_DGL_FILES("Send to Calculators Dlg: Files");
    
    private final String screen;

    private ScreenHit(String string2) {
        this.screen = string2;
    }

    @Override
    public final String screen() {
        return this.screen;
    }
}

