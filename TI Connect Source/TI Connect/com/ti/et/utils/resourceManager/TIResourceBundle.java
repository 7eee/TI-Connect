/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.resourceManager;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class TIResourceBundle
extends ResourceBundle {
    ResourceBundle bundle;

    public TIResourceBundle(ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
    }

    @Override
    protected Object handleGetObject(String string) {
        Object object = this.bundle.getObject(string);
        if (object instanceof String) {
            object = ((String)object).replaceAll("@\\[en\\]@", "");
        }
        return object;
    }

    @Override
    public Enumeration<String> getKeys() {
        return this.bundle.getKeys();
    }
}

