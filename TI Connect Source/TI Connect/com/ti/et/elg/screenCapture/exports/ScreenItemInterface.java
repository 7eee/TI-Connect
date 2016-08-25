/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.image.Image
 *  javafx.scene.image.WritableImage
 */
package com.ti.et.elg.screenCapture.exports;

import com.ti.et.elg.commonUISupport.utils.DisplaysAsSelected;
import com.ti.et.elg.commonUISupport.utils.FXNodeInterface;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public interface ScreenItemInterface
extends FXNodeInterface,
DisplaysAsSelected {
    public Image getImage();

    public Image getOriginalImage();

    public void init(Image var1, int var2);

    public void changeScaleImage(int var1);

    public void selectImage();

    public void unselectImage();

    public void selectTitleText();

    public String getTitle();

    public boolean hasBorder();

    public void setBorder(boolean var1);

    public boolean isDirty();

    public void isDirty(boolean var1);

    public void updateDirtyFlag();

    public WritableImage getImageWithBorder(boolean var1);
}

