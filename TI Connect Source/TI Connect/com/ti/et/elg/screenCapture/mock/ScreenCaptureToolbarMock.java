/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 *  javafx.scene.layout.Pane
 */
package com.ti.et.elg.screenCapture.mock;

import com.ti.et.elg.screenCapture.exports.ScreenCaptureInterface;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureToolbarInterface;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class ScreenCaptureToolbarMock
implements ScreenCaptureToolbarInterface {
    private final Integer[] sliderValues = new Integer[]{0, 1, 2, 3, 4, 5};
    private Node node = new Pane();

    @Override
    public Node getRootNode() {
        return this.node;
    }

    @Override
    public void init(ScreenCaptureInterface screenCaptureInterface) {
    }

    @Override
    public int get050ValueSlider() {
        return 0;
    }

    @Override
    public int get075ValueSlider() {
        return 1;
    }

    @Override
    public int get100ValueSlider() {
        return 2;
    }

    @Override
    public int get200ValueSlider() {
        return 3;
    }

    @Override
    public int get300ValueSlider() {
        return 4;
    }

    @Override
    public int get400ValueSlider() {
        return 5;
    }

    @Override
    public void changeScreenCaptureScaling(int n) {
    }

    public Integer[] getSliderValues() {
        return this.sliderValues;
    }

    public String[] getSliderStrings() {
        return scrCapSliderStrings;
    }
}

