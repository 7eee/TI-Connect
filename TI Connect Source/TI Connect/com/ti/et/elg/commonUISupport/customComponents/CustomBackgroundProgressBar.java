/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.layout.Pane
 */
package com.ti.et.elg.commonUISupport.customComponents;

import com.ti.et.elg.commonUISupport.customComponents.BackgroundProgressBar;
import javafx.scene.layout.Pane;

public class CustomBackgroundProgressBar
implements BackgroundProgressBar {
    String progressColor = "#555555";
    private float percentage;
    private static final String ALPHA_ZERO = "00";
    private static final String PROGRESS_BAR_OPACITY = "B2";
    private final String originalStyle;
    private Pane progressBarPane;

    public CustomBackgroundProgressBar(Pane pane) {
        this.originalStyle = pane.getStyle();
        this.progressBarPane = pane;
    }

    @Override
    public void setPercentage(float f) {
        this.percentage = f;
        this.progressBarPane.setStyle("-fx-background-color:linear-gradient(to right, " + this.progressColor + "B2" + " " + f + "%, " + this.progressColor + "00" + " " + f + "%);-fx-opacity:1");
    }

    public float getPercentage() {
        return this.percentage;
    }

    public void done() {
        this.progressBarPane.setStyle(this.originalStyle);
    }

    public void setProgressBarColor(String string) {
        this.progressColor = string;
    }
}

