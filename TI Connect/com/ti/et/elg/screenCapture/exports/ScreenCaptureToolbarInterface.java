/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.screenCapture.exports;

import com.ti.et.elg.screenCapture.exports.ScreenCaptureInterface;
import javafx.scene.Node;

public interface ScreenCaptureToolbarInterface {
    public static final String[] scrCapSliderStrings = new String[]{" 50% ", " 75% ", " 100% ", " 200% ", " 300% ", " 400% "};

    public Node getRootNode();

    public void init(ScreenCaptureInterface var1);

    public int get050ValueSlider();

    public int get075ValueSlider();

    public int get100ValueSlider();

    public int get200ValueSlider();

    public int get300ValueSlider();

    public int get400ValueSlider();

    public void changeScreenCaptureScaling(int var1);

    public static class SC_SCALE {
        public static final int PCT_050 = 0;
        public static final int PCT_075 = 1;
        public static final int PCT_100 = 2;
        public static final int PCT_200 = 3;
        public static final int PCT_300 = 4;
        public static final int PCT_400 = 5;
    }

}

