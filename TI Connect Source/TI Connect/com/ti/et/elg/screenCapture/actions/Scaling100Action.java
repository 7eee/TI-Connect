/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.screenCapture.actions.ScalingAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;

public class Scaling100Action
extends ScalingAction {
    protected static final Scaling100Action INSTANCE = new Scaling100Action();

    private Scaling100Action() {
        this.scale = ScreenCaptureFactory.getScreenCaptureToolbar().get100ValueSlider();
        this.tag = Scaling100Action.class.getSimpleName();
        this.init();
    }

    public static final ScalingAction getInstance() {
        return INSTANCE;
    }
}

