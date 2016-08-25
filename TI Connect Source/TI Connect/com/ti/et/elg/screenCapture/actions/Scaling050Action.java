/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.screenCapture.actions.ScalingAction;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;

public class Scaling050Action
extends ScalingAction {
    protected static final Scaling050Action INSTANCE = new Scaling050Action();

    private Scaling050Action() {
        this.scale = ScreenCaptureFactory.getScreenCaptureToolbar().get050ValueSlider();
        this.tag = Scaling050Action.class.getSimpleName();
        this.init();
    }

    public static final ScalingAction getInstance() {
        return INSTANCE;
    }
}

