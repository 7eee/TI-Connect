/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.screenCapture.actions.ScalingAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;

public class Scaling200Action
extends ScalingAction {
    protected static final Scaling200Action INSTANCE = new Scaling200Action();

    private Scaling200Action() {
        this.scale = ScreenCaptureFactory.getScreenCaptureToolbar().get200ValueSlider();
        this.tag = Scaling200Action.class.getSimpleName();
        this.init();
    }

    public static final ScalingAction getInstance() {
        return INSTANCE;
    }
}

