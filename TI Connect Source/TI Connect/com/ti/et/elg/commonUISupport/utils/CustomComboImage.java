/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.image.ImageView
 */
package com.ti.et.elg.commonUISupport.utils;

import javafx.scene.image.ImageView;

public class CustomComboImage {
    private int id;
    private String text;
    private ImageView image;

    public CustomComboImage(String string, ImageView imageView, int n) {
        this.text = string;
        this.image = imageView;
        this.id = n;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String string) {
        this.text = string;
    }

    public ImageView getImage() {
        return this.image;
    }

    public void setImage(ImageView imageView) {
        this.image = imageView;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int n) {
        this.id = n;
    }
}

