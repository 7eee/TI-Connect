/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.screenCapture.actions.ScalingAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;

public class Scaling400Action
extends ScalingAction {
    protected static final Scaling400Action INSTANCE = new Scaling400Action();

    private Scaling400Action() {
        this.scale = ScreenCaptureFactory.getScreenCaptureToolbar().get400ValueSlider();
        this.tag = Scaling400Action.class.getSimpleName();
        this.init();
    }

    public static ScalingAction getInstance() {
        return INSTANCE;
    }
}

