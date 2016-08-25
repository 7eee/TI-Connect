/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.screenCapture.actions.ScalingAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;

public class Scaling300Action
extends ScalingAction {
    protected static final Scaling300Action INSTANCE = new Scaling300Action();

    private Scaling300Action() {
        this.scale = ScreenCaptureFactory.getScreenCaptureToolbar().get300ValueSlider();
        this.tag = Scaling300Action.class.getSimpleName();
        this.init();
    }

    public static final ScalingAction getInstance() {
        return INSTANCE;
    }
}

