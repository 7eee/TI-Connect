/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.translator;

import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;

public class TIProgramDataHolder {
    private byte[] programTokens;
    private int programVersion;
    private TIStatus translationError = new TIStatus(0);
    private String programAsString;
    private TIVar programVar;

    public byte[] getProgramTokens() {
        return this.programTokens;
    }

    public void setProgramTokens(byte[] arrby) {
        this.programTokens = arrby;
    }

    public int getProgramVersion() {
        return this.programVersion;
    }

    public void setProgramVersion(int n) {
        this.programVersion = n;
    }

    public TIStatus getTIStatus() {
        return this.translationError;
    }

    public void setTIStatus(TIStatus tIStatus) {
        this.translationError = tIStatus;
    }

    public String getProgramAsString() {
        return this.programAsString;
    }

    public void setProgramAsString(String string) {
        this.programAsString = string;
    }

    public TIVar getProgramTIVar() {
        return this.programVar;
    }

    public void setProgramTIVar(TIVar tIVar) {
        this.programVar = tIVar;
    }
}

