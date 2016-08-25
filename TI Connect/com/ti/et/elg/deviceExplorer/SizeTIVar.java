/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.deviceExplorer;

public class SizeTIVar {
    private String sizeFormat;
    private Integer size;

    public SizeTIVar(String string, Integer n) {
        this.sizeFormat = string;
        this.size = n;
    }

    public String getSizeFormat() {
        return this.sizeFormat;
    }

    public void setSizeFormat(String string) {
        this.sizeFormat = string;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer n) {
        this.size = n;
    }
}

