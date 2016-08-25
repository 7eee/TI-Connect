/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.screenCapture.actions.ScalingAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;

public class Scaling075Action
extends ScalingAction {
    protected static final Scaling075Action INSTANCE = new Scaling075Action();

    private Scaling075Action() {
        this.scale = ScreenCaptureFactory.getScreenCaptureToolbar().get075ValueSlider();
        this.tag = Scaling075Action.class.getSimpleName();
        this.init();
    }

    public static final ScalingAction getInstance() {
        return INSTANCE;
    }
}

